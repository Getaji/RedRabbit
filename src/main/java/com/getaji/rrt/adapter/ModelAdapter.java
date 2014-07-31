package com.getaji.rrt.adapter;

import com.getaji.rrt.model.AccountsModel;
import com.getaji.rrt.model.SystemModel;
import com.getaji.rrt.model.UIModel;
import lombok.Getter;

/**
 * javadoc here.
 *
 * @author Getaji
 */
@Getter
public class ModelAdapter {
    private SystemModel systemModel;
    private UIModel uiModel;
    private AccountsModel accountsModel;

    public void initialize() {
        systemModel = new SystemModel();
        uiModel = new UIModel();
        accountsModel = new AccountsModel();
    }
}
