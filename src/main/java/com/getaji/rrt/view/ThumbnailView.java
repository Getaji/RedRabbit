package com.getaji.rrt.view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class ThumbnailView {

    private final BorderPane pane = new BorderPane();
    private final ImageView imageView = new ImageView();
    private Image plainImage;
    private int height = 64;

    public ThumbnailView() {
        pane.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER_LEFT);
        imageView.setPreserveRatio(true);
        pane.setStyle("-fx-border-color: #555;");
    }

    public ThumbnailView(Image image) {
        this();
        setImage(image);
    }

    public ThumbnailView setImage(Image image) {
        if (plainImage == null) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                new ImageViewer().setImage(image).show();
            });
        }
        plainImage = image;
        WritableImage resizeImage = new WritableImage(image.getPixelReader(),
                0, (int) (image.getHeight() / 2 - height / 2),
                (int) image.getWidth(), height + 2);

        imageView.setImage(resizeImage);
        fit(image);
        return this;
    }

    private void fit(Image image) {
        if (300 < image.getWidth()) {
            imageView.setFitWidth(300);
            pane.setMaxWidth(300);
        }
        pane.setCursor(Cursor.HAND);
    }

    public Node getNode() {
        return pane;
    }

    public Pane getPane() {
        return pane;
    }
}
