package com.getaji.rrt.event;

import lombok.Getter;
import twitter4j.Status;
import twitter4j.User;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Getter
public class OnFavoriteEvent implements Event {
    private final User source;
    private final User target;
    private final Status favoritedStatus;

    public static OnFavoriteEvent create(User source, User target, Status favoritedStatus) {
        return new OnFavoriteEvent(source, target, favoritedStatus);
    }

    private OnFavoriteEvent(User source, User target, Status favoritedStatus) {
        this.source = source;
        this.target = target;
        this.favoritedStatus = favoritedStatus;
    }
}
