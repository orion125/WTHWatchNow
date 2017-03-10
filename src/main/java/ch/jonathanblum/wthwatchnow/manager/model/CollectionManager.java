/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import ch.jonathanblum.wthwatchnow.users.controller.UsersController;
import ch.jonathanblum.wthwatchnow.users.model.User;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;


/**
 * Know all Collections and manage Collection's related operations
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class CollectionManager {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final CopyOnWriteArrayList<CollectionManagerChangedListener> onchangelisteners;
    
    protected final static int COL_NOPOS = -1;
    protected ArrayList<Collection> collections = new ArrayList<>();
    protected int currentCollectionPos = COL_NOPOS;

    public CollectionManager() {
        this.onchangelisteners = new CopyOnWriteArrayList<>();
    }
    
    public void addOnChangedListener(CollectionManagerChangedListener l) {
        this.onchangelisteners.add(l);
    }
    public void removeOnChangedListener(CollectionManagerChangedListener l) {
        this.onchangelisteners.add(l);
    }
    
    protected void fireChangeEvent() {
        LOGGER.log(Level.FINER, "Fire ChangeCollectionList Event");
       fireChangeCollectionListEvent();
       fireChangeCurrentCollectionEvent();
    }
    
    protected void fireChangeCollectionListEvent() {
        LOGGER.log(Level.FINER, "Fire ChangeCollectionList Event");
        ChangeEvent evt = new ChangeEvent(this);
        onchangelisteners.stream().forEach((l) -> {
            l.updateCollectionListEvent(evt);
        });
    }
    protected void fireChangeCurrentCollectionEvent() {
        LOGGER.log(Level.FINER, "Fire ChangeCurrentCollection Event");
        ChangeEvent evt = new ChangeEvent(this);
        onchangelisteners.stream().forEach((l) -> {
            l.changeCurrentCollectionEvent(evt);
        });       
    }

    public void fetchCollections(User u) {
        LOGGER.log(Level.FINE, "Fetching Collection List for user {0}", u.getUsername());
        collections.clear();
        
        setCollections(CollectionDAO.fetchUserCollections(u));
        
        fireChangeCollectionListEvent();
        if(collections.size() > 0) {
            setCurrentCollection(0);
        }
    }
    
    public ArrayList<Collection> getCollections() {
        return collections;
    }

    protected void setCollections(ArrayList<Collection> collections) {
        this.collections = collections; 
    }

    public Collection getCurrentCollection() {
        if(COL_NOPOS < currentCollectionPos && currentCollectionPos < collections.size())
            return collections.get(currentCollectionPos);
        else
            return null;
    }

    public void setCurrentCollection(int index) {
        if(COL_NOPOS < index && index < collections.size()) {
            this.currentCollectionPos = index;
            LOGGER.log(Level.INFO, "Switch current collection to {0}", getCurrentCollection().toString());
            fetchCurrentCollection();
        } else {
            this.currentCollectionPos = COL_NOPOS;
        }
        fireChangeCurrentCollectionEvent();
    }
    
    public void setCurrentCollection(Collection collection) {
        if(collection == null) {
            LOGGER.log(Level.WARNING, "Set current collection to null");
            setCurrentCollection(COL_NOPOS);
        } else if(!getCollections().contains(collection)) {
            LOGGER.log(Level.WARNING, "Switch current collection to inexistant collection");
            setCurrentCollection(COL_NOPOS);
        } else {
            setCurrentCollection(getCollections().indexOf(collection));         
        }
    }
    
    public void fetchCurrentCollection() {
        LOGGER.log(Level.FINE, "Fetching current collection ");
        
        switch(getCurrentCollection().getId()) {
            case 1:
                //collections.set(currentCollectionPos,CollectionFixture.getAnimes());
                collections.set(currentCollectionPos, CollectionDAO.fetchAnimesByCollection(getCurrentCollection()));
                break;
            case 2:
                //collections.set(currentCollectionPos,CollectionFixture.getMangas());
                collections.set(currentCollectionPos, CollectionDAO.fetchMangasByCollection(getCurrentCollection()));
                break;/*
            case 3:
                collections.set(currentCollectionPos,CollectionFixture.getSeries());
                break;
            case 4:
                collections.set(currentCollectionPos,CollectionFixture.getFilms());
                break;
            case 5:
                collections.set(currentCollectionPos,CollectionFixture.getLivres());
                break;*/
            default:
                LOGGER.log(Level.WARNING, "No Data for Current Collection");
        }
    }
    
    public void deleteCurrentCollection() {
        LOGGER.log(Level.INFO, "Delete collection {0}", getCurrentCollection().toString());
        
        CollectionDAO.deleteCollection(getCurrentCollection());
        getCollections().remove(getCurrentCollection());
        fireChangeCollectionListEvent();
        setCurrentCollection(null);
    }
    
    public void editCurrentCollection(String name) {
        LOGGER.log(Level.INFO, "Update current collection name to {0}", name);

        getCurrentCollection().setName(name);
        CollectionDAO.renameCollection(getCurrentCollection(), name);
        fireChangeCurrentCollectionEvent();
    }
    
    public <T extends CollectionItem> void addCollection(String name, String type) {
        User u = UsersController.getInstance().getCurrentUser();
        switch(type) {
            case "Anime":
                CollectionDAO.insertCollection(name, "anime", u);
                addCollection(new Collection<Anime>(collections.size()+1, name, Anime.class), u);
                
                break;
            case "Manga":
                CollectionDAO.insertCollection(name, "nanga", u);
                addCollection(new Collection<Manga>(collections.size()+1, name, Manga.class), u);
                break;
            default:
                 LOGGER.log(Level.SEVERE, "Attempt to create collection of unsupported type");
        }
        //addCollection(new Collection<Manga>(collections.size()+1, name));
        setCurrentCollection(collections.size()-1);
    }
    
    public <T extends CollectionItem> void addCollection(Collection<T> collection, User u) {
        LOGGER.log(Level.INFO, "Adding Collection {0} to collection list", collection.toString());
        
        collections.add(collection);
        fireChangeCollectionListEvent();
    }
}
