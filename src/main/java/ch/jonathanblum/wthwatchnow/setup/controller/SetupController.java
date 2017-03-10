/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.setup.controller;

import ch.jonathanblum.wthwatchnow.core.controller.AbstractCoreController;
import ch.jonathanblum.wthwatchnow.core.db.DBConnection;
import ch.jonathanblum.wthwatchnow.setup.view.SetupView;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class SetupController extends AbstractCoreController {

    @Override
    public void Do() {
        if(!getConf().isNoGUI())
            Display();
    }

    @Override
    protected void Display() {
        // Instantiate the View
        if(!getConf().isNoGUI()) {
            SetupView view = new SetupView(this);
            view.pack();
            view.setLocationRelativeTo(null);
            view.setVisible(true);              
        }        
    }
    
    public ArrayList<String> testConnection(String driver, String host, int port, String username, String password) throws SQLException {
        Connection con = null;
        switch(driver) {
            case "oracle":
                con = DBConnection.get(DBConnection.DRIVER_ORACLE, host, port, username, password, "");
                break;
            case "mysql":
                con = DBConnection.get(DBConnection.DRIVER_MYSQL, host, port, username, password, "");
                break;
        }        
        
        ArrayList<String> catalogs =  new ArrayList<>();
        if(con != null) {
            DatabaseMetaData dbMeta = con.getMetaData();
            ResultSet rs = dbMeta.getCatalogs();
            while (rs.next()) {catalogs.add(rs.getString("TABLE_CAT")); }

            LOGGER.log(Level.INFO, "Connection test successfull");
            LOGGER.log(Level.FINEST, "Catalogs found: {0}", catalogs);
            return catalogs;
        } else
            return null;
    }
    
    
    public void initializeDatabase(String driver, String host, int port, String username, String password, String database) {
        getConf().setDbtype(driver);
        getConf().setDbhost(host);
        getConf().setDbport(Integer.toString(port));
        getConf().setDbuser(username);
        getConf().setDbpassword(password);
        getConf().setDbname(database);
        
        getCM().writeConfigFile();
        generateDatabase();
    }
    
    private void generateDatabase() {
        switch(getConf().getDbtype()) {
            case "oracle":
                new OracleSetupController().Do();
                break;
            default:
                LOGGER.log(Level.SEVERE, "Cannot generate database: driver type unsupported");
        }
    }
}
