package com.getaji.rrt.model;

import lombok.Getter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Getter
public class Account {
    private final Twitter twitter;
    private final AccessToken accessToken;
    private final String tokenString;
    private final String secretTokenString;
    private final Configuration configuration;
    private final long id;
    private final String screenName;
    private final User user;

    public Account(Twitter twitter, AccessToken accessToken, Configuration configuration) throws TwitterException {
        this.twitter = twitter;
        this.accessToken = accessToken;
        this.tokenString = accessToken.getToken();
        this.secretTokenString = accessToken.getTokenSecret();
        this.configuration = configuration;
        this.id = accessToken.getUserId();
        // this.screenName = accessToken.getScreenName(); これはnull
        this.user = twitter.showUser(id);
        this.screenName = user.getScreenName();
    }
}
