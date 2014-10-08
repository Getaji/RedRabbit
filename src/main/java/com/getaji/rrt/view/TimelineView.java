package com.getaji.rrt.view;

import com.getaji.rrt.viewmodel.StatusViewModel;
import com.getaji.rrt.viewmodel.TimelineViewModel;
import com.sun.javafx.scene.control.skin.VirtualScrollBar;
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

    // ================================================================
    // Classes
    // ================================================================
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
                view.getView().setMaxWidth(size);
                isBound = true;
            }

            this.setGraphic(view.getView());
        }
    }

    // ================================================================
    // Fields
    // ================================================================
    private final ListView<StatusView> listView = new ListView<>();
    private final TimelineViewModel viewModel;
    private double scrollPosition = 0;
    private int scrollIndex;
    private VirtualScrollBar scrollBar;

    // ================================================================
    // Constructors
    // ================================================================
    public TimelineView(TimelineViewModel viewModel) {
        this.viewModel = viewModel;
        listView.setPrefWidth(400);
        listView.setCellFactory(view -> new TimelineStatusCell());
        listView.getStylesheets().add("timeline.css");
        listView.setOnScroll(e -> {
            scrollPosition = e.getTotalDeltaY();
        });
    }

    // ================================================================
    // Setters
    // ================================================================
    public TimelineView addStatus(StatusViewModel model) {
        listView.getItems().add(0, model.getView());

        if (scrollBar == null) {
            for (Node node : listView.lookupAll(".scroll-bar")) {
                if (node instanceof VirtualScrollBar) {
                    scrollBar = (VirtualScrollBar) node;
                    break;
                }
            }
            scrollBar.setVirtual(true);
            //scrollBar.adjustValue(0.5);
        }
        if (1 < listView.getItems().size()) {
        }
        if (0 < scrollPosition) {
            for (int i = 0; i < model.getView().getHeight() / 10; i++) {
                scrollBar.increment();
            }
        }

        return this;
    }

    public TimelineView clear() {
        listView.getItems().clear();
        return this;
    }

    // ================================================================
    // Getters
    // ================================================================
    public Node getView() {
        return listView;
    }

    public StatusView getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem();
    }
}
