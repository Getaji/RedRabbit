package com.getaji.rrt.model;

import com.getaji.rrt.Main;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import twitter4j.Status;
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
        private String date = "none";
        private String dateUrl = "";
        private String text;
        private String via = "none";
        private String viaUrl = "";

        private StatusModelBuilder() {
            //
        }

        public StatusModel build() {
            if (iconUrl.isEmpty()) {
                throw new IllegalArgumentException("IconUrl is empty");
            }

            return new StatusModel(isTwitterStatus, id, iconUrl, title, date, dateUrl, text, via, viaUrl);
        }
    }

    private final boolean isTwitterStatus;
    private final long id;
    private final String iconUrl;
    private final String title;
    private final String date;
    private final String dateUrl;
    private final String text;
    private final String via;
    private final String viaUrl;

    private StatusModel(boolean isTwitterStatus, long id, String iconUrl,
                        String title, String date, String dateUrl,
                        String text, String via, String viaUrl) {
        this.isTwitterStatus = isTwitterStatus;
        this.id = id;
        this.iconUrl = iconUrl;
        this.title = title;
        this.date = date;
        this.dateUrl = dateUrl;
        this.text = text;
        this.via = via;
        this.viaUrl = viaUrl;
    }

    public boolean retweet() {
        if (isTwitterStatus) {
            Twitter twitter = StaticObjects.getCurrentTwitter();
            try {
                twitter.retweetStatus(id);
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.SUCCESS, "リツイートに成功:" + text
                );
                return true;
            } catch (TwitterException e) {
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.ERROR, "リツイートに失敗:" + text
                );
                log.error(e);
                return false;
            }
        }
        return true;
    }

    public boolean unRetweet() {
        if (isTwitterStatus) {
            Twitter twitter = StaticObjects.getCurrentTwitter();
            Status status = StaticObjects.getStatusCache().get(id);
            try {
                twitter.destroyStatus(id);
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.SUCCESS, "リツイート解除に成功:" + text
                );
                return true;
            } catch (TwitterException e) {
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.ERROR, "リツイート解除に失敗:" + text
                );
                log.error(e);
                return false;
            }
        }
        return true;
    }

    public void favorite() {
        //
    }

    public void unFavorite() {
        //
    }
}
