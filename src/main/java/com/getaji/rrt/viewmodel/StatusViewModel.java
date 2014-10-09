package com.getaji.rrt.viewmodel;

import com.getaji.rrt.model.StaticObjects;
import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.view.StatusView;
import javafx.scene.Node;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Log4j2
public class StatusViewModel {

    // ================================================================
    // Fields
    // ================================================================
    @Getter private final StatusView view = new StatusView(this);
    @Getter private final StatusModel model;

    // ================================================================
    // Constructors
    // ================================================================
    public StatusViewModel(StatusModel model) {
        this.model = model;
        StaticObjects.getImageCache().request(model.getIconUrl(), (loc, img) -> {
            view.setImage(img);
            log.debug(img == null);
        });
        if (!model.getSubIconUrl().isEmpty()) {
            StaticObjects.getImageCache().request(model.getSubIconUrl(), (loc, img) -> {
                view.setSubImage(img);
            });
        }
        view.setTitle(model.getTitle());
        view.setDate(model.getDate(), model.getDateUrl());
        view.setText(model.getText());
        view.setRTCount(model.getRetweets());
        view.setFavCount(model.getFavorites());
        view.setVia(model.getVia(), model.getViaUrl());

        // TODO 全フィールド分の以下略
        //model.addTitleSetHandler(w -> view.setTitle(w.get()));
        //model.addTextSetHandler(w -> view.setText(w.get()));

        if (model.isTwitterStatus()) {
            view.addRTButtonHandler(e -> {
                if (view.isPushedRT()) {
                    boolean isComplete = model.retweet();
                    view.setRTButtonSelected(isComplete);
                } else {
                    boolean isComplete = model.unRetweet();
                    view.setRTButtonSelected(!isComplete);
                }
            });
            view.addFavButtonHandler(e -> {
                if (view.isPushedFav()) {
                    boolean isComplete = model.favorite();
                    view.setFavButtonSelected(isComplete);
                } else {
                    boolean isComplete = model.unFavorite();
                    view.setFavButtonSelected(!isComplete);
                }
            });
        }
    }

    public StatusViewModel setTextNodes(List<Node> textNodes, String plainText) {
        model.setText(plainText);
        view.setTextAll(textNodes);
        return this;
    }
}
