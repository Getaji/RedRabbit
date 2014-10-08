package com.getaji.rrt.util.ui;

import com.getaji.rrt.model.StatusModel;
import com.getaji.rrt.viewmodel.StatusViewModel;

/**
 * 汎用的な{@link com.getaji.rrt.viewmodel.StatusViewModel}を生成します。
 *
 * @author Getaji
 */
class SimpleStatusBuilder implements StatusBuilder {

    // ================================================================
    // Static methods
    // ================================================================
    public static SimpleStatusBuilder newInstance(String title, String text) {
        return new SimpleStatusBuilder(title, text);
    }

    // ================================================================
    // Fields
    // ================================================================
    private final StatusModel.StatusModelBuilder builder;

    // ================================================================
    // Constructors
    // ================================================================
    private SimpleStatusBuilder(String title, String text) {
        builder = StatusModel.builder();
        builder.setIconUrl("icon_x48.png")
                .setTitle(title)
                .setText(text)
                .setVia("google");
    }

    // ================================================================
    // Getters
    // ================================================================
    @Override
    public StatusModel.StatusModelBuilder getBuilder() {
        return builder;
    }

    @Override
    public StatusViewModel buildViewModel() {
        return builder.build().createViewModel();
    }
}
