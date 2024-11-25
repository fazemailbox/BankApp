/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.UserStorage;

/**
 *
 * @author stairs
 */
public class UserController {
     public static Response registerUser(int id, String firstname, String lastname, int age) {
        try {
            
            try {
                if (id < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }
            
            if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }
            
            if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }
            
            try {
                if (age < 0) {
                    return new Response("Age must be positive", Status.BAD_REQUEST);
                }
                else if (age < 18) {
                    return new Response("User must be older than 18 to register", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Age must be numeric", Status.BAD_REQUEST);
            }
            
            UserStorage ustorage = UserStorage.getInstance();            
            if (!ustorage.addUser(new User(id, firstname, lastname, age))) {
                return new Response("A person with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Person created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
     }
}
