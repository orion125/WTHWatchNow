/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import ch.jonathanblum.wthwatchnow.core.db.DBConnection;
import ch.jonathanblum.wthwatchnow.core.model.ConfigurationManager;
import ch.jonathanblum.wthwatchnow.users.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class CollectionDAO {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static ArrayList<Collection> fetchUserCollections(User u) {
        ArrayList<Collection> list = new ArrayList<>();
        
        // TODO  Fetch from Database 
        try {
            
            Connection con = DBConnection.get();
            PreparedStatement stmt = con.prepareStatement("SELECT col_id, col_name, col_type, col_public "
                    + "FROM wwn_collection "
                    + "WHERE col_user_id LIKE ?");
            stmt.setInt(1, u.getId());
            ResultSet rs = stmt.executeQuery();
            
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
            LOGGER.log(Level.SEVERE, "Impossible to fetch collection for user {0}", u.getUsername());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }
        
        return list;
    }

    /*
    public static Collection getCollection(int id) {
        Collection c = new Collection(0, "blank", null);
        
        return c;
    }*/
    
    public static Collection<Anime> fetchAnimesByCollection(Collection col) {
        fetchCollectionLists(col);        
        return AnimeDAO.fetchAnimesByCollection(col);
    }
    
    public static Collection<Manga> fetchMangasByCollection(Collection col) {
        fetchCollectionLists(col);
        return MangaDAO.fetchMangasByCollection(col);
    }
    
    public static void fetchCollectionLists(Collection col) {
        
        try {
            col.clearLists();
            Connection con = DBConnection.get();
            
            PreparedStatement stmt = con.prepareStatement("SELECT list_id, list_libelle "
                    + "FROM wwn_list "
                    + "WHERE list_col_id LIKE ?");
            stmt.setInt(1, col.getId());
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                col.addList(rs.getString("list_libelle"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Impossible to fetch list for collection {0}", col.getName());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }
    }
    
    public static void addAnimeToCollection(Anime a, Collection c) {
        // 1. Check if anime already in db
        // TODO
        // 2. If not, add Anime to db
        int id_coi = insertCollectionItem(a);
        AnimeDAO.insertAnime(a, id_coi);
        // 3. Link to Collection
        insertItemToCollectionLink(id_coi, c.getId(), 1); // TODO : FIX LIST ID
        
    }
    
    public static void addMangaToCollection(Manga m, Collection c) {
        // 1. Check if manga already in db
        // TODO
        // 2. If not, add Manga to db
        int id_coi = insertCollectionItem(m);
        MangaDAO.insertManga(m, id_coi);
        // 3. Link to Collection
        insertItemToCollectionLink(id_coi, c.getId(), 1);
        
    }
    
    private static void insertItemToCollectionLink(int item_id, int col_id, int list_id) {
        try {
            Connection con = DBConnection.get();
            
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO wwn_ci_listed (listed_col_id, listed_coi_id, listed_list_id)"
                  + "VALUES (?, ?, ?)");
            stmt.setInt(1, col_id);
            stmt.setInt(2, item_id);
            stmt.setInt(3, list_id);
            
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cannot insert CollectionItem to Collection association into database");
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
       
        }
    }
    
    private static int insertCollectionItem(CollectionItem coi) {
        int id_coi = -1;
        try {
            Connection con = DBConnection.get();
            
            //You can either use the prepareStatement method taking an additional int parameter
            //
            //PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            //For some JDBC drivers (for example, Oracle) you have to explicitly list the column names or indices of the generated keys:
            //
            //PreparedStatement ps = con.prepareStatement(sql, new String[]{"USER_ID"})   
            String sql = "";
            PreparedStatement stmt = null;
            switch(ConfigurationManager.getInstance().getConf().getDbtype()) {
                case "mysql":
                     stmt = con.prepareStatement(
                        "INSERT INTO wwn_collectionitem (coi_title, coi_picture)"
                        + "VALUES (?, ?)", 
                        PreparedStatement.RETURN_GENERATED_KEYS
                     );
                    break;
                case "oracle":
                    stmt = con.prepareStatement(
                        "INSERT INTO wwn_collectionitem (coi_id, coi_title, coi_picture)"
                        + "VALUES (seq_coi_id.NEXTVAL, ?, ?)", 
                        new String[]{"coi_id"} // GENERATED KEY
                    );
                    break;
                default:
                    LOGGER.log(Level.SEVERE, "Cannot determine driver for insertion");
                    return -1; //TODO : Throw exception        
            }
   
            stmt.setString(1, coi.getTitle());
            stmt.setString(2, coi.getPicture());
            stmt.executeUpdate();
            ResultSet key = stmt.getGeneratedKeys();
            if(key.next()) {
                id_coi = key.getInt(1);
            }
            LOGGER.log(Level.FINEST, "CollectionItem ID : {0}", id_coi);
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cannot insert CollectionItem into database");
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }
        
        return id_coi;
    }
    
    public static void insertCollection(String name, String type, User u) {
        try {
            Connection con = DBConnection.get();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO `wwn_collection` (`col_id`, `col_name`, `col_type`, `col_public`, `col_user_id`)"
                  + " VALUES (NULL, ?, ?, ?, ?);"
            );
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setInt(3, 0);
            stmt.setInt(4, u.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cannot insert Collection into database");
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
        }
    }
    
    public static void renameCollection(Collection c, String name) {
        try {
            Connection con = DBConnection.get();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE `wwn_collection` "
                    + "SET `col_name` = ? "
                    + "WHERE `wwn_collection`.`col_id` = ?");
            stmt.setString(1, name);
            stmt.setInt(2, c.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cannot update Collection {0] into database", c.getId());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());   
        }
        
    }
    
    public static void deleteCollection(Collection c) {
        try {
            Connection con = DBConnection.get();
            PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM `wwn_collection` "
                  + "WHERE `wwn_collection`.`col_id` = ?");
            stmt.setInt(1, c.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Cannot delete Collection {0} into database", c.getId());
            LOGGER.log(Level.FINE, "{0}", ex.getMessage());
       }
    }
    
}
