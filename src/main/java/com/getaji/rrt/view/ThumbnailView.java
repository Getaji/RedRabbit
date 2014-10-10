package com.getaji.rrt.view;

import com.getaji.rrt.viewmodel.ImageViewer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;

import java.net.URISyntaxException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class ThumbnailView {

    public static ThumbnailView create() {
        return new ThumbnailView();
    }

    private final BorderPane pane = new BorderPane();
    private final ImageView imageView = new ImageView();
    private Image plainImage;
    private int height = 64;

    private ThumbnailView() {
        pane.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        imageView.setPreserveRatio(true);
        pane.setStyle("-fx-border-color: #555;");
        pane.setCursor(Cursor.HAND);
    }

    public ThumbnailView setImage(Image image, String location) {
        if (plainImage == null) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    ImageViewer.create().setImage(image, location).showView();
                } catch (URISyntaxException e1) {
                    log.error(e1);
                }
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
    }

    public Node getNode() {
        return pane;
    }

    public Pane getPane() {
        return pane;
    }
}
