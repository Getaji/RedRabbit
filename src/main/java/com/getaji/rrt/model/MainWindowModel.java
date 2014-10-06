package com.getaji.rrt.model;

import com.getaji.rrt.viewmodel.TimelineViewModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MainWindowModel {

    private final List<TimelineViewModel> timelines = new ArrayList<>();

    @Getter private TimelineViewModel currentTimeline;

    public TimelineViewModel addTimeline(TimelineViewModel timeline) {
        if (timelines.size() == 0) {
            currentTimeline = timeline;
        }

        timelines.add(timeline);

        return timeline;
    }

    public MainWindowModel setCurrentTimeline(TimelineViewModel timeline) {
        if (!timelines.contains(timeline)) {
            throw new IllegalArgumentException("そんなタイムラインはない");
        }

        currentTimeline = timeline;
        return this;
    }
}
