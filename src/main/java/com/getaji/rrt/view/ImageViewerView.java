package com.getaji.rrt.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class ImageViewerView {
    private final Stage stage;
    private final BorderPane borderPane = new BorderPane();
    private final ImageView imageView = new ImageView();
    private final BorderPane bottomPane = new BorderPane();
    private final TextField urlField = new TextField();
    private final Button reloadButton = new Button("再読込");
    private final Button openButton = new Button("ブラウザで開く");
    private final Button saveButton = new Button("保存");
    private Image image;
    private double beforeWidth, beforeHeight;
    private final double defaultWidth = 640;
    private final double defaultHeight = 480;

    public ImageViewerView() {
        // --------------------------------
        Scene scene = new Scene(borderPane);
        stage = new Stage();
        stage.setTitle("画像詳細");
        stage.setScene(scene);
        stage.setMinWidth(128);
        scene.getStylesheets().add("css/ImageViewer.css");
        // --------------------------------
        borderPane.setId("top-pane");
        BorderPane.setAlignment(imageView, Pos.CENTER);
        borderPane.setCenter(imageView);
        borderPane.setBottom(bottomPane);
        // --------------------------------
        imageView.setPreserveRatio(true);
        // --------------------------------
        final HBox buttonsBox = new HBox();
        bottomPane.setCenter(urlField);
        bottomPane.setRight(buttonsBox);
        BorderPane.setMargin(urlField, new Insets(5));
        BorderPane.setMargin(buttonsBox, new Insets(5));
        //urlField.setStyle("-fx-border-color: #FFF;");
        buttonsBox.getChildren().addAll(reloadButton, openButton, saveButton);
        buttonsBox.setSpacing(4);
        // --------------------------------
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            beforeWidth = newValue.doubleValue();
            if (image == null) return;
            if (width <= image.getWidth()) {
                imageView.setFitWidth(width);
            } else {
                imageView.setFitWidth(image.getWidth());
            }
            if (oldValue.doubleValue() < newValue.doubleValue()) {
                if (beforeHeight < imageView.getFitHeight()) {
                    imageView.setFitHeight(imageView.getFitHeight() - beforeHeight);
                }
            }
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            beforeHeight = newValue.doubleValue();
            if (image == null) return;
            if (height <= image.getHeight()) {
                imageView.setFitHeight(height - bottomPane.getHeight());
            } else {
                imageView.setFitHeight(image.getHeight());
            }
            if (oldValue.doubleValue() < newValue.doubleValue()) {
                if (beforeWidth < imageView.getFitWidth()) {
                    imageView.setFitWidth(imageView.getFitWidth() - beforeWidth);
                }
            }
        });
    }

    // ================================================================
    // Getters
    // ================================================================
    public Stage getStage() {
        return stage;
    }

    public String getPathFieldValue() {
        return urlField.getText();
    }

    // ================================================================
    // Setters
    // ================================================================
    public ImageViewerView setImage(Image image, String location) {
        this.image = image;
        imageView.setImage(image);
        urlField.setText(location);
        double width = image.getWidth();
        double height = image.getHeight();
        if (height < width) {
            if (defaultWidth < width) {
                imageView.setFitWidth(defaultWidth);
            }
        } else {
            if (defaultHeight < height) {
                imageView.setFitHeight(defaultHeight);
            }
        }
        beforeWidth = imageView.getFitWidth();
        beforeHeight = imageView.getFitHeight();
        return this;
    }

    public ImageViewerView setReloadButtonText(String text) {
        reloadButton.setText(text);
        return this;
    }

    public ImageViewerView setReloadButtonImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setBlendMode(BlendMode.DIFFERENCE);
        reloadButton.setGraphic(imageView);
        return this;
    }

    public ImageViewerView removeReloadButtonImage() {
        reloadButton.setGraphic(null);
        return this;
    }

    public ImageViewerView addReloadButtonHandler(EventHandler<ActionEvent> handler) {
        reloadButton.addEventHandler(ActionEvent.ACTION, handler);
        return this;
    }

    public ImageViewerView addOpenBrowserButtonHandler(EventHandler<ActionEvent> handler) {
        openButton.addEventHandler(ActionEvent.ACTION, handler);
        return this;
    }

    public ImageViewerView addSaveButtonHandler(EventHandler<ActionEvent> handler) {
        saveButton.addEventHandler(ActionEvent.ACTION, handler);
        return this;
    }

    public ImageViewerView show() {
        stage.show();
        return this;
    }
}
