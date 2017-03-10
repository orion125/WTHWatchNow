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
public class AnimeDAO {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    /* // NIY
    public static ArrayList<Collection> fetchAllAnimes() {
        
        
        ArrayList<Collection> list = new ArrayList<>();
        
        try {
            
            Connection con = DBConnection.get();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("");
            
            while (rs.next()) {
                int id = rs.getInt("col_id");
                String name = rs.getString("col_name");
                
                        
                switch(rs.getString("col_type")) {
                    case "anime":
                        list.add(new Collection<Anime>(id, name, Anime.class));
                        break;
                    case "manga":
                        list.add(new Collection<Manga>(id, name, Manga.class));
                        break;
                    default:
                        LOGGER.log(Level.WARNING, "Wrong collection type for collection \"{0}\"", name);
                        
                }      
            }
        } catch (SQLException ex) {
            //LOGGER.log(Level.SEVERE, "Impossible to fetch collection for user {0}", u.getUsername());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }
        
        return list;
    }
    */
    
    public static Collection<Anime> fetchAnimesByCollection(Collection col) {

        try {
            col.clearItems();
            Connection con = DBConnection.get();
            
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT coi_title, "
                        + "coi_picture, "
                        + "ani_type, "
                        + "ani_eps_max, "
                        + "ani_eps_aired, "
                        + "ani_eps_dl, "
                        + "ani_eps_viewed, "
                        + "ani_synopsis, "
                        + "ani_genres, "
                        + "ani_status, "
                        + "ani_authors, "
                        + "ani_producers,"
                        + "listed_col_id "
                    + "FROM wwn_ci_listed "
                    + "LEFT JOIN wwn_collectionitem "
                        + "ON wwn_collectionitem.coi_id = wwn_ci_listed.listed_coi_id "
                    + "LEFT JOIN wwn_anime "
                        + "ON wwn_anime.ani_coi_id = wwn_collectionitem.coi_id "
                    + "WHERE wwn_ci_listed.listed_col_id LIKE ? "
                    + "ORDER BY coi_title");
            stmt.setInt(1, col.getId());
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Anime a = new Anime(rs.getString("coi_title"));
                a.setStatus(rs.getString("ani_status"));
                a.setSynopsis(rs.getString("ani_synopsis"));
                a.addProducer(rs.getString("ani_producers"));
                a.addGenre(rs.getString("ani_genres"));
                a.setMaxEp(rs.getInt("ani_eps_max"));
                a.setAiredEp(rs.getInt("ani_eps_aired"));
                a.setDlEp(rs.getInt("ani_eps_dl"));
                a.setViewedEp(rs.getInt("ani_eps_viewed"));               
                
                col.addItem(a, col.getLists().get(rs.getInt("listed_col_id")).toString());
            }
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to fetch Anime list for collection {0}", col.getName());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }        
        
        return col;
    }
    
    public static int insertAnime(Anime a, int id) {
        int id_anime = -1;
        try {
            Connection con = DBConnection.get();

            PreparedStatement stmt = con.prepareStatement(
              "INSERT INTO wwn_anime ("
                      + "ani_coi_id, "
                      + "ani_type, "
                      + "ani_eps_max, "
                      + "ani_eps_aired, "
                      + "ani_eps_dl, "
                      + "ani_eps_viewed, "
                      + "ani_synopsis, "
                      + "ani_genres, "
                      + "ani_status, "
                      + "ani_authors, "
                      + "ani_producers)"
                      + "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            
            stmt.setInt(1, id);
            stmt.setString(2, a.getType());
            stmt.setInt(3, a.getMaxEp());
            stmt.setInt(4, a.getAiredEp());
            stmt.setInt(5, a.getDlEp());
            stmt.setInt(6, a.getViewedEp());
            stmt.setString(7, a.getSynopsis());
            stmt.setString(8, a.getGenres().toString());
            stmt.setString(9, a.getStatus());
            stmt.setString(10, ""); // TODO Add getAuthors
            stmt.setString(11, a.getProducers().toString());
            stmt.executeUpdate();
            /*
            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()) {
                id_anime = key.getInt(1);
            } */
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to insert Anime to database");
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());        
        }
        
        return id_anime;
    }
    
    
    
}
