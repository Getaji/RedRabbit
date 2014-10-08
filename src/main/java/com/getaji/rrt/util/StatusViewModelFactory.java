package com.getaji.rrt.util;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.viewmodel.StatusViewModel;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import twitter4j.*;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class StatusViewModelFactory {

    @Getter
    private static class LinkContainer implements Comparable<LinkContainer> {
        private final String display;
        private final String link;
        private final int start;
        private final int end;

        private LinkContainer(String display, String link, int start, int end) {
            this.display = display;
            this.link = link;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(LinkContainer o) {
            int x = this.start;
            int y = o.start;
            return x < y ? -1 : (x == y ? 0 : 1);
        }
    }

    private static final Pattern viaPattern = Pattern.compile("^\\<a href=\"(.+)\" rel=\".+\"\\>(.+)\\</a\\>$");
    DateTimeFormatter shortDateTimeFormatter = DateTimeFormatter.ofPattern("");
    private static final String dateFormat = "yy:MM:DD HH:mm:ss";

    public static StatusViewModel createSimple(String title, String text) {
        return new StatusViewModel(StatusModel.builder()
                .setIconUrl("icon_x48.png")
                .setTitle(title)
                .setText(text)
                .setVia("google")
                .build()
        );
    }

    public static StatusViewModel createFromTwitter(Status status) {
        // ================================
        boolean retweeted = status.isRetweet();
        User retweeter = status.getUser();
        User user = retweeted ? status.getRetweetedStatus().getUser() : status.getUser();
        Status retweetedStatus = status;
        status = retweeted ? status.getRetweetedStatus(): status;
        // --------------------------------
        String title = String.format("%s @%s", user.getName(), user.getScreenName());
        // --------------------------------
        List<Node> textNodes = new ArrayList<>();
        List<LinkContainer> links = new ArrayList<>();
        for (URLEntity urlEntity : status.getURLEntities()) {
            links.add(new LinkContainer(
                    urlEntity.getExpandedURL(), urlEntity.getExpandedURL(),
                    urlEntity.getStart(), urlEntity.getEnd()));
        }
        for (MediaEntity mediaEntity : status.getExtendedMediaEntities()) {
            links.add(new LinkContainer(
                    mediaEntity.getDisplayURL(), mediaEntity.getExpandedURL(),
                    mediaEntity.getStart(), mediaEntity.getEnd()));
        }
        for (HashtagEntity hashtagEntity : status.getHashtagEntities()) {
            String hashtag = hashtagEntity.getText();
            String link = String.format("https://twitter.com/hashtag/%s?src=hash", hashtag);
            links.add(new LinkContainer("#" + hashtag, link,
                    hashtagEntity.getStart(), hashtagEntity.getEnd()));
        }
        for (UserMentionEntity mentionEntity : status.getUserMentionEntities()) {
            String sn = mentionEntity.getScreenName();
            String link = "https://twitter.com/" + sn;
            links.add(new LinkContainer("@" + sn, link,
                    mentionEntity.getStart(), mentionEntity.getEnd()));
        }
        Collections.sort(links);
        StringBuilder textBuilder = new StringBuilder(status.getText());
        int lastDeleteLength = 0;
        for (LinkContainer link : links) {
            String plain = textBuilder.substring(0, link.start - lastDeleteLength);
            if (!plain.isEmpty()) {
                textNodes.add(new Text(plain));
            }
            try {
                Hyperlink hyperlink = FXHelper.createHyperlink(
                        link.getDisplay(), link.getLink()
                );
                textNodes.add(hyperlink);
            } catch (URISyntaxException e) {
                textNodes.add(new Text(link.getDisplay()));
            }
            lastDeleteLength = lastDeleteLength + link.getEnd();
            textBuilder.delete(0, link.getEnd());
        }
        if (0 < textBuilder.length()) {
            textNodes.add(new Text(textBuilder.toString()));
        }
        // --------------------------------
        LocalDateTime createdAt = status.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String time = DateTimeUtil.getLocalDateTimeDiff(createdAt, LocalDateTime.now()).getThree();
        // --------------------------------
        String dateUrl = String.format("https://twitter.com/%s/status/%s",
                user.getScreenName(), status.getId());
        // --------------------------------
        Matcher viaMatcher = viaPattern.matcher(status.getSource());
        viaMatcher.find();
        String via = viaMatcher.group(2);
        String viaUrl = viaMatcher.group(1);
        // --------------------------------
        return new StatusViewModel(StatusModel.builder()
                .setTwitterStatus(true)
                .setId(status.getId())
                .setIconUrl(user.getProfileImageURL())
                .setSubIconUrl(retweeted ? retweeter.getMiniProfileImageURL() : "")
                .setTitle(title)
                .setDate(time)
                .setDateUrl(dateUrl)
                .setRetweets(status.getRetweetCount())
                .setFavorites(status.getFavoriteCount())
                .setVia(via)
                .setViaUrl(viaUrl)
                .build()
        ).setTextNodes(textNodes, status.getText());
    }
}
