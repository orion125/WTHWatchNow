/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.db;

import ch.jonathanblum.wthwatchnow.core.model.Configuration;
import ch.jonathanblum.wthwatchnow.core.model.ConfigurationManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum
 */
public class DBConnection {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public final static String DRIVER_MYSQL = "mysql";
    public final static String DRIVER_ORACLE = "oracle";
    public final static String DRIVER_PGSQL = "pgsql";
    
    private static Connection con = null;
    
    public static void connect(String driver, String host, int port, String username, String password, String database) {
        try {
            switch(driver) {
                case DRIVER_MYSQL:
                    con = ch.jonathanblum.wthwatchnow.core.db.mysql.Tools.connect(host, port, username, password, database);
                    break;
                case DRIVER_PGSQL:
                    con = ch.jonathanblum.wthwatchnow.core.db.pgsql.Tools.connect(host, port, username, password, database);
                    break;
                case DRIVER_ORACLE:
                    con = ch.jonathanblum.wthwatchnow.core.db.oracle.Tools.connect(host, port, username, password, database);
                    break;
                default:
                    LOGGER.log(Level.SEVERE, "Attempt to connect to a non supported DBMS");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "SQL Exception: {0}", ex.getMessage());
        } catch(ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Driver {0} not found:" + ex.getMessage(), driver);
        }
    }
    
    public static Connection get(String driver, String host, int port, String username, String password, String database) {
        if(con == null) {connect(driver, host, port, username, password, database); }
        return con;
    }
    
    public static Connection get() {
        Configuration conf = ConfigurationManager.getInstance().getConf();
        return DBConnection.get(conf.getDbtype(), conf.getDbhost(), Integer.parseInt(conf.getDbport()), conf.getDbuser(), conf.getDbpassword(), conf.getDbname());           
    }
        
    public static void close() {
        try {con.close(); con = null; }
        catch(SQLException ex) { LOGGER.log(Level.SEVERE, "Impossible to close database connection"); }
    }
}
