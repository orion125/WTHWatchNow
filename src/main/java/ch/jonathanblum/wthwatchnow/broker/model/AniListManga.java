/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import static ch.jonathanblum.wthwatchnow.broker.model.SearchResult.LOGGER;
import ch.jonathanblum.wthwatchnow.manager.model.Manga;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class AniListManga extends SearchResult implements MangaConvertible {

    protected String title_romaji;
    protected String type;
    protected String start_date;
    protected String end_date;
    protected String title_japanese;
    protected String title_english;
    protected ArrayList<String> synonims;
    protected String description;
    protected ArrayList<String> genres;
    protected String image_url_lge;
    protected String image_url_med;
    protected String image_url_banner;
    protected String publishing_status;
    protected float average_score;
    protected int total_chapters;
    protected int total_volumes;
    protected boolean adult;
    protected int popularity;
    protected String relation_type;
    protected Object role;
    protected HashMap<String, Integer> list_stats;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_romaji() {
        return title_romaji;
    }

    public void setTitle_romaji(String title_romaji) {
        this.title_romaji = title_romaji;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTitle_japanese() {
        return title_japanese;
    }

    public void setTitle_japanese(String title_japanese) {
        this.title_japanese = title_japanese;
    }

    public String getTitle_english() {
        return title_english;
    }

    public void setTitle_english(String title_english) {
        this.title_english = title_english;
    }

    public ArrayList<String> getSynonims() {
        return synonims;
    }

    public void setSynonims(ArrayList<String> synonims) {
        this.synonims = synonims;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getImage_url_lge() {
        return image_url_lge;
    }

    public void setImage_url_lge(String image_url_lge) {
        this.image_url_lge = image_url_lge;
    }

    public String getImage_url_med() {
        return image_url_med;
    }

    public void setImage_url_med(String image_url_med) {
        this.image_url_med = image_url_med;
    }

    public String getImage_url_banner() {
        return image_url_banner;
    }

    public void setImage_url_banner(String image_url_banner) {
        this.image_url_banner = image_url_banner;
    }

    public String getPublishing_status() {
        return publishing_status;
    }

    public void setPublishing_status(String publishing_status) {
        this.publishing_status = publishing_status;
    }

    public float getAverage_score() {
        return average_score;
    }

    public void setAverage_score(float average_score) {
        this.average_score = average_score;
    }

    public int getTotal_chapters() {
        return total_chapters;
    }

    public void setTotal_chapters(int total_chapters) {
        this.total_chapters = total_chapters;
    }

    public int getTotal_volumes() {
        return total_volumes;
    }

    public void setTotal_volumes(int total_volumes) {
        this.total_volumes = total_volumes;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public HashMap<String, Integer> getList_stats() {
        return list_stats;
    }

    public void setList_stats(HashMap<String, Integer> list_stats) {
        this.list_stats = list_stats;
    }
    
    
    
    public static String[] getTableModelColumns() {
       return new String[]{"ID", "Titre", "type"};
    }
    
    public String[] getTableModelData() {
        return null;
    }
    
    @Override
    public DefaultTableModel getTableModel() {
        columns = new String[]{"ID", "Titre"};
        return super.getTableModel();
    }

    @Override
    public Object[] toTableModelRow() {
        return new Object[]{this.id, this.title_english};
    }
    
    @Override
    public Manga toManga() {
        LOGGER.log(Level.FINER, "Convert {0} to Manga", this.id);
        Manga m = new Manga(this.getTitle_english());
        m.setPics(this.getImage_url_med());
        m.setMaxChap(this.getTotal_chapters());
        m.setType(this.getType());
        m.setSynopsis(this.getDescription());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
                
        try {
            m.setAiringDate(format.parse(this.getStart_date()));
            m.setEndDate(format.parse(this.getEnd_date()));
        } catch (Exception ex) {
             LOGGER.log(Level.SEVERE, "Error parsing dates for Manga: {0}", this.getId());
        }
        m.setGenres(this.getGenres());
 
        return m;
    }
}
