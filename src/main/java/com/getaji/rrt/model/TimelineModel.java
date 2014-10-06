package com.getaji.rrt.model;

import com.getaji.rrt.util.Wrapper;
import com.getaji.rrt.viewmodel.StatusViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class TimelineModel {

    // ================================================================
    // Fields
    // ================================================================
    private final List<StatusViewModel> statuses = new ArrayList<>();
    private final Wrapper<Double> width = Wrapper.wrap(370d);
    private String name = "None";

    // ================================================================
    // Getters
    // ================================================================
    public double getWidth() {
        return width.get();
    }

    public Wrapper<Double> getWidthWrapper() {
        return width;
    }

    public String getName() {
        return name;
    }

    public int getStatusesSize() {
        return statuses.size();
    }

    // ================================================================
    // Setters
    // ================================================================
    public TimelineModel addStatus(StatusViewModel status) {
        statuses.add(status);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ================================================================
    // Commands
    // ================================================================
    public TimelineModel clearStatuses() {
        statuses.clear();
        return this;
    }
}
