package com.getaji.rrt.view;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MainWindowView {

    private Stage stage;
    private final Scene scene;
    private final BorderPane topPane = new BorderPane();
        private final MenuBar menuBar = new MenuBarView();

    public MainWindowView() {
        scene = new Scene(topPane);
        topPane.setTop(menuBar);
    }

    public MainWindowView setStage(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);
        return this;
    }

    public MainWindowView setTitle(String title) {
        stage.setTitle(title);
        return this;
    }

    public MainWindowView setWidth(int width) {
        stage.setWidth(width);
        return this;
    }

    public MainWindowView setHeight(int height) {
        stage.setHeight(height);
        return this;
    }
}
