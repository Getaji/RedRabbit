package com.getaji.rrt.view;

import com.getaji.rrt.logic.MainLogic;
import com.getaji.rrt.logic.TwitterOAuthLogic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Optional;
import java.util.Set;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class AccountManageView extends Stage {
    private final BorderPane topPane = new BorderPane();
    private final ListView<String> listView = new ListView<>();
    private final VBox buttonsBox = new VBox();
    public AccountManageView() {
        setTitle("アカウントの管理");
        setScene(new Scene(topPane));
        setWidth(320);
        setHeight(240);
        setResizable(false);

        topPane.setMargin(listView, new Insets(5, 5, 5, 5));
        topPane.setMargin(buttonsBox, new Insets(5, 5, 5, 5));

        topPane.setCenter(listView);
        topPane.setRight(buttonsBox);

        buttonsBox.setAlignment(Pos.TOP_RIGHT);
        buttonsBox.setSpacing(1.5D);
        buttonsBox.getChildren().addAll(
                createButton("デフォルトに設定", null),
                createSeparator(),
                createButton("アカウントの追加", (e) -> {
                    if (TwitterOAuthLogic.authorization()) {
                        loadAccounts();
                    }
                }),
                createButton("アカウントの編集", null),
                createButton("アカウントの削除", null)
        );
    }

    public static void showWindow() {
        new AccountManageView().show();
    }

    public Button createButton(String name, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(name);
        Optional.ofNullable(eventHandler).ifPresent(button::setOnAction);
        return button;
    }

    public Separator createSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(5, 0, 5, 0));
        return separator;
    }

    public void loadAccounts() {
        listView.getItems().clear();
        Set<Twitter> twitters = MainLogic.MODEL_ADAPTER.getAccountsModel().twitters;
        twitters.forEach(twitter -> {
            try {
                listView.getItems().add(twitter.getScreenName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        });
    }
}
