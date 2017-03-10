/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.controller;

import ch.jonathanblum.wthwatchnow.core.model.Configuration;
import ch.jonathanblum.wthwatchnow.core.model.ConfigurationManager;
import java.util.logging.Logger;

/**
 * Base Controller
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public abstract class AbstractCoreController {
    protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    
    protected ConfigurationManager getCM() {
        return ConfigurationManager.getInstance();
    }
    
    protected Configuration getConf() {
        return getCM().getConf();
    }
    
    /**
     * Execute Controller's actions
     */
    abstract public void Do();
    
    /**
     * Open User Interface
     */
    abstract protected void Display();
}
