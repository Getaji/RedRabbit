package com.getaji.rrt.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class ImageViewer {
    private final Stage stage;
    private final BorderPane borderPane = new BorderPane();
    private final ImageView imageView = new ImageView();

    public ImageViewer() {
        stage = new Stage();
        stage.setTitle("画像詳細");
        stage.setScene(new Scene(borderPane));
        stage.setWidth(400);
        stage.setWidth(400);
        borderPane.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        imageView.setPreserveRatio(true);
    }

    public ImageViewer setImage(Image image) {
        imageView.setImage(image);
        if (image.getWidth() < image.getHeight()) {
            imageView.setFitHeight(400);
        } else {
            imageView.setFitWidth(400);
        }
        return this;
    }

    public ImageViewer show() {
        stage.show();
        return this;
    }
}
