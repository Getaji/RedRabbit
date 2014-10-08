package com.getaji.rrt.util.ui;

import com.getaji.rrt.util.Processor;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class FXHelper {

    public static Hyperlink createHyperlink(String caption, Processor processor) {
        Hyperlink hyperlink = new Hyperlink(caption);
        hyperlink.setOnAction(e -> processor.process());
        return hyperlink;
    }

    public static Hyperlink createHyperlink(String caption, String url,
                                            Processor... processors)
            throws URISyntaxException {
        URI uri = url == null || url.isEmpty() ? null : new URI(url);
        return createHyperlink(shorten(caption, 32), uri);
    }

    public static Hyperlink createHyperlink(String caption, URI uri,
                                            Processor... processors) {
        Hyperlink hyperlink = new Hyperlink(shorten(caption, 32));
        hyperlink.setOnAction(event -> {
            if (uri != null) {
                try {
                    Desktop.getDesktop().browse(uri);
                } catch (IOException exception) {
                    log.error("Can't open uri", exception);
                }
            }
            for (Processor processor : processors) {
                processor.process();
            }
        });
        return hyperlink;
    }

    public static Hyperlink createHyperlink(String caption,
                                            Supplier<URI> uriGetter,
                                            Processor... processors) {
        Hyperlink hyperlink = new Hyperlink(shorten(caption, 32));
        hyperlink.setOnAction(event -> {
            try {
                URI uri = uriGetter.get();
                if (uri != null) {
                    Desktop.getDesktop().browse(uriGetter.get());
                }
            } catch (IOException exception) {
                log.error("Can't open uri", exception);
            }
        });
        return hyperlink;
    }

    public static Hyperlink createHyperlink(String caption, URI uri,
                                            Consumer<IOException> exceptionConsumer,
                                            Processor... processors) {
        Hyperlink hyperlink = new Hyperlink(shorten(caption, 32));
        hyperlink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException exception) {
                exceptionConsumer.accept(exception);
            }
        });
        return hyperlink;
    }

    public static Hyperlink createHyperlink(String caption,
                                            Supplier<URI> uriGetter,
                                            Consumer<IOException> exceptionConsumer, Processor... processors) {
        Hyperlink hyperlink = new Hyperlink(shorten(caption, 32));
        hyperlink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(uriGetter.get());
            } catch (IOException exception) {
                exceptionConsumer.accept(exception);
            }
        });
        return hyperlink;
    }

    private static String shorten(String text, int length) {
        if (length < text.length()) {
            return text.substring(0, length) + "...";
        }
        return text;
    }

    public static void setButtonTextSwitching(ToggleButton button, String popped, String pushed) {
        button.addEventHandler(ActionEvent.ACTION, e -> {
            if (button.isSelected()) {
                button.setText(pushed);
            } else {
                button.setText(popped);
            }
        });
    }
}
