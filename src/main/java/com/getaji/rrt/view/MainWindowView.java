package com.getaji.rrt.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;


/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class MainWindowView {

    private final Stage stage;
    private final Scene scene;
    private final BorderPane topPane = new BorderPane();
        private final VBox topToolbar = new VBox();
            private final MenuBar menuBar = new MenuBarView();
            private final Accordion accordion = new Accordion();
                private final TextArea textArea = new TextArea();
        private final ScrollPane scrollPane = new ScrollPane();
            private final HBox timelines = new HBox();
        private final HBox bottomToolbar = new HBox();
            private final HBox statusBox = new HBox();
                private final ImageView statusImage = new ImageView();
                private final Label statusLabel = new Label("初期化中...");

    public MainWindowView(Stage stage) {
        this.stage = stage;
        timelines.setSpacing(5);
        scene = new Scene(topPane);
        stage.setScene(scene);
        stage.setTitle("RedRabbit");
        topPane.setTop(topToolbar);
        topPane.setCenter(scrollPane);
        topPane.setBottom(bottomToolbar);
        // --------------------------------
        TitledPane inputPane = new TitledPane("What's happend?", textArea);
        textArea.setWrapText(true);
        accordion.getPanes().addAll(inputPane);
        accordion.setMaxHeight(100);
        topToolbar.getChildren().addAll(menuBar, accordion);
        textArea.maxWidthProperty().bind(stage.widthProperty());
        // --------------------------------
        scrollPane.setContent(timelines);
        timelines.maxHeightProperty().bind(scrollPane.heightProperty().subtract(10).subtract(10));
        timelines.minHeightProperty().bind(scrollPane.heightProperty().subtract(10).subtract(10));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // --------------------------------
        bottomToolbar.getChildren().addAll(statusBox);
        statusBox.getChildren().addAll(statusImage, statusLabel);
        statusBox.setSpacing(5);
        statusBox.maxWidthProperty().bind(stage.widthProperty().divide(3));
    }

    public MainWindowView addTextAreaHandler(EventHandler<KeyEvent> handler) {
        textArea.addEventHandler(KeyEvent.KEY_RELEASED, handler);
        return this;
    }

    public MainWindowView setInputText(String text) {
        textArea.setText(text);
        return this;
    }

    public MainWindowView addTimeline(TimelineView timelineView) {
        Node node = timelineView.getView();
        timelines.getChildren().add(node);
        return this;
    }

    public MainWindowView setWindowStatus(String status) {
        log.trace("status: " + status);
        statusLabel.setText(status);
        return this;
    }

    public MainWindowView setWindowStatus(Image image, String status) {
        log.trace("status: " + status);
        statusImage.setImage(image);
        statusLabel.setText(status);

        return this;
    }

    public String getInputText() {
        return textArea.getText();
    }

    /*public <E> void addResizeTimelineHandler(ChangeListener<? super Number> listener) {
        timelines.widthProperty().addListener(listener);
    }*/
}
