/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.AccountStorage;

/**
 *
 * @author stairs
 */
public class AccountList {
    public static Response listAccounts(){
        try{
            AccountStorage astorage = AccountStorage.getInstance();
            
            if (astorage.getAccounts().isEmpty()){
                return new Response("No accounts available", Status.NOT_FOUND);
            }else{
                astorage.getAccounts().sort((obj1, obj2) -> (obj1.getId()).compareTo(obj2.getId()));
                return new Response("Showing accounts", Status.OK, astorage.getAccounts());
            }
        }catch(Exception ex){
            return new Response("Error", Status.INTERNAL_SERVER_ERROR);
        } 
    }
}
