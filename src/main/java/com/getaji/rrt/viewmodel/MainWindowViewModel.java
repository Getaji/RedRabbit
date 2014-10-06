package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.MainWindowModel;
import com.getaji.rrt.model.WindowStatusType;
import com.getaji.rrt.view.MainWindowView;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MainWindowViewModel {

    @Getter private final MainWindowModel model = new MainWindowModel();
    @Getter private final MainWindowView view;

    public MainWindowViewModel(Stage stage) {
        view = new MainWindowView(stage);
    }

    public TimelineViewModel addTimeline() {
        TimelineViewModel timeline = new TimelineViewModel();
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

    public TimelineViewModel getCurrentTimeline() {
        return model.getCurrentTimeline();
    }

    public MainWindowViewModel setWindowStatus(WindowStatusType type, String status) {
        view.setWindowStatus(type.getImage(), status);
        return this;
    }
}
