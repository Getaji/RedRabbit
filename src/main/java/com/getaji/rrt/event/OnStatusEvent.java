package com.getaji.rrt.event;

import com.getaji.rrt.model.Account;
import lombok.Getter;
import twitter4j.Status;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class OnStatusEvent implements Event {

    public static OnStatusEvent create(Account account, Status status) {
        return new OnStatusEvent(account, status);
    }

    @Getter private final Account account;
    @Getter private final Status status;

    private OnStatusEvent(Account account, Status status) {
        this.account = account;
        this.status = status;
    }
}
