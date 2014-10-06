package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.view.StatusView;
import lombok.Getter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class StatusViewModel {

    @Getter private final StatusView view = new StatusView();
    @Getter private final StatusModel model;

    /* ================ CONSTRUCTORS ================ */
    public StatusViewModel(StatusModel model) {
        this.model = model;
        StaticObjects.getImageCache().request(model.getIconUrl(), (loc, img) -> {
            view.setImage(img);
        });
        view.setTitle(model.getTitle());
        view.setDate(model.getDate(), model.getDateUrl());
        view.setText(model.getText());
        view.setVia(model.getVia(), model.getViaUrl());

        // TODO RTハンドラ→idをもとにStatusCacheからデータを取りAccountsModelのCurrentAccountからRT
        if (model.isTwitterStatus()) {
            view.addRTButtonHandler(e -> {
                if (!view.isPushedRT()) {
                    boolean isComplete = model.retweet();
                    view.setRTButtonSelected(isComplete);
                } else {
                    boolean isComplete = model.unRetweet();
                    view.setRTButtonSelected(!isComplete);
                }
            });
            view.addFavButtonHandler(e -> {
                if (!view.isPushedFav()) {
                    model.favorite();
                } else {
                    model.unFavorite();
                }
            });
        }
    }
}
