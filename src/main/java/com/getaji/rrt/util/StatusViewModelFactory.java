package com.getaji.rrt.util;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.viewmodel.StatusViewModel;
import twitter4j.Status;
import twitter4j.User;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StatusViewModelFactory {

    private static final Pattern viaPattern = Pattern.compile("^\\<a href=\"(.+)\" rel=\".+\"\\>(.+)\\</a\\>$");
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
        User user = status.getUser();
        String title = String.format("%s @%s", user.getName(), user.getScreenName());
        String date = new SimpleDateFormat(dateFormat).format(status.getCreatedAt());
        String dateUrl = String.format("https://twitter.com/%s/status/%s",
                user.getScreenName(), status.getId());
        Matcher viaMatcher = viaPattern.matcher(status.getSource());
        viaMatcher.find();
        String via = viaMatcher.group(2);
        String viaUrl = viaMatcher.group(1);
        return new StatusViewModel(StatusModel.builder()
                .setTwitterStatus(true)
                .setId(status.getId())
                .setIconUrl(user.getProfileImageURL())
                .setTitle(title)
                .setDate(date)
                .setDateUrl(dateUrl)
                .setText(status.getText())
                .setVia(via)
                .setViaUrl(viaUrl)
                .build()
        );
    }
}
