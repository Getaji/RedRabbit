package com.getaji.rrt.view.menus;

import com.getaji.rrt.view.AccountManageView;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;


/**
 * javadoc here.
 *
 * @author Getaji
*/
public class AccountMenu extends AbstractMenuAdapter {

    public static AccountMenu create() {
        return new AccountMenu();
    }

    private final Menu menu;
    private AccountMenu() {
        menu = createMenu("アカウント(_A)");

        menu.getItems().addAll(
                createItem("アカウントの管理(_M)", e -> {
                    AccountManageView.showWindow();
                }, new KeyCharacterCombination("M", KeyCombination.CONTROL_DOWN))
        );
        menu.mnemonicParsingProperty();
    }

    @Override
    public Menu getMenu() {
        return menu;
    }
}
