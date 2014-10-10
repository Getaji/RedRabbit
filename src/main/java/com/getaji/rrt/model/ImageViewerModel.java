package com.getaji.rrt.model;

import com.getaji.rrt.util.FileUtil;
import com.getaji.rrt.view.ImageViewerView;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class ImageViewerModel {

    // ================================================================
    // Static factory methods
    // ================================================================
    public static ImageViewerModel create(ImageViewerView view) {
        return new ImageViewerModel(view);
    }

    // ================================================================
    // Static fields
    // ================================================================
    private static File lastSavedLocation;

    // ================================================================
    // Fields
    // ================================================================
    private final ImageViewerView view;

    private Image image = null;
    private String location = "";
    private URI uri = null;
    private FileChooser fileChooser = new FileChooser();

    // ================================================================
    // Constructors
    // ================================================================
    private ImageViewerModel(ImageViewerView view) {
        this.view = view;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("名前を付けて保存");
    }

    // ================================================================
    // Getters
    // ================================================================
    public boolean isEmpty() {
        return image == null;
    }

    // ================================================================
    // Setters
    // ================================================================
    public void setImage(Image image, String location) throws URISyntaxException {
        this.image = image;
        setLocation(location);
    }

    private void setLocation(String location) throws URISyntaxException {
        if (!this.location.equals(location)) {
            this.location = location;
            uri = new URI(location);
        }
    }

    private void setFileChooserProperty() {
        if (lastSavedLocation == null) {
            String user_home = System.getProperty("user.home");
            if (user_home != null && !user_home.isEmpty()) {
                fileChooser.setInitialDirectory(new File(user_home));
            } else {
                fileChooser.setInitialDirectory(new File("./"));
            }
        } else {
            fileChooser.setInitialDirectory(lastSavedLocation);
        }
        fileChooser.setInitialFileName(FileUtil.getFileName(location));
        ObservableList<FileChooser.ExtensionFilter> filters = fileChooser.getExtensionFilters();
        if (filters.isEmpty()) {
            filters.add(new FileChooser.ExtensionFilter("すべてのファイル", "*.*"));
        }
    }

    // ================================================================
    // Commands
    // ================================================================
    public void openInBrowser() throws IOException {
        if (!isEmpty()) {
            Desktop.getDesktop().browse(uri);
        }
    }

    public void save() throws IOException {
        if (!isEmpty()) {
            setFileChooserProperty();
            File file = fileChooser.showSaveDialog(view.getStage());
            if (file != null) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, FileUtil.getExtension(file.getPath()), file);
                lastSavedLocation = file.getParentFile();
            }
        }
    }
}
