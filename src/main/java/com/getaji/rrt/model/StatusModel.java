package com.getaji.rrt.model;

import com.getaji.rrt.Main;
import com.getaji.rrt.util.Wrapper;
import com.getaji.rrt.viewmodel.StatusViewModel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.function.Consumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class StatusModel {

    // ================================================================
    // Classes
    // ================================================================
    @Data
    @Accessors(chain = true)
    public static class StatusModelBuilder {

        private boolean isTwitterStatus = false;
        private long id = -1;
        private String iconUrl;
        private String subIconUrl = "";
        private String title;
        private String date = "none";
        private String dateUrl = "";
        private String text;
        private long retweets = 0;
        private long favorites = 0;
        private String via = "none";
        private String viaUrl = "";

        private StatusModelBuilder() {
            //
        }

        public StatusModel build() {
            if (iconUrl.isEmpty()) {
                throw new IllegalArgumentException("IconUrl is empty");
            }

            return new StatusModel(this);
        }
    }

    // ================================================================
    // Static methods
    // ================================================================
    public static StatusModelBuilder builder() {
        return new StatusModelBuilder();
    }

    // ================================================================
    // Fields
    // ================================================================
    @Getter private boolean isTwitterStatus;
    @Getter private long id;
    @Getter private String iconUrl;
    @Getter private String subIconUrl;
    @Getter private String title;
    @Getter private String date;
    @Getter private String dateUrl;
    @Getter private String text;
    @Getter private String via;
    @Getter private String viaUrl;
    @Getter private long retweets = 0;
    @Getter private long favorites = 0;

    private final Wrapper<Boolean> isTwitterStatusWrapper;
    private final Wrapper<Long> idWrapper;
    private final Wrapper<String> iconUrlWrapper;
    private final Wrapper<String> subIconUrlWrapper;
    private final Wrapper<String> titleWrapper;
    private final Wrapper<String> dateWrapper;
    private final Wrapper<String> dateUrlWrapper;
    private final Wrapper<String> textWrapper;
    private final Wrapper<String> viaWrapper;
    private final Wrapper<String> viaUrlWrapper;

    // ================================================================
    // Constructors
    // ================================================================
    private StatusModel(StatusModelBuilder builder) {
        this.isTwitterStatus = builder.isTwitterStatus;
        this.id = builder.id;
        this.iconUrl = builder.iconUrl;
        this.subIconUrl = builder.subIconUrl;
        this.title = builder.title;
        this.date = builder.date;
        this.dateUrl = builder.dateUrl;
        this.text = builder.text;
        this.via = builder.via;
        this.viaUrl = builder.viaUrl;
        this.retweets = builder.retweets;
        this.favorites = builder.favorites;

        isTwitterStatusWrapper = Wrapper.wrapNullable(isTwitterStatus);
        idWrapper = Wrapper.wrapNullable(id);
        iconUrlWrapper = Wrapper.wrapNullable(iconUrl);
        subIconUrlWrapper = Wrapper.wrapNullable(subIconUrl);
        titleWrapper = Wrapper.wrapNullable(title);
        dateWrapper = Wrapper.wrapNullable(date);
        dateUrlWrapper = Wrapper.wrapNullable(dateUrl);
        textWrapper = Wrapper.wrapNullable(text);
        viaWrapper = Wrapper.wrapNullable(via);
        viaUrlWrapper = Wrapper.wrapNullable(viaUrl);
    }

    // ================================================================
    // Getters
    // ================================================================
    public StatusViewModel createViewModel() {
        return StatusViewModel.create(this);
    }

    // ================================================================
    // Setters
    // ================================================================
    // TODO 全フィールド分のハンドラを作る
    public StatusModel addTitleSetHandler(Consumer<Wrapper<String>> handler) {
        titleWrapper.addValueSetHandler(handler);
        return this;
    }

    public StatusModel addTextSetHandler(Consumer<Wrapper<String>> handler) {
        textWrapper.addValueSetHandler(handler);
        return this;
    }

    public void setTwitterStatus(boolean isTwitterStatus) {
        this.isTwitterStatus = isTwitterStatus;
        isTwitterStatusWrapper.set(isTwitterStatus);
    }

    public void setId(long id) {
        this.id = id;
        idWrapper.set(id);
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        iconUrlWrapper.set(iconUrl);
    }

    public void setSubIconUrl(String subIconUrl) {
        this.subIconUrl = subIconUrl;
        subIconUrlWrapper.set(subIconUrl);
    }

    public void setTitle(String title) {
        this.title = title;
        titleWrapper.set(title);
    }

    public void setDate(String date) {
        this.date = date;
        dateWrapper.set(date);
    }

    public void setDateUrl(String dateUrl) {
        this.dateUrl = dateUrl;
        dateUrlWrapper.set(dateUrl);
    }

    public void setText(String text) {
        this.text = text;
        textWrapper.set(text);
    }

    public void setVia(String via) {
        this.via = via;
        viaWrapper.set(via);
    }

    public void setViaUrl(String viaUrl) {
        this.viaUrl = viaUrl;
        viaUrlWrapper.set(viaUrl);
    }

    // ================================================================
    // Commands
    // ================================================================
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

    public boolean favorite() {
        if (isTwitterStatus) {
            Twitter twitter = StaticObjects.getCurrentTwitter();
            try {
                twitter.createFavorite(id);
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.SUCCESS, "お気に入り登録に成功:" + text
                );
                return true;
            } catch (TwitterException e) {
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.ERROR, "お気に入り登録に失敗:" + text
                );
                log.error(e);
                return false;
            }
        }
        return true;
    }

    public boolean unFavorite() {
        if (isTwitterStatus) {
            Twitter twitter = StaticObjects.getCurrentTwitter();
            try {
                twitter.destroyFavorite(id);
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.SUCCESS, "お気に入り解除に成功:" + text
                );
                return true;
            } catch (TwitterException e) {
                Main.getInstance().getMainWindowViewModel().setWindowStatus(
                        WindowStatusType.ERROR, "お気に入り解除に失敗:" + text
                );
                log.error(e);
                return false;
            }
        }
        return true;
    }
}
