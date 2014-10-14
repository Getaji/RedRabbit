package com.getaji.rrt.view;

import com.getaji.rrt.util.ui.FXFontBuilder;
import com.getaji.rrt.util.ui.FXHelper;
import com.getaji.rrt.viewmodel.StatusViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

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
    // Static factory methods
    // ================================================================
    public static StatusView create(StatusViewModel viewModel) {
        return new StatusView(viewModel);
    }

    // ================================================================
    // Fields
    // ================================================================
    private final BorderPane borderPane = new BorderPane();
    private final VBox iconBox = new VBox();
    private final ImageView imageView = new ImageView();
    private final ImageView subImageView = new ImageView();
    private final BorderPane centerBorderPane = new BorderPane();
    private final BorderPane titlePane = new BorderPane();
    private final Text title = new Text();
    private final Hyperlink date;
    private final TextFlow text = new TextFlow();
    private final VBox bottomsBox = new VBox();
    private final BorderPane bottomPane = new BorderPane();
    private final HBox buttonsBox = new HBox();
    private final ToggleButton buttonRT = new ToggleButton("");
    private final ToggleButton buttonFav = new ToggleButton("");
    private final Button buttonMore = new Button("...▼");
    private final Hyperlink via;
    private final StatusViewModel viewModel;
    private URI dateUri = null;
    private URI viaUri = null;

    // ================================================================
    // Constructors
    // ================================================================
    public StatusView(StatusViewModel viewModel) {
        this.viewModel = viewModel;

        // ============ Nodes ============
        borderPane.setLeft(iconBox);
        borderPane.setCenter(centerBorderPane);
        // --------------------------------
        iconBox.getChildren().addAll(imageView, subImageView);
        iconBox.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setMargin(iconBox, new Insets(3, 5, 0, 0));
        // --------------------------------
        centerBorderPane.setTop(titlePane);
        centerBorderPane.setCenter(text);
        centerBorderPane.setBottom(bottomsBox);
        // --------------------------------
        date = FXHelper.createHyperlink("none", this::getDateUri);
        titlePane.setLeft(title);
        titlePane.setRight(date);
        title.setFont(FXFontBuilder.of(title.getFont())
                .add(1).weight(FontWeight.BOLD).build());
        date.setAlignment(Pos.CENTER_LEFT);
        // --------------------------------
        bottomsBox.getChildren().add(bottomPane);
        bottomsBox.setSpacing(2);
        via = FXHelper.createHyperlink("via", this::getViaURI);
        bottomPane.setLeft(buttonsBox);
        bottomPane.setRight(via);
        buttonsBox.getChildren().addAll(buttonRT, buttonFav, buttonMore);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.setSpacing(3);
        bottomPane.setPadding(new Insets(4, 0, 4, 0));
        buttonRT.getStylesheets().add("css/button_rt.css");
        buttonFav.getStylesheets().add("css/button_fav.css");
        buttonMore.addEventHandler(ActionEvent.ACTION, e -> {
            log.debug(String.format("width:%s height:%s", borderPane.getWidth(), borderPane.getHeight()));
        });
        via.setFont(FXFontBuilder.of(via.getFont())
                .sub(3).build());
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

    public StatusView setTextAll(Node... textNode) {
        text.getChildren().addAll(textNode);
        return this;
    }

    public StatusView setTextAll(Collection<Node> textNode) {
        text.getChildren().addAll(textNode);
        return this;
    }

    public StatusView setImage(Image image) {
        this.imageView.setImage(image);
        this.iconBox.requestLayout();
        return this;
    }

    public StatusView setSubImage(Image image) {
        this.subImageView.setImage(image);
        this.iconBox.requestLayout();
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

    public StatusView setRTCount(long count) {
        buttonRT.setText(String.valueOf(count));
        return this;
    }

    public StatusView setFavCount(long count) {
        buttonFav.setText(String.valueOf(count));
        return this;
    }

    public StatusView addBottomNode(Node node) {
        bottomsBox.getChildren().add(node);
        return this;
    }

    public StatusView update() {
        bottomsBox.requestLayout();
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

    public double getWidth() {
        return borderPane.getWidth();
    }

    public double getHeight() {
        return borderPane.getHeight();
    }

    public StatusViewModel getViewModel() {
        return viewModel;
    }
}
