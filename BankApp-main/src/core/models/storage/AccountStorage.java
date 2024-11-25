/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Account;
import java.util.ArrayList;

/**
 *
 * @author stairs
 */
public class AccountStorage {
    private static AccountStorage instance;

    private ArrayList<Account> accounts;
    
    private AccountStorage() {
        this.accounts = new ArrayList<>();
    }
    
    public static AccountStorage getInstance() {
        if (instance == null) {
            instance = new AccountStorage();
        }
        return instance;
    }
    
    public boolean addAccount(Account account) {
        for (Account p : this.accounts) {
            if (p.getId().equals(account.getId())) {
                return false;
            }
        }
        this.accounts.add(account);
        return true;
    }
    
    public Account getAccount(String id) {
        for (Account account : this.accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }
    
    public boolean delAccount(String id) {
        for (Account account : this.accounts) {
            if (account.getId().equals(id)) {
                this.accounts.remove(account);
                return true;
            }
        }
        return false;
    }

}
