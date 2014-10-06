package com.getaji.rrt.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class SettableComputePane extends Pane {
    public SettableComputePane() {
        super();
    }

    public SettableComputePane(Node... children) {
        super(children);
    }

    @Override
    protected double computeMinWidth(double height) {
        return super.computeMinWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        return super.computeMinHeight(width);
    }

    @Override
    protected double computePrefWidth(double height) {
        return super.computePrefWidth(height);
    }

    @Override
    protected double computePrefHeight(double width) {
        return super.computePrefHeight(width);
    }

    @Override
    protected double computeMaxWidth(double height) {
        return super.computeMaxWidth(height);
    }

    @Override
    protected double computeMaxHeight(double width) {
        return super.computeMaxHeight(width);
    }
}
