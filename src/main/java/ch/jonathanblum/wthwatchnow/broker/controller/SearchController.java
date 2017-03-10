/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.controller;

import ch.jonathanblum.wthwatchnow.broker.model.AniListAnime;
import ch.jonathanblum.wthwatchnow.broker.model.AniListManga;
import ch.jonathanblum.wthwatchnow.broker.model.AnilistBroker;
import ch.jonathanblum.wthwatchnow.broker.model.AnimeConvertible;
import ch.jonathanblum.wthwatchnow.broker.model.Broker;
import ch.jonathanblum.wthwatchnow.broker.model.BrokerUnreachableException;
import ch.jonathanblum.wthwatchnow.broker.model.MangaConvertible;
import ch.jonathanblum.wthwatchnow.broker.model.SearchResult;
import ch.jonathanblum.wthwatchnow.broker.view.SearchView;
import ch.jonathanblum.wthwatchnow.core.controller.AbstractCoreController;
import ch.jonathanblum.wthwatchnow.manager.controller.ManagerController;
import ch.jonathanblum.wthwatchnow.manager.model.Anime;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionDAO;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import ch.jonathanblum.wthwatchnow.manager.model.Manga;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class SearchController extends AbstractCoreController {
    protected static final String APP_USER_AGENT = "WTHWatchNow v0.0.1";
    protected static final String APP_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String TYPE_ANIME = "anime";
    public static final String TYPE_MANGA = "manga";
    
    protected SearchView view;
    protected HashMap<String, ArrayList<RegisteredBroker>> brokers= new HashMap<>();
    protected Broker currentBroker = null;
    protected AbstractCoreController caller;
    
    @Override
    public void Do() {

    }
    
    public void Do(AbstractCoreController caller, String type) {
        LOGGER.log(Level.FINER, "Do() Search Controller");
        if(caller == null) {
            LOGGER.log(Level.SEVERE, "No Controller was given, can't load brokers.");
        }
        this.caller = caller;
        if(type == null) {
            LOGGER.log(Level.SEVERE, "No Collection Type was given, can't load brokers.");
            return;
        }
         
        registerBrokers();           
        
        if(!getCM().getConf().isNoGUI()) {
            view = new SearchView(this, type);
            Display();
        }       
    }

    @Override
    protected void Display() {
        if(!getCM().getConf().isNoGUI() || view != null) {
           LOGGER.log(Level.FINE, "Display() Search GUI");
           view.pack();
           view.setLocationRelativeTo(null);
           view.setVisible(true);        
        }  
    }
    
    
    static String requestHTTP(String request_url, String method) {
        
        URL url;
        HttpURLConnection con;
        BufferedReader rr;
        String line;
        String output = "";
        
        LOGGER.log(Level.FINER, "Request URL: {0}; Method: {1}", new Object[]{request_url, method});
        try {
            url = new URL(request_url);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", APP_CONTENT_TYPE);
            con.setRequestProperty("User-Agent", APP_USER_AGENT);

            rr = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while((line = rr.readLine()) != null) {
                output += line;
            }
            rr.close();
            
        } catch (MalformedURLException ex) {
            LOGGER.log(Level.SEVERE, "Broker''s URL malformed. {0}", ex.getMessage());
        } catch (ProtocolException ex) {
            LOGGER.log(Level.SEVERE, "Protocol Exception : {0}", ex.getMessage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Cannot reach {0}", ex.getMessage());
        }
        return output;
    }
    
    public static String getHTTP (String request_url) {
        return requestHTTP(request_url, "GET");        
    }
    
    public static String postHTTP(String request_url) {
        return requestHTTP(request_url, "POST");
    }
    
    protected void registerBrokers() {
        LOGGER.log(Level.FINER, "Register Brokers");
        brokers.put(TYPE_ANIME, new ArrayList<RegisteredBroker>() {
            {
                add(new RegisteredBroker("AniList", AnilistBroker.class));
                
            }
        });
        brokers.put(TYPE_MANGA, new ArrayList<RegisteredBroker>(){
            {
                add(new RegisteredBroker("AniList", AnilistBroker.class));
            }
        });
        
    }

    public ArrayList<RegisteredBroker> getBrokersForType(String type) {
        LOGGER.log(Level.FINER, "Get RegisteredBrokers for {0}", type);
        return brokers.get(type);
    }
    public Broker getCurrentBroker() {
        return currentBroker;
    }
    
    public void selectCurrentBroker(RegisteredBroker broker) {
        try {
            LOGGER.log(Level.INFO, "Broker {0} selected", broker.getName());
            currentBroker = (Broker) broker.getClassref().newInstance();
           
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.log(Level.WARNING, "Trying to select inexistant broker");
            LOGGER.log(Level.SEVERE, ex.getMessage());
            
        }           
    }   
    
    public ArrayList search(String query, String type) throws BrokerUnreachableException {
        return currentBroker.search(query.trim(), type);
    }
    
    public void AddToCurrentCollection(SearchResult element, String type) throws BrokerUnreachableException {
        if(caller != null && !(caller instanceof ManagerController)) {
            LOGGER.log(Level.SEVERE, "Cannot get reference to Manager, aborting operation");
            return;
        }
        try {
            LOGGER.log(Level.FINE, "Selected element id : {0}", element.getId());
            LOGGER.log(Level.FINEST, "Selected element type : {0}", type);
            
            SearchResult fullresult = currentBroker.get(element.getId(), type);

            CollectionManager manager = ((ManagerController) caller).getCollectionManager();
            CollectionItem item = null;
            switch(type.toLowerCase()) {
                case TYPE_ANIME:
                    item = ((AniListAnime) fullresult).toAnime();
                    if(item != null) {
                        CollectionDAO.addAnimeToCollection((Anime) item, manager.getCurrentCollection());
                        manager.getCurrentCollection().addItem(item, "");
                    }
                    break;
                case TYPE_MANGA:
                    item = ((AniListManga) fullresult).toManga();
                    if(item != null) {
                        CollectionDAO.addMangaToCollection((Manga) item, manager.getCurrentCollection());
                        manager.getCurrentCollection().addItem(item, "");
                    }
                    break;
            }

        } catch (BrokerUnreachableException ex) {
            LOGGER.log(Level.SEVERE, "Fetching remote informations for {0} failed. Adding aborted", element.getId());
            throw ex;
        }
    }
    
    public class RegisteredBroker {
        private final String name;
        private final Class classref;

        public RegisteredBroker(String name, Class classref) {
            this.name = name;
            this.classref = classref;
        }
        public String getName() { return name; }
        public Class getClassref() { return classref; }
        @Override
        public String toString() { return name; }
    }
}
