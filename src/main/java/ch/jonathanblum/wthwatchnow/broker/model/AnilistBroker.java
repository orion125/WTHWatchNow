/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import ch.jonathanblum.wthwatchnow.broker.controller.SearchController;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class AnilistBroker extends Broker {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    protected final static String API_URL = "https://anilist.co/api/";
    protected final static String API_ID = "noirvent-ojck1";
    protected final static String API_SECRET = "zWweZivdGCho6kyk4tjbopCM9p";
    protected static AniListAccessToken access_token;

    public AnilistBroker() {
        
    }
    
   
    @Override
    public ArrayList<SearchResult> search(String query, String type) throws BrokerUnreachableException {
        try {
            if(query.isEmpty() || type == null) {
                LOGGER.log(Level.WARNING, "Search query is empty");
                return null;
            }
            switch(type) {
                case "anime": return (ArrayList<SearchResult>) ((Object)searchAnime(query));
                case "manga": return (ArrayList<SearchResult>) ((Object)searchManga(query));
                default:return null;
            } 
        } catch (BrokerUnreachableException ex) {
            LOGGER.log(Level.SEVERE, "Cannot search, Anilist unreacheable");
            throw ex;
        }
    }
    
    @Override
    public SearchResult get(int id, String type) throws BrokerUnreachableException {
        try {
            switch(type.toLowerCase()) {
                case "anime": return getAnime(id);
                case "manga": return getManga(id);
                default: return null;
            }
        } catch (BrokerUnreachableException ex) {
            LOGGER.log(Level.SEVERE, "Cannot search, Anilist unreacheable");
            throw ex;
        }
    }
    
    public ArrayList<AniListAnime> searchAnime(String query) throws BrokerUnreachableException {
        String url = "anime/search/";
        query = query.trim();
        LOGGER.log(Level.INFO, "Search Anilist for Anime : {0}", query);
        String response = getHTTP(url + query);
        
        ArrayList<AniListAnime> animes = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        if(!response.isEmpty()) {
            JsonArray array = parser.parse(response).getAsJsonArray();
            for (JsonElement element : array) {
                animes.add(gson.fromJson(element, AniListAnime.class));
            }
        } else { 
            LOGGER.log(Level.INFO, "No results for {0}", query);
        }
        return animes;
    }
    
    public AniListAnime getAnime(int ID) throws BrokerUnreachableException {
        String url = "anime/";
        LOGGER.log(Level.INFO, "Get Anime ID {0} on Anilist", ID);
        try {
            String response = getHTTP(url + ID);
            AniListAnime a = null;
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            if(response != null && !response.isEmpty()) {
                a = gson.fromJson(parser.parse(response).toString(), AniListAnime.class);
            }

            return a;
        } catch (BrokerUnreachableException ex) {
            throw ex;
        }
       

    }
    
    public ArrayList<AniListManga> searchManga(String query) throws BrokerUnreachableException {
        String url = "manga/search/";
        query = query.trim();
        LOGGER.log(Level.INFO, "Search Anilist for Manga : {0}", query); 
        String response = getHTTP(url + query);
        
        ArrayList<AniListManga> mangas = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        if(!response.isEmpty()) {
            JsonArray array = parser.parse(response).getAsJsonArray();
            for (JsonElement element : array) {
                mangas.add(gson.fromJson(element, AniListManga.class));
            }
        } else { 
            LOGGER.log(Level.INFO, "No results for {0}", query);
        }
        return mangas;
    }
    
    public AniListManga getManga(int ID) throws BrokerUnreachableException {
        String url = "manga/";
        LOGGER.log(Level.INFO, "Get Manga ID {0} on Anilist", ID);
        String response = getHTTP(url + ID);
        AniListManga m = null;
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        if(response != null && !response.isEmpty()) {
            m = gson.fromJson(parser.parse(response).toString(), AniListManga.class);
        }
        return m;
    }    
        
    private void refreshAccessToken() {
        if(access_token == null || access_token.isExpired())
            getAccessToken();
    }
    private void getAccessToken() {
        LOGGER.log(Level.FINE,"Getting Anilist Acces Token");
     
        
        String url = "auth/access_token";
        String grant_type = "client_credentials";
        String request = String.format("?grant_type=%s&client_id=%s&client_secret=%s", grant_type ,API_ID, API_SECRET);
        
        
        try {
            String response = postHTTP(url + request);
            JsonObject json = new JsonParser().parse(response).getAsJsonObject();
            access_token = new AniListAccessToken(
                    json.get("access_token").getAsString(),
                    json.get("token_type").getAsString(),
                    json.get("expires").getAsLong(),
                    json.get("expires_in").getAsInt()
            );
            LOGGER.log(Level.INFO, "Anilist Access Token Granted");         
            LOGGER.log(Level.FINE, "Token : {0}", access_token);
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, "Cannot get Access Token for Anilist. Aborting operation.");
        }
    }
    
    
    protected String getHTTP (String request_url) throws BrokerUnreachableException {
        refreshAccessToken();
        if(access_token != null) {
            String request = String.format("%s%s?%s", API_URL, request_url, access_token.getAccess_tokenParam());
            return SearchController.getHTTP(request);        
        } else { 
            LOGGER.log(Level.SEVERE, "No access token, aborting HTTP request");
            throw new BrokerUnreachableException("No Access Token");
        }         
    }
    
    protected String postHTTP(String request_url) {
        String request = String.format("%s%s", API_URL, request_url);
        return SearchController.postHTTP(request);
    }    

    @Override
    public String toString() {
        return "AniList";
    }  
}