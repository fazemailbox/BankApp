/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.UserStorage;

/**
 *
 * @author stairs
 */
public class UserList {
    public static Response listUsers(){
        try{
            UserStorage ustorage = UserStorage.getInstance(); 
            
            if(ustorage.getUsers().isEmpty()){
                return new Response ("No users available",Status.NOT_FOUND);
            }else{
                //Se tiene que verificar si hay solo un usuario o más?
                ustorage.getUsers().sort((obj1, obj2) -> (obj1.getId() - obj2.getId()));
                
                return new Response("Showing users",Status.OK, ustorage.getUsers());
                
            }
        }catch (Exception ex) {
            return new Response("Error", Status.INTERNAL_SERVER_ERROR);
      }
    }

}
