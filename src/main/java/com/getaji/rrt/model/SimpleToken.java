package com.getaji.rrt.model;

import lombok.Data;
import twitter4j.auth.AccessToken;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Data
public class SimpleToken {
    private String accessToken;
    private String accessTokenSecret;

    public SimpleToken(AccessToken accessToken) {
        this(accessToken.getToken(), accessToken.getTokenSecret());
    }

    public SimpleToken(String accessToken, String accessTokenSecret) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    public AccessToken create() {
        return new AccessToken(accessToken, accessTokenSecret);
    }
}
