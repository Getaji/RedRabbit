package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.ImageViewerModel;
import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.view.ImageViewerView;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class ImageViewer {

    public static ImageViewer create() {
        return new ImageViewer();
    }

    private final ImageViewerView view;
    private final ImageViewerModel model;

    public ImageViewer() {
        view = ImageViewerView.create();
        model = ImageViewerModel.create(view);

        view.addReloadButtonHandler(e -> {
            StaticObjects.getImageCache().request("icons/loading.gif", (loc, img) -> {
                view.setReloadButtonText("");
                view.setReloadButtonImage(img);
            });
            String location = view.getPathFieldValue();
            StaticObjects.getImageCache().requestNew(location, (loc, img) -> {
                try {
                    this.setImage(img, loc);
                } catch (URISyntaxException e1) {
                    log.error(e1);
                } finally {
                    view.setReloadButtonText("再読込");
                    view.removeReloadButtonImage();
                }
            });
        });
        view.addOpenBrowserButtonHandler(e -> {
            try {
                model.openInBrowser();
            } catch (IOException e1) {
                log.error(e1);
            }
        });
        view.addSaveButtonHandler(e -> {
            try {
                model.save();
            } catch (IOException e1) {
                log.error(e1);
            }
        });
    }

    public ImageViewer setImage(Image image, String location) throws URISyntaxException {
        view.setImage(image, location);
        model.setImage(image, location);
        return this;
    }

    public ImageViewer showView() {
        view.show();
        return this;
    }
}
