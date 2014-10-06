package com.getaji.rrt;

import com.getaji.rrt.logic.twitter.TwitterConnector;
import com.getaji.rrt.model.Account;
import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.model.WindowStatusType;
import com.getaji.rrt.util.StatusViewModelFactory;
import com.getaji.rrt.viewmodel.MainWindowViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import twitter4j.*;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class Main extends Application {

    @Getter private static Main instance;
    @Getter private MainWindowViewModel mainWindowViewModel;

    public static void main(String[] args) throws Exception {
        //twitterPreInit();
        Application.launch(Main.class, args);
    }

    private static void twitterPreInit() throws TwitterException {
        TwitterConnector connector = new TwitterConnector();
        connector.setAPIKeys(
                "oth6oiwapFEgesjI6U9CcD2QC",
                "Qc3utRmAcBcO5BeZSG4xZKaVx81jf3NHRHKXXZKJHxEC2du1KS"
        );
        Account account = connector.setAccessToken(
                "730631798-IhbL8ptKcQMKM1e1SGiSmMcj4pxicH4NKo20Uoox",
                "gAPkvAaoofEBIoiLlgk4ckRo8ySgpEwGFqTIo5INnN4Jw"
        );
        StaticObjects.ACCOUNTS.addAccount(account);
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.trace("Starting...");
        instance = this;
        mainWindowViewModel = new MainWindowViewModel(stage);
        mainWindowViewModel.addTimeline("Home");
        mainWindowViewModel.addTimeline("Mentions");
        stage.show();
        mainWindowViewModel.setWindowStatus(WindowStatusType.PLAIN, "起動成功");
        log.trace("Starting complete");
        //linkTwitter();
    }

    private void linkTwitter() {
        Account account = StaticObjects.ACCOUNTS.getAccount(730631798);
        TwitterStreamFactory streamFactory = new TwitterStreamFactory(account.getConfiguration());
        TwitterStream twitterStream = streamFactory.getInstance(account.getAccessToken());
        twitterStream.addListener(new UserStreamAdapter() {
            @Override
            public void onStatus(Status status) {
                StaticObjects.STATUS_CACHE.set(status);
                Platform.runLater(() -> {
                    mainWindowViewModel.addStatusToCurrent(
                            StatusViewModelFactory.createFromTwitter(status)
                    );
                });
            }
        });
        twitterStream.user();
    }
}
