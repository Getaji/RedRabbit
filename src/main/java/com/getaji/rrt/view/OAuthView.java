package com.getaji.rrt.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class OAuthView extends Stage {

    private final Stage stage = new Stage();

    private final GridPane gridPane = new GridPane();
    private final TextField field = new TextField();
    private final Button buttonOK = new Button("認証");
    private final Button buttonCancel = new Button("キャンセル");

    public OAuthView() {
        setTitle("認証");

        gridPane.setVgap(3);
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        gridPane.add(new Label("認証して出てきた数値を入力してください。"), 0, 0);

        field.setAlignment(Pos.CENTER);
        gridPane.add(field, 0, 1);

        buttonCancel.setOnAction(e -> this.close());
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(buttonOK, buttonCancel);
        gridPane.add(buttonBox, 0, 2);

        setScene(new Scene(gridPane));
    }

    public OAuthView setOnPressedOK(EventHandler<ActionEvent> eventHandler) {
        buttonOK.setOnAction(eventHandler);
        return this;
    }

    public String getValue() {
        return field.getText();
    }

    public static OAuthView showWindow(Consumer<String> whenPressed) {
        OAuthView oAuthView = new OAuthView();
        oAuthView.setOnPressedOK(e -> whenPressed.accept(oAuthView.getValue()));
        oAuthView.show();
        return oAuthView;
    }
}
