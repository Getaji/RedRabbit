package com.getaji.rrt.logic.twitter;

import com.getaji.rrt.model.Account;
import lombok.Getter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class TwitterConnector {
    private Twitter twitter;
    private final ConfigurationBuilder configurationBuilder;
    private RequestToken requestToken;
    @Getter private AccessToken accessToken;

    public TwitterConnector() throws TwitterException {
        configurationBuilder = new ConfigurationBuilder();
    }

    public void setAPIKeys(String api_key, String api_secret) throws TwitterException {
        configurationBuilder.setOAuthConsumerKey(api_key);
        configurationBuilder.setOAuthConsumerSecret(api_secret);
    }

    public String getOAuthURL() throws TwitterException {
        return requestToken.getAuthorizationURL();
    }

    public void setPin(String pin) throws TwitterException {
        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter = twitterFactory.getInstance();
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        afterOAuth(accessToken);
    }

    public Account setAccessToken(String token, String token_secret) throws TwitterException {
        Configuration configuration = configurationBuilder.build();
        TwitterFactory twitterFactory = new TwitterFactory(configuration);
        AccessToken accessToken = new AccessToken(token, token_secret);
        twitter = twitterFactory.getInstance(accessToken);
        Account account = new Account(twitter, accessToken, configuration);
        afterOAuth(accessToken);
        return account;
    }

    private void afterOAuth(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
