package ch.jonathanblum.wthwatchnow.manager.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;

/**
 *  Manage Collections Content
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public abstract class AbstractCollectionController {
    protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected CollectionManager cm;

    public AbstractCollectionController(CollectionManager cm) {
        this.cm = cm;
    }

    protected CollectionManager getCm() {
        return cm;
    }    

    protected JInternalFrame view;
  
    public abstract JInternalFrame getView(CollectionItem item);
    
    public abstract void changeItem(CollectionItem item);
    
}
