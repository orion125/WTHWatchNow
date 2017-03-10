/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.controller;

import ch.jonathanblum.wthwatchnow.manager.controller.ManagerController;
import ch.jonathanblum.wthwatchnow.setup.controller.SetupController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class Dispatcher extends AbstractCoreController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static final String ROUTE_DEFAULT = "default";
    public static final String ROUTE_SETUP = "setup";
    public static final String ROUTE_MANAGER = "manager";
    public static final String ROUTE_SERVER = "server";
    
    public Dispatcher() {
        
    }
    
    public void dispatch(String request) {
        switch(request) {
            case ROUTE_SETUP: 
                LOGGER.log(Level.INFO, "Loading Setup");
                new SetupController().Do();
                break;
            case ROUTE_SERVER:
                LOGGER.log(Level.INFO, "Loading Server");
                break;
            case ROUTE_MANAGER:
                LOGGER.log(Level.INFO, "Loading Manager");
                new ManagerController().Do();
                break;
            default:
                if(getCM().getConf().isServerMode()) {
                    dispatch(ROUTE_SERVER);
                }
                if(!getCM().getConf().isNoGUI()) {
                    dispatch(ROUTE_MANAGER);
                }
        }
    }

    @Override
    public void Do() {
        LOGGER.warning("Trying to execute Dispatcher's Do, who doesn't exist");        
    }

    @Override
    protected void Display() {
        LOGGER.warning("Trying to display Dispatcher's view, who doesn't exist");        
    }
    
}
