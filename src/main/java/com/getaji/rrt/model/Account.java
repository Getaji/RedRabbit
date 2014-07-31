package com.getaji.rrt.model;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class Account {
    private final Twitter twitter;
    private final AccessToken accessToken;
    private final String tokenString;
    private final String secretTokenString;

    public Account(Twitter twitter, AccessToken accessToken) {
        this.twitter = twitter;
        this.accessToken = accessToken;
        this.tokenString = accessToken.getToken();
        this.secretTokenString = accessToken.getTokenSecret();
    }
}
