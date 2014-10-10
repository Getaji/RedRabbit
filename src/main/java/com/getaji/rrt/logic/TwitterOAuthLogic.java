package com.getaji.rrt.logic;

import com.getaji.rrt.logic.config.DataSaveLogic;
import com.getaji.rrt.logic.twitter.TwitterConnector;
import com.getaji.rrt.util.Wrapper;
import com.getaji.rrt.view.OAuthView;
import lombok.extern.log4j.Log4j2;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class TwitterOAuthLogic {
    public static boolean authorization() {
        // TODO がんばれ❤
        Wrapper<TwitterConnector> twitterConnector = Wrapper.wrapNullable(null);
        try {
            twitterConnector.set(TwitterConnector.create());
        } catch (TwitterException e1) {
            log.error("TwitterConnectorの初期化に失敗。");
            e1.printStackTrace();
            return false;
        }

        Wrapper<Boolean> success = Wrapper.wrap(Boolean.FALSE);
        Consumer<String> whenPressOK = pin -> {
            try {
                TwitterConnector connector = twitterConnector.get();
                connector.setPin(pin);
                Twitter twitter = connector.getTwitter();
                try {
                    DataSaveLogic.saveToken(connector.getAccessToken(), twitter.getId());
                    // TODO MainLogic.MODEL_ADAPTER.getAccountsModel().addTwitter(twitter);
                    success.set(Boolean.TRUE);
                } catch (IOException e) {
                    log.error("データの保存に失敗。");
                    e.printStackTrace();
                }
            } catch (TwitterException e1) {
                log.error("不正なPINコード。");
            }
        };
        OAuthView.showWindow(whenPressOK);
        while (!success.get()) {
            try {
                Desktop.getDesktop().browse(new URI(twitterConnector.get().getOAuthURL()));
            } catch (URISyntaxException e1) {
                log.error("不正な認証URL。");
                return false;
            } catch (IOException e1) {
                log.error("認証ブラウザの表示に失敗。");
                e1.printStackTrace();
                return false;
            } catch (TwitterException e1) {
                log.error("認証URLの取得に失敗。");
                e1.printStackTrace();
            }
        }
        return success.get();
    }
}
