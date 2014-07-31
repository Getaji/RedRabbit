package com.getaji.rrt.view.menus;

import com.getaji.rrt.view.AccountManageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;

import java.util.Optional;


/**
 * javadoc here.
 *
 * @author Getaji
 */
public class AccountMenu extends Menu {
    public AccountMenu() {
        super("アカウント(_A)");

        getItems().addAll(
                createItem("アカウントの管理(_M)", e -> {
                    AccountManageView.showWindow();
                }, new KeyCharacterCombination("M", KeyCombination.CONTROL_DOWN))
        );
        mnemonicParsingProperty();
    }

    public Menu createMenu(String name) {
        Menu menu = new Menu(name);
        return menu;
    }

    public MenuItem createItem(String name, EventHandler<ActionEvent> eventHandler, KeyCombination shortcut) {
        MenuItem item = new MenuItem(name);
        Optional.ofNullable(eventHandler).ifPresent(item::setOnAction);
        Optional.ofNullable(shortcut).ifPresent(item::setAccelerator);
        item.mnemonicParsingProperty();
        return item;
    }
}
