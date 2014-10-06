package com.getaji.rrt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class AccountsModel {
    @Getter private Set<Account> accounts = new HashSet<>();
    @Getter @Setter private Account currentAccount;

    public void addAccount(Account account) {
        if (accounts.isEmpty()) {
            currentAccount = account;
        }

        accounts.add(account);
    }

    /**
     * 指定したscreenNameを持つAccountインスタンスを返します。存在しない場合はnullを返します。
     * @param screenName
     * @return
     */
    public Account getAccount(String screenName) {
        for (Account account : accounts) {
            String _screenName = account.getScreenName();
            if (screenName.equals(_screenName)) {
                return account;
            }
        }
        return null;
    }

    public Account getAccount(long id) {
        for (Account account : accounts) {
            if (id == account.getId()) {
                return account;
            }
        }
        return null;
    }
}
