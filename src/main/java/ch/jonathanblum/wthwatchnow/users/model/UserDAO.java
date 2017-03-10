/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.users.model;

import ch.jonathanblum.wthwatchnow.core.db.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for users
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class UserDAO {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        
        try {
            
            Connection con = DBConnection.get();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, user_name, user_password, user_access "
                                            + "FROM wwn_user");
            
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"), 
                        rs.getString("user_password"),
                        rs.getString("user_access")
                ));
            }
            stmt.close();

        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Cannot fetch users from database: {0}", ex.getMessage());
            return null;
        }
        return users;
    }
}
