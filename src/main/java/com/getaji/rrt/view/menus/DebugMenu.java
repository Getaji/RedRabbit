package com.getaji.rrt.view.menus;

import com.getaji.rrt.Main;
import com.getaji.rrt.util.StatusViewModelFactory;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;

import java.time.LocalDateTime;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class DebugMenu extends AbstractMenuAdapter {

    private final Menu menu;
    public DebugMenu() {
        menu = createMenu("デバッグ(_D)");

        menu.getItems().addAll(
                createItem("Statusオブジェクトの追加(_M)", e -> {
                    Main.getInstance().getMainWindowViewModel().addStatusToCurrent(
                            StatusViewModelFactory.createSimple(
                                    LocalDateTime.now().toString(),
                                    "実行可能なスレッド数に足りない場合、登録したスレッドは実行待ちになる。実行中のスレッドの処理が終わると、待機していたスレッドが実行される"
                            )
                    );
                }, new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN)),

                createItem("TLのクリア(_C)", e -> {
                    Main.getInstance().getMainWindowViewModel().getCurrentTimeline().clearStatuses();
                })
        );
        menu.mnemonicParsingProperty();
    }

    @Override
    public Menu getMenu() {
        return menu;
    }
}
