package com.getaji.rrt.adapter;

import com.getaji.rrt.view.MainWindowView;
import lombok.Getter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Getter
public class ViewAdapter {
    private MainWindowView mainWindowView;

    public void initialize() {
        mainWindowView = new MainWindowView();
    }
}
