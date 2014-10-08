package com.getaji.rrt.view;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private final HBox imagesBox = new HBox();
    //private final List<Image> images = new ArrayList<>()

    public ImageViewer() {
        stage = new Stage();
        stage.setTitle("画像詳細");
        stage.setScene(new Scene(borderPane));
        stage.setWidth(400);
        stage.setWidth(400);
        borderPane.setCenter(imageView);
    }/*

    public ImageViewer addImage(Image image) {
        ImageView thumbnail = new ImageView(image);
        thumbnail.
    }*/
}
