package com.getaji.rrt;

import com.getaji.rrt.event.OnFavoriteEvent;
import com.getaji.rrt.event.OnStatusEvent;
import com.getaji.rrt.logic.twitter.TwitterConnector;
import com.getaji.rrt.model.Account;
import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.model.WindowStatusType;
import com.getaji.rrt.util.ui.StatusBuilder;
import com.getaji.rrt.viewmodel.MainWindowViewModel;
import com.getaji.rrt.viewmodel.TimelineViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import twitter4j.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class Main extends Application {

    // ================================================================
    // Static fields
    // ================================================================
    @Getter private static Main instance;

    // ================================================================
    // Static methods
    // ================================================================
    public static void main(String[] args) throws Exception {
        Application.launch(Main.class, args);
    }

    // ================================================================
    // Fields
    // ================================================================
    @Getter private MainWindowViewModel mainWindowViewModel;

    // ================================================================
    // Commands
    // ================================================================
    @Override
    public void start(Stage stage) throws Exception {
        log.trace("Starting...");
        instance = this;
        mainWindowViewModel = new MainWindowViewModel(stage);
        TimelineViewModel homeTimeline = mainWindowViewModel.addTimeline("Home");
        TimelineViewModel eventsTimeline = mainWindowViewModel.addTimeline("Events");
        stage.show();
        mainWindowViewModel.setWindowStatus(WindowStatusType.PLAIN, "起動成功");
        log.trace("Starting complete");
        StaticObjects.getTwitterEventDispatcher().addHandler(
                OnStatusEvent.class, e -> Platform.runLater(() -> {
                    homeTimeline.addStatus(e.getStatus());
                }));
        StaticObjects.getTwitterEventDispatcher().addHandler(
                OnFavoriteEvent.class, e -> Platform.runLater(() -> {
                    if (e.getSource().getId() != StaticObjects.getCurrentAccount().getId()) {
                        StatusBuilder builder = StatusBuilder.twitter(
                                e.getFavoritedStatus());
                        String name = e.getSource().getName();
                        String screenName = e.getSource().getScreenName();
                        String title = String.format("%s @%sにふぁぼられました", name, screenName);
                        builder.getBuilder().setTitle(title);
                        eventsTimeline.addStatus(builder.buildViewModel());
                    }
                }));
        daemon(() -> { linkTwitter(false); return null; });
    }

    private void daemon(Callable<Void> callable) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        );
        executorService.submit(callable);
    }

    private void linkTwitter(boolean connectUS) throws TwitterException {
        TwitterConnector connector = new TwitterConnector();
        connector.setAPIKeys(
                "oth6oiwapFEgesjI6U9CcD2QC",
                "Qc3utRmAcBcO5BeZSG4xZKaVx81jf3NHRHKXXZKJHxEC2du1KS"
        );
        Account account = connector.setAccessToken(
                "730631798-IhbL8ptKcQMKM1e1SGiSmMcj4pxicH4NKo20Uoox",
                "gAPkvAaoofEBIoiLlgk4ckRo8ySgpEwGFqTIo5INnN4Jw"
        );
        StaticObjects.getAccounts().addAccount(account);

        if (connectUS) {
            TwitterStreamFactory streamFactory = new TwitterStreamFactory(account.getConfiguration());
            TwitterStream twitterStream = streamFactory.getInstance(account.getAccessToken());
            twitterStream.addListener(new UserStreamAdapter() {
                @Override
                public void onStatus(Status status) {
                    StaticObjects.getStatusCache().set(status);
                    StaticObjects.getTwitterEventDispatcher().onFire(
                            OnStatusEvent.class, new OnStatusEvent(account, status)
                    );
                }

                @Override
                public void onFavorite(User source, User target, Status favoritedStatus) {
                    StaticObjects.getStatusCache().set(favoritedStatus);
                    StaticObjects.getTwitterEventDispatcher().onFire(
                            OnFavoriteEvent.class, new OnFavoriteEvent(source, target, favoritedStatus)
                    );
                }
            });
            twitterStream.user();
        }
    }
}
