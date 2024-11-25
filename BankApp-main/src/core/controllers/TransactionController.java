/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.Transaction;
import core.models.TransactionType;
import core.models.storage.AccountStorage;
import core.models.storage.TransactionStorage;

/**
 *
 * @author stairs
 */

public class TransactionController {
    
    public static Response executeTransaction(TransactionType ttype, String srcaccID, String destaccID, double amount) {
        
        try {
            
            AccountStorage astorage = AccountStorage.getInstance();
            TransactionStorage tstorage = TransactionStorage.getInstance();
            
            try {
                if (amount< 0) {
                    return new Response("Amount must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }
            
            switch (ttype) {
                case DEPOSIT: {
                    
                    if (destaccID.equals("")) {
                        return new Response("Destination account must be not empty", Status.BAD_REQUEST);
                    }
                    
                    Account destacc = null;
                    for (Account account : astorage.getAccounts()) {
                        if (account.getId().equals(destaccID)) {
                            destacc = account;
                        }
                    }
                    
                    if (destacc != null) {
                        destacc.deposit(amount);
                        tstorage.addTransaction(new Transaction(TransactionType.DEPOSIT, null, destacc, amount));
                        return new Response ("Deposit executed successfully", Status.CREATED);
                    }else{
                        return new Response ("Destination account does not exist", Status.NOT_FOUND);
                    }
                }
                case WITHDRAW: {
                    
                    if (srcaccID.equals("")) {
                        return new Response("Source account must be not empty", Status.BAD_REQUEST);
                    }
                    
                    Account srcacc = null;
                    for (Account account : astorage.getAccounts()) {
                        if (account.getId().equals(srcaccID)) {
                            srcacc = account;
                        }
                    }
                    
                    if (srcacc != null) {
                        if(!srcacc.withdraw(amount)){
                            return new Response("Source account does not have the funds to complete this transaction", Status.BAD_REQUEST);
                        }else{
                            tstorage.addTransaction(new Transaction(TransactionType.WITHDRAW, srcacc, null, amount));
                            return new Response ("Withdraw executed successfully", Status.CREATED);
                        }
                    }else{
                        return new Response ("Source account does not exist", Status.NOT_FOUND);
                    }
                }
                case TRANSFER: {
                    
                    if (srcaccID.equals("")) {
                        return new Response("Source account must be not empty", Status.BAD_REQUEST);
                    }
                    
                    if (destaccID.equals("")) {
                        return new Response("Destination account must be not empty", Status.BAD_REQUEST);
                    }
                    
                    Account srcacc = null;
                    for (Account account : astorage.getAccounts()) {
                        if (account.getId().equals(srcaccID)) {
                            srcacc = account;
                        }
                    }
                    
                    Account destacc = null;
                    for (Account account : astorage.getAccounts()) {
                        if (account.getId().equals(destaccID)) {
                            destacc = account;
                        }
                    }
                    
                    if (srcacc != null) {
                        if (destacc != null){
                            if (srcacc.withdraw(amount)){
                                destacc.deposit(amount);
                                tstorage.addTransaction(new Transaction(TransactionType.TRANSFER, srcacc, destacc, amount));
                                return new Response ("Transfer executed succesfully", Status.CREATED);
                            }else{
                                return new Response ("Source account does not have the funds to complete this transaction",Status.BAD_REQUEST);
                            }
                            
                        }else{
                            return new Response ("Destination account does not exist", Status.NOT_FOUND);
                        }
                    }else{
                        return new Response ("Source account does not exist", Status.NOT_FOUND);
                    }
                }
                default: {
                    return new Response ("Invalid transaction type",Status.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
           return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}