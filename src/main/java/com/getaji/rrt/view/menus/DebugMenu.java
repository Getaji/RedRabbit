package com.getaji.rrt.view.menus;

import com.getaji.rrt.Main;
import com.getaji.rrt.util.ui.StatusBuilder;
import com.getaji.rrt.view.StatusView;
import com.getaji.rrt.viewmodel.StatusViewModel;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class DebugMenu extends AbstractMenuAdapter {

    private final Menu menu;
    public DebugMenu() {
        menu = createMenu("デバッグ(_D)");

        menu.getItems().addAll(
                createItem("Statusオブジェクトの追加(_M)", e -> {
                    Main.getInstance().getMainWindowViewModel().addStatusToCurrent(
                            StatusBuilder.simple(
                                    LocalDateTime.now().toString(),
                                    "実行可能なスレッド数に足りない場合、登録したスレッドは実行待ちになる。実行中のスレッドの処理が終わると、待機していたスレッドが実行される"
                            ).buildViewModel()
                    );
                }, new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN)),

                createItem("TLのクリア(_C)", e -> {
                    Main.getInstance().getMainWindowViewModel().getCurrentTimeline().clearStatuses();
                }),

                createItem("横幅の出力", e -> {
                    StatusViewModel selectedStatus = Main.getInstance().getMainWindowViewModel().getCurrentTimeline().getSelectedStatus();
                    StatusView statusView = selectedStatus.getView();
                    log.debug(String.format("width:%s height:%s", statusView.getWidth(), statusView.getHeight()));
                })
        );
        menu.mnemonicParsingProperty();
    }

    @Override
    public Menu getMenu() {
        return menu;
    }
}
