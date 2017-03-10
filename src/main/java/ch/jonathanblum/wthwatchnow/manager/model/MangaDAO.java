/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import ch.jonathanblum.wthwatchnow.core.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class MangaDAO {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static Collection<Manga> fetchMangasByCollection(Collection col) {

        try {
            col.clearItems();
            Connection con = DBConnection.get();
            
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT coi_title, "
                        + "coi_picture, "
                        + "man_type, "
                        + "man_chap_max, "
                        + "man_chap_aired, "
                        + "man_chap_dl, "
                        + "man_chap_read, "
                        + "man_synopsis, "
                        + "man_genres, "
                        + "man_status, "
                        + "man_authors, "
                        + "man_editors,"
                        + "listed_col_id "
                    + "FROM wwn_ci_listed "
                    + "LEFT JOIN wwn_collectionitem "
                        + "ON wwn_collectionitem.coi_id = wwn_ci_listed.listed_coi_id "
                    + "LEFT JOIN wwn_manga "
                        + "ON wwn_manga.man_coi_id = wwn_collectionitem.coi_id "
                    + "WHERE wwn_ci_listed.listed_col_id LIKE ? "
                    + "ORDER BY coi_title");
            stmt.setInt(1, col.getId());
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Manga m = new Manga(rs.getString("coi_title"));
                m.setStatus(rs.getString("man_status"));
                m.setSynopsis(rs.getString("man_synopsis"));
                m.addProducer(rs.getString("man_editors"));
                m.addGenre(rs.getString("man_genres"));
                m.setMaxChap(rs.getInt("man_chap_max"));
                m.setAiredChap(rs.getInt("man_chap_aired"));
                m.setDlChap(rs.getInt("man_chap_dl"));
                m.setReadChap(rs.getInt("man_chap_read"));              
                
                col.addItem(m, col.getLists().get(rs.getInt("listed_col_id")).toString());
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to fetch Manga list for collection {0}", col.getName());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }        
        
        return col;
    }
    
    public static int insertManga(Manga m, int id) {
        int id_anime = -1;
        try {
            Connection con = DBConnection.get();

            PreparedStatement stmt = con.prepareStatement(
              "INSERT INTO wwn_manga ("
                      + "man_coi_id, "
                      + "man_type, "
                      + "man_chap_max, "
                      + "man_chap_aired, "
                      + "man_chap_dl, "
                      + "man_chap_read, "
                      + "man_synopsis, "
                      + "man_genres, "
                      + "man_status, "
                      + "man_authors, "
                      + "man_editors)"
                      + "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            
            stmt.setInt(1, id);
            stmt.setString(2, m.getType());
            stmt.setInt(3, m.getMaxChap());
            stmt.setInt(4, m.getAiredChap());
            stmt.setInt(5, m.getDlChap());
            stmt.setInt(6, m.getReadChap());
            stmt.setString(7, m.getSynopsis());
            stmt.setString(8, m.getGenres().toString());
            stmt.setString(9, m.getStatus());
            stmt.setString(10, ""); // TODO Add getAuthors
            stmt.setString(11, m.getProducers().toString());
            stmt.executeUpdate();
            /*
            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()) {
                id_anime = key.getInt(1);
            } */
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to insert Manga to database");
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());        
        }
        
        return id_anime;
    }
    
}
