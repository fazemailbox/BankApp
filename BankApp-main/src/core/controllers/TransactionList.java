/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Transaction;
import core.models.storage.TransactionStorage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author stairs
 */
public class TransactionList {
    public static Response listTransactions(){
        try{
            TransactionStorage tstorage = TransactionStorage.getInstance();
            
            if (tstorage.getTransactions().isEmpty()){
                return new Response("No transactions available", Status.NOT_FOUND);
            }else{
                ArrayList<Transaction> tclone = (ArrayList<Transaction>) tstorage.getTransactions().clone();
                Collections.reverse(tclone);
                return new Response("Showing transactions", Status.OK, tstorage.getTransactions());
            }
        }catch(Exception ex){
            return new Response("Error", Status.INTERNAL_SERVER_ERROR);
        } 
    }
}
