/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.AccountStorage;
import core.models.storage.UserStorage;
import core.models.Account;
import java.util.Random;
/**
 *
 * @author stairs
 */
public class AccountController {
    
    public static Response createAccount(String userId, double initBal){
        try {
            int idInt;
            double balDouble;
            String accountId;
            
            try {
                idInt = Integer.parseInt(userId);
                if (idInt < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            
            try {
                balDouble = initBal;
                if (balDouble < 0) {
                    return new Response("Starting balance must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Starting balance must be numeric", Status.BAD_REQUEST);
            }
            
            AccountStorage astorage = AccountStorage.getInstance();  
            UserStorage ustorage = UserStorage.getInstance();
            
            User owner = null;
            for (User user : ustorage.getUsers()){
                if(user.getId() == idInt && owner == null){
                    owner = user;
                }
            }
            
            if (owner != null) {
                Random random = new Random();
                int first = random.nextInt(1000);
                int second = random.nextInt(1000000);
                int third = random.nextInt(100);
                
                accountId = String.format("%03d", first) + "-" + String.format("%06d", second) + "-" + String.format("%02d", third);
                
                if (!astorage.addAccount(new Account(accountId, owner, balDouble))) {
                    return new Response("The id in question is already occupied", Status.BAD_REQUEST);
                }
            }
            

            return new Response("Person created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
