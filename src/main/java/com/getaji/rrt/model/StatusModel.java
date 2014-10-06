package com.getaji.rrt.model;

import com.getaji.rrt.Main;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
@Getter
@Accessors(chain = true)
public class StatusModel {

    public static StatusModelBuilder builder() {
        return new StatusModelBuilder();
    }

    @Data
    public static class StatusModelBuilder {

        private boolean isTwitterStatus = false;
        private long id = -1;
        private String iconUrl;
        private String title;
        private String text;
        private String via;

        private StatusModelBuilder() {
            //
        }

        public StatusModel build() {
            if (iconUrl.isEmpty()) {
                throw new IllegalArgumentException("IconUrl is empty");
            }

            return new StatusModel(isTwitterStatus, id, iconUrl, title, text, via);
        }
    }

    private final boolean isTwitterStatus;
    private final long id;
    private final String iconUrl;
    private final String title;
    private final String text;
    private final String via;

    private StatusModel(boolean isTwitterStatus, long id, String iconUrl, String title, String text, String via) {
        this.isTwitterStatus = isTwitterStatus;

        this.id = id;
        this.iconUrl = iconUrl;
        this.title = title;
        this.text = text;
        this.via = via;
    }

    public void retweet() {
        if (isTwitterStatus) {
            Twitter twitter = StaticObjects.ACCOUNTS.getCurrentAccount().getTwitter();
            try {
                twitter.retweetStatus(id);
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.SUCCESS, "リツイートに成功:" + text
                );
            } catch (TwitterException e) {
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.ERROR, "リツイートに失敗:" + text
                );
                log.error(e);
            }
        }
    }

    public void unRetweet() {
        //
    }

    public void favorite() {
        //
    }

    public void unFavorite() {
        //
    }
}
