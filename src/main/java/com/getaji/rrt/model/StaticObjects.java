package com.getaji.rrt.model;

import com.getaji.rrt.event.EventDispatcher;
import com.getaji.rrt.util.ImageCache;
import com.getaji.rrt.util.StatusCache;
import twitter4j.Twitter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StaticObjects {

    private static final AccountsModel ACCOUNTS = AccountsModel.create();
    private static final ImageCache IMAGE_CACHE = ImageCache.create();
    private static final StatusCache STATUS_CACHE = StatusCache.create();
    private static final EventDispatcher twitterEventDispatcher = EventDispatcher.create();

    private static Account currentAccount;

    static {
        ACCOUNTS.addCurrentAccountChangeHandler(wrapper -> {
            currentAccount = wrapper.get();
        });
    }

    public static AccountsModel getAccounts() {
        return ACCOUNTS;
    }

    public static ImageCache getImageCache() {
        return IMAGE_CACHE;
    }

    public static StatusCache getStatusCache() {
        return STATUS_CACHE;
    }

    public static Twitter getCurrentTwitter() {
        return currentAccount.getTwitter();
    }

    public static EventDispatcher getTwitterEventDispatcher() {
        return twitterEventDispatcher;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    private StaticObjects() {}
}
