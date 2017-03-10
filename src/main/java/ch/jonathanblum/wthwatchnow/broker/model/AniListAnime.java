/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import ch.jonathanblum.wthwatchnow.manager.model.Anime;
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
public class AniListAnime extends SearchResult implements AnimeConvertible {
 
    protected String title_romaji;
    protected String type;
    protected String image_url_med;
    protected String image_url_sml;
    protected String start_date;
    protected String end_date;
    protected String title_japanese;
    protected String title_english;
    protected String classification;
    protected String hashtag;
    protected String source;
    protected ArrayList<String> synonyms;
    protected String description;
    protected ArrayList<String> genres;    
    protected String image_url_lge;
    protected String image_url_banner;
    protected int duration;   
    protected String airing_status;    
    protected float average_score;
    protected int total_episodes;
    protected String youtube_id;
    protected boolean adult;
    protected int popularity;
    protected Object relation_type;
    protected Object role;
    protected HashMap<String, Integer> list_stats;
    protected Airing airing;

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

    public String getImage_url_med() {
        return image_url_med;
    }

    public void setImage_url_med(String image_url_med) {
        this.image_url_med = image_url_med;
    }

    public String getImage_url_sml() {
        return image_url_sml;
    }

    public void setImage_url_sml(String image_url_sml) {
        this.image_url_sml = image_url_sml;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
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

    public String getImage_url_banner() {
        return image_url_banner;
    }

    public void setImage_url_banner(String image_url_banner) {
        this.image_url_banner = image_url_banner;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAiring_status() {
        return airing_status;
    }

    public void setAiring_status(String airing_status) {
        this.airing_status = airing_status;
    }

    public float getAverage_score() {
        return average_score;
    }

    public void setAverage_score(float average_score) {
        this.average_score = average_score;
    }

    public int getTotal_episodes() {
        return total_episodes;
    }

    public void setTotal_episodes(int total_episodes) {
        this.total_episodes = total_episodes;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
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

    public Object getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(Object relation_type) {
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

    public Airing getAiring() {
        return airing;
    }

    public void setAiring(Airing airing) {
        this.airing = airing;
    }


    @Override
    public String toString() {
        return "AniListAnime{" + "id=" + id + ", title_english=" + title_english + '}';
    }
    
    @Override
    public DefaultTableModel getTableModel() {
        columns = new String[]{"ID", "Titre", "type"};
        return super.getTableModel();
    }
    
    @Override
    public Object[] toTableModelRow() {
        return new Object[] {this.id, this.title_english, this.type};
    }
    
    @Override
    public Anime toAnime() {
        LOGGER.log(Level.FINER, "Convert {0} to Anime", this.id);
        Anime a = new Anime(this.getTitle_english());
        a.setPics(this.getImage_url_med());
        a.setMaxEp(this.getTotal_episodes());
        a.setType(this.getType());
        a.setSynopsis(this.getDescription());/*
        switch(this.getAiring_status()) {
            case "finished airing":
                a.setStatus(Anime.STATUS_FINISHED);
            default:
                a.setStatus(Anime.STATUS_TBA);
        }*/
        a.setStatus(this.getAiring_status());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
                
        try {
            a.setAiringDate(format.parse(this.getStart_date()));
            a.setEndDate(format.parse(this.getEnd_date()));
        } catch (Exception ex) {
             LOGGER.log(Level.SEVERE, "Error parsing dates for Anime: {0}", this.getId());
        }
        a.setGenres(this.getGenres());
        
        return a;
    }
   
    public class Airing {
        private String time;
        private long countdown;
        private int next_episode;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public long getCountdown() {
            return countdown;
        }

        public void setCountdown(long countdown) {
            this.countdown = countdown;
        }

        public int getNext_episode() {
            return next_episode;
        }

        public void setNext_episode(int next_episode) {
            this.next_episode = next_episode;
        }
    }
}
