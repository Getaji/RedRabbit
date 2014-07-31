package com.getaji.rrt.view;

import com.getaji.rrt.view.menus.AccountMenu;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MenuBarView extends javafx.scene.control.MenuBar {
    public MenuBarView() {
        super();

        getMenus().addAll(
                new AccountMenu()
        );
        //
    }
}
