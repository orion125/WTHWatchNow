/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.model;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class ConfigurationManager {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private static class SingletonHolder
    {
        private final static ConfigurationManager INSTANCE = new ConfigurationManager();
    } 
    
    public static ConfigurationManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public Configuration getConf() {
        return Configuration.getInstance();
    }

    private ConfigurationManager() {

    }
    
    public Boolean checkConfig() {

        String path = Configuration.getInstance().getConfPath();
        if(path == null) {
            path = System.getProperty("user.home") +
                          System.getProperty("file.separator") +
                          ".wthwatchnow" +
                          System.getProperty("file.separator");
            Configuration.getInstance().setConfPath(path);
        }
                             
        File f = new File(path + "config.properties");
        
        boolean isconfigfound = false;
        if(f.exists())  {
            LOGGER.log(Level.INFO, "Configuration file found at {0}", path + Configuration.FILENAME);
            isconfigfound = true;
            loadConfigFile();
            
        } else {
            LOGGER.log(Level.WARNING, "Configuration file not found in {0}", path);
            isconfigfound = false;
            //writeConfigFile();
        }
        
        return isconfigfound;
    }
    
    public void loadConfigFile () {
        Configuration.getInstance().load(Configuration.getInstance().getConfPath());
    }
    
    public void writeConfigFile() {
        LOGGER.log(Level.INFO,"writing configuration file.");
        String dirpath = Configuration.getInstance().getConfPath();
        File dir = new File(dirpath);
        if(!dir.exists()) {
            try {
            dir.mkdir();
            } catch(SecurityException se) {
                LOGGER.log(Level.SEVERE,"Cannot create conf dir.");
            }
        }
        Configuration.getInstance().write(Configuration.getInstance().getConfPath());
    }
}
