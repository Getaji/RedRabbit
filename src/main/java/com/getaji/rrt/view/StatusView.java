package com.getaji.rrt.view;

import com.getaji.rrt.util.FXHelper;
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

import java.net.URI;
import java.net.URISyntaxException;

/**
 * ステータスの表示を行うクラス。
 *
 * <h1>参照</h1>
 * <ul>
 *     <li>{@link com.getaji.rrt.viewmodel.StatusViewModel}</li>
 *     <li>{@link com.getaji.rrt.model.StatusModel}</li>
 * </ul>
 *
 * @author Getaji
 */
@Log4j2
public class StatusView {

    // ================================================================
    // Fields
    // ================================================================
    private final BorderPane borderPane = new BorderPane();
    private final ImageView imageView = new ImageView();
    private final BorderPane centerBorderPane = new BorderPane();
    private final HBox titleBox = new HBox();
    private final Text title = new Text();
    private final Hyperlink date;
    private final TextFlow text = new TextFlow();
    private final HBox bottomNodes = new HBox();
    private final ToggleButton buttonRT = new ToggleButton("RT");
    private final ToggleButton buttonFav = new ToggleButton("Fav");
    private final Hyperlink via;
    private URI dateUri = null;
    private URI viaUri = null;

    // ================================================================
    // Constructors
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
        date = FXHelper.createHyperlink("none", this::getDateUri);
        titleBox.getChildren().addAll(title, date);
        titleBox.setSpacing(4);
        title.setFont(Font.font(null, FontWeight.BOLD, title.getFont().getSize()));
        // --------------------------------
        via = FXHelper.createHyperlink("via", this::getViaURI);
        bottomNodes.getChildren().addAll(buttonRT, buttonFav, via);
        bottomNodes.setAlignment(Pos.CENTER_RIGHT);
        bottomNodes.setSpacing(3);
        bottomNodes.setPadding(new Insets(5, 0, 5, 0));
        FXHelper.setButtonTextSwitching(buttonRT, "RT", "RTed");
        FXHelper.setButtonTextSwitching(buttonFav, "Fav", "Faved");
        via.setFont(Font.font(via.getFont().getSize() - 3));
        // ================================
    }

    // ================================================================
    // Setters
    // ================================================================
    public StatusView setTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public StatusView setDate(String date) {
        this.date.setText(date);
        return this;
    }

    public StatusView setDate(String date, String url) {
        this.date.setText(date);
        if (url != null && !url.isEmpty()) {
            try {
                this.dateUri = new URI(url);
            } catch (URISyntaxException e) {
                log.error("URISyntaxException " + e.getMessage());
            }
        }
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
                this.viaUri = new URI(url);
            } catch (URISyntaxException e) {
                log.error("URISyntaxException " + e.getMessage());
            }
        }
        return this;
    }

    public StatusView setRTButtonSelected(boolean selected) {
        buttonRT.setSelected(selected);
        return this;
    }

    public StatusView setFavButtonSelected(boolean selected) {
        buttonFav.setSelected(selected);
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

    // ================================================================
    // Getters
    // ================================================================
    public Pane getView() {
        return borderPane;
    }

    public boolean isPushedRT() {
        return buttonRT.isSelected();
    }

    public boolean isPushedFav() {
        return buttonFav.isSelected();
    }

    public URI getViaURI() {
        return viaUri;
    }

    public URI getDateUri() {
        return dateUri;
    }
}
