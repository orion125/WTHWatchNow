/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.db.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Jonathan Blum
 */
public class Tools {

    public static Connection connect(String host, int port, String username, String password, String database) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        Properties props = new Properties();
        props.put("user", username);
        props.put("password", password);
        //props.put("charset", "UTF-8");
        
        return DriverManager.getConnection(String.format("jdbc:oracle:thin:@%s:%d:%s",
                                                         host,
                                                         port,
                                                         database
                                                        ), props);
    }
    
}
