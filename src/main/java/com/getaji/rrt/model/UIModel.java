package com.getaji.rrt.model;

import com.getaji.rrt.logic.MainLogic;
import lombok.Data;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Data
public class UIModel {
    private int width;
    private int height;
    public static final String TITLE_BASE = "RedRabbit";

    public String getTitle() {
        return TITLE_BASE + " " + MainLogic.MODEL_ADAPTER.getSystemModel().getFormattedVersion();
    }
}
