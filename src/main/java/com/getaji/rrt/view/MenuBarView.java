package com.getaji.rrt.view;

import com.getaji.rrt.view.menus.AccountMenu;
import com.getaji.rrt.view.menus.DebugMenu;
import javafx.scene.control.MenuBar;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MenuBarView extends MenuBar {

    public static MenuBarView create() {
        return new MenuBarView();
    }

    private MenuBarView() {
        super();

        getMenus().addAll(
                AccountMenu.create().getMenu(),
                DebugMenu.create().getMenu()
        );
        //
    }
}
