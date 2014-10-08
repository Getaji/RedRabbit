package com.getaji.rrt.util.ui;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.util.DateTimeUtil;
import com.getaji.rrt.viewmodel.StatusViewModel;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import lombok.Getter;
import twitter4j.*;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
final class TwitterStatusViewModelBuilder implements StatusBuilder {

    // ================================================================
    // Classes
    // ================================================================
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

    // ================================================================
    // Static fields
    // ================================================================
    private static final Pattern viaPattern = Pattern.compile("^\\<a href=\"(.+)\" rel=\".+\"\\>(.+)\\</a\\>$");
    private static final String dateFormat = "yy:MM:DD HH:mm:ss";

    // ================================================================
    // Static methods
    // ================================================================
    public static TwitterStatusViewModelBuilder newInstance(Status status) {
        return new TwitterStatusViewModelBuilder(status);
    }

    // ================================================================
    // Fields
    // ================================================================
    private final Status status;
    private final StatusModel.StatusModelBuilder builder;
    private final List<Node> textNodes;

    // ================================================================
    // Constructors
    // ================================================================
    private TwitterStatusViewModelBuilder(Status status) {
        this.status = status;
        builder = StatusModel.builder();
        // --------------------------------
        boolean retweeted = status.isRetweet();
        User retweeter = status.getUser();
        User user = retweeted ? status.getRetweetedStatus().getUser() : status.getUser();
        Status retweetedStatus = status;
        status = retweeted ? status.getRetweetedStatus(): status;
        // --------------------------------
        String title = String.format("%s @%s", user.getName(), user.getScreenName());
        // --------------------------------
        textNodes = new ArrayList<>();
        List<LinkContainer> links = loadTextNodes();
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
            lastDeleteLength = link.getEnd() - lastDeleteLength;
            textBuilder.delete(0, lastDeleteLength);
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
        builder.setTwitterStatus(true)
                .setId(status.getId())
                .setIconUrl(user.getProfileImageURL())
                .setSubIconUrl(retweeted ? retweeter.getMiniProfileImageURL() : "")
                .setTitle(title)
                .setDate(time)
                .setDateUrl(dateUrl)
                .setRetweets(status.getRetweetCount())
                .setFavorites(status.getFavoriteCount())
                .setVia(via)
                .setViaUrl(viaUrl);
    }

    private List<LinkContainer> loadTextNodes() {
        Status status = this.status.isRetweet() ? this.status.getRetweetedStatus() : this.status;
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
        return links;
    }

    /**
     * 注意点：最終的に{@link #buildViewModel()}で追加処理を行うため、
     * ここで取得したbuilderで{@link com.getaji.rrt.model.StatusModel.StatusModelBuilder#build()}を
     * 呼び出すと不完全なViewModelとなってしまうため、必ず中間処理に留めること。
     * @return
     */
    public StatusModel.StatusModelBuilder getBuilder() {
        return builder;
    }

    @Override
    public StatusViewModel buildViewModel() {
        return builder.build().createViewModel().setTextNodes(textNodes, status.getText());
    }
}
