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

    @Getter private final Account account;
    @Getter private final Status status;

    public OnStatusEvent(Account account, Status status) {
        this.account = account;
        this.status = status;
    }
}
