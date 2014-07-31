package com.getaji.rrt.logic.twitter;

import lombok.Getter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class TwitterConnector {
    private Twitter twitter;
    private TwitterFactory twitterFactory;
    private final ConfigurationBuilder configurationBuilder;
    private RequestToken requestToken;
    @Getter private AccessToken accessToken;

    public TwitterConnector() throws TwitterException {
        twitterFactory = new TwitterFactory();
        configurationBuilder = new ConfigurationBuilder();
    }

    public void setAPIKeys(String consumer_key, String consumer_secret) throws TwitterException {
        twitter.setOAuthConsumer(consumer_key, consumer_secret);
        requestToken = twitter.getOAuthRequestToken();
        configurationBuilder.setOAuthConsumerKey(consumer_key);
        configurationBuilder.setOAuthConsumerSecret(consumer_secret);
    }

    public String getOAuthURL() throws TwitterException {
        return requestToken.getAuthorizationURL();
    }

    public void setPin(String pin) throws TwitterException {
        twitter = twitterFactory.getInstance();
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        afterOAuth(accessToken);
    }

    public void setAccessToken(String token, String token_secret) {
        AccessToken accessToken = new AccessToken(token, token_secret);
        twitter = twitterFactory.getInstance(accessToken);
        afterOAuth(accessToken);
    }

    private void afterOAuth(AccessToken accessToken) {
        configurationBuilder.setOAuthAccessToken(accessToken.getToken());
        configurationBuilder.setOAuthAccessTokenSecret(accessToken.getTokenSecret());
        this.accessToken = accessToken;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
