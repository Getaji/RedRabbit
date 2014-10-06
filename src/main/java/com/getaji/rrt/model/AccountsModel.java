package com.getaji.rrt.model;

import com.getaji.rrt.util.Wrapper;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class AccountsModel {
    @Getter private Set<Account> accounts = new HashSet<>();
    private Wrapper<Account> currentAccount = Wrapper.empty();

    // ================================================================
    // Getter
    // ================================================================
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

    public Account getCurrentAccount() {
        return currentAccount.get();
    }

    public boolean isCurrentAccountPresent() {
        return currentAccount.isPresent();
    }

    public void addCurrentAccountChangeHandler(Consumer<Wrapper<Account>> handler) {
        currentAccount.addValueSetHandler(handler);
    }

    // ================================================================
    // Setter
    // ================================================================
    public void addAccount(Account account) {
        if (accounts.isEmpty()) {
            currentAccount.set(account);
        }

        accounts.add(account);
    }
}
