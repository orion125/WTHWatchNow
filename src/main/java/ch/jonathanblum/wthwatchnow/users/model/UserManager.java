/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.users.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Manage all users-related operations
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public final class UserManager {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    protected ArrayList<User> users = new ArrayList<>();
    protected User currentUser;
    
    private static class SingletonHolder
    {
        private final static UserManager instance = new UserManager();
    } 
    
    public static UserManager getInstance() {
        return SingletonHolder.instance;
    }

    private UserManager() {
        fetchUsers();
    }
    
    public final void fetchUsers() {       
        UserDAO.getUsers().stream().forEach((user) -> {
            addUser(user);
        });
        
        LOGGER.log(Level.FINE, "Database Access Granted");
        
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }

    protected void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    protected void addUser(User u) {
        LOGGER.log(Level.INFO, "Add user {0}", u);
        users.add(u);
    }
    
    protected void removeUser(User u) {
        users.remove(u);
    }
    
    protected boolean contains(User u) {
        return users.contains(u);
    }
    
    public boolean authenticate(User u, String p) {
        // TODO Better Password Management
        if(users.contains(u) &&  u.password.equals(p)) {
            LOGGER.log(Level.INFO, "User {0} authenticated", u);
            currentUser = u;
            return true;
        } else {
            LOGGER.log(Level.INFO, "Incorrect password for {0}", u);
            currentUser = null;
            return false;
        }
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    public boolean isAuthenticated() {
        return (currentUser != null);
    }
        
}
