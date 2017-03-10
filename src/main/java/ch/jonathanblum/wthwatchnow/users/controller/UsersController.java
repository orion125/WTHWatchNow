/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.users.controller;

import ch.jonathanblum.wthwatchnow.core.controller.AbstractCoreController;
import ch.jonathanblum.wthwatchnow.users.model.User;
import ch.jonathanblum.wthwatchnow.users.model.UserManager;
import ch.jonathanblum.wthwatchnow.users.model.UserAuthenticatedListener;
import ch.jonathanblum.wthwatchnow.users.view.LoginView;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import javax.swing.event.ChangeEvent;

/**
 * Controller for managing Authentication
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public final class UsersController extends AbstractCoreController {
    private final UserManager users = UserManager.getInstance();
    private final CopyOnWriteArrayList<UserAuthenticatedListener> onauthenticatelisteners;  
    
    private static class SingletonHolder {
        private final static UsersController instance = new UsersController();
    }
    public static UsersController getInstance() {
        return SingletonHolder.instance;
    }

    private UsersController() {
        this.onauthenticatelisteners = new CopyOnWriteArrayList<>();
    }
    
    public void addOnAuthenticateListener(UserAuthenticatedListener l) {
        onauthenticatelisteners.add(l);
    }
    public void  removeOnAuthenticateListener(UserAuthenticatedListener l) {
        onauthenticatelisteners.remove(l);
    }
    
    private void fireUserAuthenticatedEvent() {
        LOGGER.log(Level.FINER, "Fire UserAuthenticated Event");
        ChangeEvent evt = new ChangeEvent(this);
        onauthenticatelisteners.stream().forEach((l) -> {
            l.userAuthenticatedEvent(evt);
        });
    }

    @Override
    public void Do() {
        LOGGER.log(Level.FINER, "Do()");

    }

    public ArrayList<User> getUsers() {
        return users.getUsers();
    }
    public User getCurrentUser() {
        return users.getCurrentUser();
    }
    
    public boolean authenticate(User user, String password) {
        boolean isauth = users.authenticate(user, password);
        if(isauth) fireUserAuthenticatedEvent();
        return isauth;
    }
    
    public boolean authenticate() {
        if(!users.isAuthenticated())
            displayLogin();
        return users.isAuthenticated();
    }

    private boolean displayLogin() {
        LoginView loginview = new LoginView(this);
        
       if(!getCM().getConf().isNoGUI()) {
           LOGGER.log(Level.FINE, "Display() Login GUI");
           loginview.pack();
           loginview.setLocationRelativeTo(null);
           loginview.setVisible(true);        
       }   
       return (loginview.getReturnStatus() == LoginView.RET_OK);
    }

    @Override
    protected void Display() {
 
    }  
}
