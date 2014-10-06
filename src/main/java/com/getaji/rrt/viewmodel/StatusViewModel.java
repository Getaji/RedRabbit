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
        StaticObjects.IMAGE_CACHE.request(model.getIconUrl(), (loc, img) -> {
            view.setImage(img);
        });
        view.setTitle(model.getTitle());
        view.setText(model.getText());
        view.setVia(model.getVia(), "");

        // TODO RTハンドラ→idをもとにStatusCacheからデータを取りAccountsModelのCurrentAccountからRT
        if (model.isTwitterStatus()) {
            view.addRTButtonHandler(e -> {
                if (!view.isPushedRT()) {
                    model.retweet();
                } else {
                    model.unRetweet();
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
