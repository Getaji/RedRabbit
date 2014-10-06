package com.getaji.rrt.model;

import com.getaji.rrt.util.ImageCache;
import com.getaji.rrt.util.StatusCache;
import twitter4j.Twitter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StaticObjects {
    private static final AccountsModel ACCOUNTS = new AccountsModel();
    private static final ImageCache IMAGE_CACHE = new ImageCache();
    private static final StatusCache STATUS_CACHE = new StatusCache();

    private static Twitter currentTwitter;

    static {
        ACCOUNTS.addCurrentAccountChangeHandler(wrapper -> {
            currentTwitter = wrapper.get().getTwitter();
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
        return currentTwitter;
    }
}
