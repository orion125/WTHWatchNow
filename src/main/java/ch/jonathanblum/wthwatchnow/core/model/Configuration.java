/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class Configuration {
    public static final String FILENAME = "config.properties";

    
    private Configuration() { }
    
    private static class SingletonHolder
    {
        private final static Configuration instance = new Configuration();
    } 
    
    public static Configuration getInstance() {
        return SingletonHolder.instance;
    }
    
    private final String shortname = "WTHWatchNow";
    private final String fullname = "What The Hell Should I Watch Now ?";
    private final String version = "0.0.1";
    private String confdirpath = null;
    
    // Writed Options
    private static final String OPT_SERVER = "server";
    private Boolean servermode = false;
    private static final String OPT_NOGUI = "nogui";
    private Boolean nogui = false;
    private static final String OPT_DEBUG = "debug";
    private Boolean debugmode = false;
    
    private static final String OPT_DBTYPE = "dbtype";
    private String dbtype = "";
    private static final String OPT_DBHOST = "dbhost";
    private String dbhost = "localhost";
    private static final String OPT_DBPORT = "dbport";
    private String dbport = "";
    private static final String OPT_DBUSER = "dbuser";
    private String dbuser = "";
    private static final String OPT_DBPWD = "dbpassword";
    private String dbpassword = "";
    private static final String OPT_DBNAME = "dbname";
    private String dbname = "";

    public String getVersion() {
        return version;
    }

    public String getShortname() {
        return shortname;
    }

    public String getFullname() {
        return fullname;
    }
    
    public Boolean isServerMode() {
        return servermode;
    }

    public void setServerMode(Boolean servermode) {
        this.servermode = servermode;
    }

    public Boolean isDebugMode() {
        return debugmode;
    }

    public void setDebugMode(Boolean VerboseMode) {
        this.debugmode = VerboseMode;
    }

    public Boolean isNoGUI() {
        return nogui;
    }

    public void setNoGUI(Boolean nogui) {
        this.nogui = nogui;
    }
    
    public String getConfPath() {
        return confdirpath;
    }

    public void setConfPath(String confdirpath) {
        this.confdirpath = confdirpath;
    }    

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }
    
    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }
    
    
   
    public Properties getProperties() {
        Properties prop = new Properties();
        prop.setProperty(OPT_SERVER, String.valueOf(isServerMode()));
        prop.setProperty(OPT_DEBUG, String.valueOf(isDebugMode()));
        prop.setProperty(OPT_NOGUI, String.valueOf(isNoGUI()));
        prop.setProperty(OPT_DBTYPE, getDbtype());
        prop.setProperty(OPT_DBHOST, getDbhost());
        prop.setProperty(OPT_DBPORT, getDbport());
        prop.setProperty(OPT_DBUSER, getDbuser());
        prop.setProperty(OPT_DBPWD, getDbpassword());
        prop.setProperty(OPT_DBNAME, getDbname());
        return prop;
    }
    public void setProperties(Properties prop) {
        setServerMode(isServerMode() || Boolean.valueOf(prop.getProperty(OPT_SERVER)));
        setDebugMode(isDebugMode() || Boolean.valueOf(prop.getProperty(OPT_DEBUG)));
        setNoGUI(isNoGUI() || Boolean.valueOf(prop.getProperty(OPT_NOGUI)));
        setDbtype(prop.getProperty(OPT_DBTYPE));
        setDbhost(prop.getProperty(OPT_DBHOST));
        setDbport(prop.getProperty(OPT_DBPORT));
        setDbuser(prop.getProperty(OPT_DBUSER));
        setDbpassword(prop.getProperty(OPT_DBPWD));     
        setDbname(prop.getProperty(OPT_DBNAME));
    }
    
    public void write(String path) {
        Properties prop = getProperties();
        OutputStream output = null;
        
        try {
            output = new FileOutputStream(path + FILENAME);
           
            prop.store(output, "WTHWatchNow Config File");
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if(output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void load(String path) {
        Properties prop = new Properties();
        InputStream input = null;
        
        try {
            input = new FileInputStream(path + FILENAME);
            prop.load(input);
            
            setProperties(prop);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
