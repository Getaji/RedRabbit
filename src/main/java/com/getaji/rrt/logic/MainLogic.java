package com.getaji.rrt.logic;

import com.getaji.rrt.adapter.LogicAdapter;
import com.getaji.rrt.adapter.ModelAdapter;
import com.getaji.rrt.adapter.ViewAdapter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class MainLogic extends Application {

    private static MainLogic instance;

    public static final ModelAdapter MODEL_ADAPTER = new ModelAdapter();
    public static final LogicAdapter LOGIC_ADAPTER = new LogicAdapter();
    public static final ViewAdapter VIEW_ADAPTER = new ViewAdapter();

    @Getter private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.stage = stage;

        Platform.runLater(() -> {
            InitializeLogic initializeLogic = new InitializeLogic();
            try {
                initializeLogic.initialize();

                stage.show();
            } catch (InitializeException e) {
                e.printStackTrace();
            }
        });
    }

    public static MainLogic getInstance() {
        return instance;
    }
}
