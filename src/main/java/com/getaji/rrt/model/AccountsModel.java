package com.getaji.rrt.model;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.Set;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class AccountsModel {
    public Set<Twitter> twitters;

    public void addTwitter(AccessToken accessToken) {
        TwitterFactory factory = new TwitterFactory();
        addTwitter(factory.getInstance(accessToken));
    }

    public void addTwitter(Twitter twitter) {
        twitters.add(twitter);
    }
}
