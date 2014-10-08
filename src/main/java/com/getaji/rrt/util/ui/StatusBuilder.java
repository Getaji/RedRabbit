package com.getaji.rrt.util.ui;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.viewmodel.StatusViewModel;
import twitter4j.Status;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public interface StatusBuilder {

    public static StatusBuilder simple(String title, String text) {
        return SimpleStatusBuilder.newInstance(title, text);
    }

    public static StatusBuilder twitter(Status twitterStatus) {
        return TwitterStatusViewModelBuilder.newInstance(twitterStatus);
    }

    public StatusModel.StatusModelBuilder getBuilder();
    public StatusViewModel buildViewModel();
}
