package com.getaji.rrt.view.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

import java.util.Optional;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public abstract class AbstractMenuAdapter {

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

    public MenuItem createItem(String name, EventHandler<ActionEvent> eventHandler) {
        MenuItem item = new MenuItem(name);
        Optional.ofNullable(eventHandler).ifPresent(item::setOnAction);
        item.mnemonicParsingProperty();
        return item;
    }

    public abstract Menu getMenu();
}
