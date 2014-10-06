package com.getaji.rrt.view;

import com.getaji.rrt.viewmodel.StatusViewModel;
import com.getaji.rrt.viewmodel.TimelineViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class TimelineView {

    private final ListView<StatusView> listView = new ListView<>();

    private final TimelineViewModel viewModel;

    private final class TimelineStatusCell extends ListCell<StatusView> {

        private boolean isBound = false;

        public TimelineStatusCell() {
            super();
            super.setWidth(listView.getWidth());
        }

        @Override
        protected void updateItem(StatusView view, boolean empty) {
            super.updateItem(view, empty);

            if (view == null) { return; }

            double size = viewModel.getModel().getWidth();
            if (!isBound || view.getView().getPrefWidth() != size) {
                /*view.getView().prefWidthProperty().bind(
                        listView.widthProperty().subtract(20)
                );*/
                view.getView().setPrefWidth(size);
                isBound = true;
            }

            this.setGraphic(view.getView());
        }
    }

    public TimelineView(TimelineViewModel viewModel) {
        this.viewModel = viewModel;
        ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                final Boolean focused = newValue;
                if (focused) {

                }
            }
        };
        listView.focusedProperty().addListener(listener);
        listView.setPrefWidth(400);
        listView.setCellFactory(view -> new TimelineStatusCell());
        listView.getStylesheets().add("timeline.css");
    }

    public TimelineView addStatus(StatusViewModel model) {

        listView.getItems().add(0, model.getView());
        return this;
    }

    public Node getView() {
        return listView;
    }

    public TimelineView clear() {
        listView.getItems().clear();
        return this;
    }

    //
}
