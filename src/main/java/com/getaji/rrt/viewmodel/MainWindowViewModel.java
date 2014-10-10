package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.MainWindowModel;
import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.model.WindowStatusType;
import com.getaji.rrt.util.ui.StatusBuilder;
import com.getaji.rrt.view.MainWindowView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class MainWindowViewModel {

    public static MainWindowViewModel create(Stage primaryStage) {
        return new MainWindowViewModel(primaryStage);
    }

    @Getter private final MainWindowModel model = MainWindowModel.create();
    @Getter private final MainWindowView view;

    private MainWindowViewModel(Stage stage) {
        view = MainWindowView.create(stage);
        view.addTextAreaHandler(e -> {
            log.debug(e.getCode());
            if (e.isControlDown() && e.getCode() == KeyCode.ENTER) {
                String inputText = view.getInputText();
                try {
                    StaticObjects.getCurrentTwitter().updateStatus(inputText);
                    view.setInputText("");
                    setWindowStatus(WindowStatusType.SUCCESS, "ツイートに成功:" + inputText);
                } catch (TwitterException e1) {
                    setWindowStatus(WindowStatusType.ERROR, "ツイートに失敗:" + inputText);
                    log.error(e1);
                }
            }
        });
    }

    public TimelineViewModel addTimeline() {
        TimelineViewModel timeline = TimelineViewModel.create();
        model.addTimeline(timeline);
        view.addTimeline(timeline.getView());
        return timeline;
    }

    public TimelineViewModel addTimeline(String timelineName) {
        TimelineViewModel timeline = addTimeline();
        timeline.getModel().setName(timelineName);
        return timeline;
    }

    public MainWindowViewModel addStatusToCurrent(StatusViewModel status) {
        model.getCurrentTimeline().addStatus(status);
        return this;
    }

    public MainWindowViewModel addStatusToCurrent(Status twitterStatus) {
        model.getCurrentTimeline().addStatus(StatusBuilder.twitter(twitterStatus).buildViewModel());
        return this;
    }

    public TimelineViewModel getCurrentTimeline() {
        return model.getCurrentTimeline();
    }

    public MainWindowViewModel setWindowStatus(WindowStatusType type, String status) {
        view.setWindowStatus(type.getImage(), status.replace("\n", " "));
        return this;
    }
}
