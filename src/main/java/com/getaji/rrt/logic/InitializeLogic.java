package com.getaji.rrt.logic;

import com.getaji.rrt.logic.config.DataSaveLogic;
import com.getaji.rrt.model.AccountsModel;
import com.getaji.rrt.model.SystemModel;
import com.getaji.rrt.model.UIModel;
import com.getaji.rrt.view.MainWindowView;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Optional;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class InitializeLogic {
    public InitializeLogic() {}

    public void initialize() throws InitializeException {
        Optional<MainLogic> mainLogicOptional = Optional.ofNullable(MainLogic.getInstance());
        mainLogicOptional.orElseThrow(InitializeException::new);

        MainLogic.MODEL_ADAPTER.initialize();
        MainLogic.LOGIC_ADAPTER.initialize();
        MainLogic.VIEW_ADAPTER.initialize();

        final MainLogic mainLogic = mainLogicOptional.get();
        final MainWindowView mainWindowView = Optional.of(MainLogic.VIEW_ADAPTER.getMainWindowView()).get();
        final UIModel uiModel = Optional.of(MainLogic.MODEL_ADAPTER.getUiModel()).get();
        final SystemModel systemModel = Optional.of(MainLogic.MODEL_ADAPTER.getSystemModel()).get();

        final AccountsModel accountsModel = Optional.of(MainLogic.MODEL_ADAPTER.getAccountsModel()).get();
        Arrays.stream(DataSaveLogic.loadTokens()).forEach(accountsModel::addTwitter);

        Stage stage = mainLogic.getStage();
        mainWindowView.setStage(stage)
                      .setTitle(uiModel.getTitle())
                      .setWidth(uiModel.getWidth())
                      .setHeight(uiModel.getHeight());
    }
}
