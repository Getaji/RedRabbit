package com.getaji.rrt.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class StatusView {

    // ================================================================
    // FIELDS
    // ================================================================
    private final BorderPane borderPane = new BorderPane();

    private final ImageView imageView = new ImageView();
    //private final Label titleLabel = new Label();
    private final BorderPane centerBorderPane = new BorderPane();
    private final HBox titleBox = new HBox();
    private final Text title = new Text();
    private final Hyperlink date = new Hyperlink("none");
    private final TextFlow text = new TextFlow();
    private final HBox bottomNodes = new HBox();
    private final ToggleButton buttonRT = new ToggleButton("RT");
    private final ToggleButton buttonFav = new ToggleButton("Fav");
    private final Hyperlink via = new Hyperlink();
    private URI uri = null;

    // ================================================================
    // CONSTRUCTORS
    // ================================================================
    public StatusView() {
        // ============ Nodes ============
        borderPane.setLeft(imageView);
        borderPane.setCenter(centerBorderPane);
        // --------------------------------
        BorderPane.setMargin(imageView, new Insets(3, 5, 0, 0));
        // --------------------------------
        centerBorderPane.setTop(titleBox);
        centerBorderPane.setCenter(text);
        centerBorderPane.setBottom(bottomNodes);
        // --------------------------------
        titleBox.getChildren().addAll(title, date);
        title.setFont(Font.font(null, FontWeight.BOLD, 14));
        date.setAlignment(Pos.CENTER_RIGHT);
        // --------------------------------
        bottomNodes.getChildren().addAll(buttonRT, buttonFav, via);
        bottomNodes.setAlignment(Pos.CENTER_RIGHT);
        bottomNodes.setSpacing(3);
        buttonRT.addEventHandler(ActionEvent.ACTION, e -> {
            if (buttonRT.isSelected()) {
                buttonRT.setText("RTed");
            } else {
                buttonRT.setText("RT");
            }
        });
        buttonFav.addEventHandler(ActionEvent.ACTION, e -> {
            if (buttonFav.isSelected()) {
                buttonFav.setText("Faved");
            } else {
                buttonFav.setText("Fav");
            }
        });
        via.setFont(Font.font(via.getFont().getSize() - 3));
        via.setOnAction(e -> {
            try {
                if (uri != null) {
                    java.awt.Desktop.getDesktop().browse(uri);
                }
            } catch (IOException e1) {
                log.error("Failed open browser " + e1.getMessage());
            }
        });
        // ================================
        //borderPane.setPrefHeight(titleLabel.getHeight() + textFlow.getHeight());
    }

    // ================================================================
    // METHODS
    // ================================================================

    // SETTERS ====================================
    public StatusView setTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public StatusView setText(final String stringText) {
        final ObservableList<Node> textChildren = this.text.getChildren();
        if (1 <= textChildren.size()) { // recycle text node
            for (Node node : textChildren) {
                if (node instanceof Text) { // set text if node of text instance
                    ((Text) node).setText(stringText);
                    textChildren.clear();
                    textChildren.add(node);
                    return this;
                }
            }
        }
        textChildren.clear();
        textChildren.addAll(new Text(stringText));
        return this;
    }

    public StatusView setImage(Image image) {
        this.imageView.setImage(image);
        return this;
    }

    public StatusView setVia(String text, String url) {
        this.via.setText("via " + text);
        if (url != null && !url.isEmpty()) {
            try {
                this.uri = new URI(url);
            } catch (URISyntaxException e) {
                log.error("URISyntaxException " + e.getMessage());
            }
        }
        return this;
    }

    public StatusView addRTButtonHandler(EventHandler<ActionEvent> handler) {
        buttonRT.addEventHandler(ActionEvent.ACTION, handler);
        return this;
    }

    public StatusView addFavButtonHandler(EventHandler<ActionEvent> handler) {
        buttonFav.addEventHandler(ActionEvent.ACTION, handler);
        return this;
    }

    // GETTERS ====================================
    public Pane getView() {
        return borderPane;
    }

    public boolean isPushedRT() {
        return buttonRT.isSelected();
    }

    public boolean isPushedFav() {
        return buttonFav.isSelected();
    }
}
