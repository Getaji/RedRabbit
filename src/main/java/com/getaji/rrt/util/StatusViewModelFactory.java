package com.getaji.rrt.util;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.viewmodel.StatusViewModel;
import twitter4j.Status;
import twitter4j.User;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StatusViewModelFactory {

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
        return new StatusViewModel(StatusModel.builder()
                .setTwitterStatus(true)
                .setId(status.getId())
                .setIconUrl(user.getProfileImageURL())
                .setTitle(title)
                .setText(status.getText())
                .setVia(status.getSource())
                .build()
        );
    }
}
