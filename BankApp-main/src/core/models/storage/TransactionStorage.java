/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Transaction;
import java.util.ArrayList;

/**
 *
 * @author stairs
 */
public class TransactionStorage {
  private static TransactionStorage instance;

    private ArrayList<Transaction> transactions;
    
    private TransactionStorage() {
        this.transactions = new ArrayList<>();
    }
    
    public static TransactionStorage getInstance() {
        if (instance == null) {
            instance = new TransactionStorage();
        }
        return instance;
    }
    
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
