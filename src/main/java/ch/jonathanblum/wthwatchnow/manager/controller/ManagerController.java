/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.controller;

import ch.jonathanblum.wthwatchnow.broker.controller.SearchController;
import ch.jonathanblum.wthwatchnow.core.controller.AbstractCoreController;
import ch.jonathanblum.wthwatchnow.manager.model.Collection;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionControllerFactory;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManagerChangedListener;
import ch.jonathanblum.wthwatchnow.manager.view.ManagerView;
import ch.jonathanblum.wthwatchnow.users.controller.UsersController;
import ch.jonathanblum.wthwatchnow.users.model.UserAuthenticatedListener;
import ch.jonathanblum.wthwatchnow.users.model.UserManager;
import java.util.logging.Level;
import javax.swing.event.ChangeEvent;


/**
 * Entry point for the management of user's collections
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class ManagerController extends AbstractCoreController implements CollectionManagerChangedListener, UserAuthenticatedListener {

    protected CollectionManager collectionmanager = new CollectionManager();
    protected AbstractCollectionController collectioncontroller = null;
    
    @Override
    public void Do() {
        LOGGER.log(Level.FINER, "Do()");
        
        // First : User authentication
        UsersController.getInstance().addOnAuthenticateListener(this);
        UsersController.getInstance().authenticate();
        
    }
    
    @Override
    public void userAuthenticatedEvent(ChangeEvent evt) {
        // Second : Manager.
        collectionmanager.addOnChangedListener(this);        
        collectionmanager.fetchCollections(UserManager.getInstance().getCurrentUser());
        
        // Display the Interface   
        if(!getConf().isNoGUI())
            Display();    }

    public CollectionManager getCollectionManager() {
        return collectionmanager;
    }    
    
    @Override
    public void updateCollectionListEvent(ChangeEvent evt) {
 
    }

    @Override
    public void changeCurrentCollectionEvent(ChangeEvent evt) {
        LOGGER.log(Level.FINER,"changeCurrentCollection received");
        setCollectionController();
    }
    
    public void setCollectionController() {
        if(getCollectionManager().getCurrentCollection() == null)
            return;
        
        Collection current = getCollectionManager().getCurrentCollection();
        LOGGER.log(Level.FINE, "Set CollectionControler for {0}", current);
        
        
        collectioncontroller = CollectionControllerFactory.getCollectionController(collectionmanager);
    }

    public AbstractCollectionController getCollectionController() {
        return collectioncontroller;
    }
    
    public void search() {
        new SearchController().Do(this, getCollectionManager().getCurrentCollection().getType());
    }
    
    @Override
    protected void Display() {       
        // Instantiate the View
        if(!getConf().isNoGUI()) {
            ManagerView view = new ManagerView(this);
            collectionmanager.addOnChangedListener(view);
            view.pack();
            view.setLocationRelativeTo(null);
            view.setVisible(true);              
        } 
    }

}
