package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.TimelineModel;
import com.getaji.rrt.util.ui.StatusBuilder;
import com.getaji.rrt.view.StatusView;
import com.getaji.rrt.view.TimelineView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import twitter4j.Status;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class TimelineViewModel implements ChangeListener<Number> {

    public static TimelineViewModel create() {
        return new TimelineViewModel();
    }

    @Getter private final TimelineView view = TimelineView.create(this);
    @Getter private final TimelineModel model = TimelineModel.create();

    // ================================================================
    // Constructors
    // ================================================================
    public TimelineViewModel() {
        model.getWidthWrapper().addValueSetHandler(w -> {
            //
        });
    }

    public TimelineViewModel(String name) {
        this();
        model.setName(name);
    }

    // ================================================================
    // Getters
    // ================================================================
    public StatusViewModel getSelectedStatus() {
        StatusView statusView = view.getSelectedItem();
        if (statusView == null) {
            return null;
        }
        return statusView.getViewModel();
    }

    // ================================================================
    // Setters
    // ================================================================
    public TimelineViewModel addStatus(Status twitterStatus) {
        addStatus(StatusBuilder.twitter(twitterStatus).buildViewModel());
        return this;
    }

    public TimelineViewModel addStatus(StatusViewModel status) {
        model.addStatus(status);
        view.addStatus(status);
        log.trace(String.format("Timeline name:%s Add status id: %d index:%d",
                model.getName(),
                status.getModel().getId(),
                model.getStatusesSize() - 1));
        return this;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        // TODO TimelineViewに横幅の変更を伝える
    }

    public TimelineViewModel clearStatuses() {
        log.trace("timeline clear");
        view.clear();
        model.clearStatuses();
        return this;
    }
}
