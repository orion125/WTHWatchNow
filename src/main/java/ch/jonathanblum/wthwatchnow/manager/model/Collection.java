/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 * @param <T> A CollectionItem
 */
public class Collection<T extends CollectionItem> extends AbstractTableModel {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private final Class<T> clazz;
    
    private final CopyOnWriteArrayList<CollectionUpdateListener> onchangelistener;
    protected int id;    
    protected String name;
    protected LinkedHashMap<T, String> items = new LinkedHashMap<>();
    protected ArrayList<String> lists = new ArrayList<>();
    protected int current = -1;
    
    public Collection(int id, String Name, Class<T> clazz) {
        this.id = id;
        this.name = Name;
        this.onchangelistener = new CopyOnWriteArrayList<>();
        this.clazz = clazz;
    }  
    
    public void addCollectionChangedListener(CollectionUpdateListener l) {
        this.onchangelistener.add(l);
    }
    public void removeCollectionChangedListener(CollectionUpdateListener l) {
        this.onchangelistener.remove(l);
    }
    
    protected void fireUpdateEvent() {
        LOGGER.log(Level.FINER, "Trigger Collection Update Event");
        ChangeEvent evt = new ChangeEvent(this);
        onchangelistener.stream().forEach((l) -> {
            l.updateCollectionEvent(evt);
        });
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
        fireUpdateEvent();
    }
    
    public Class<T> getClazz() {
        return clazz;
    }
    
    public String getType() {
        return clazz.getSimpleName();
    }
    

    public LinkedHashMap<T, String> getItems() {
        return items;
    }
     
    public void addItem(T item, String list) {
        LOGGER.log(Level.INFO, "Adding {0} to {1}", new String[]{item.getTitle(), this.toString()});
        String tagsStr;
        if(!lists.contains(list))
            list = "";
        items.put(item, list);
        fireUpdateEvent();
    }
    
    void removeItem(T item) {
        LOGGER.log(Level.INFO, "Removing {0} from {1}", new String[]{item.getTitle(), this.getName()});
        items.remove(item);
        fireUpdateEvent();
    }
    
    void clearItems() {
        items.clear();
    }

    public ArrayList<String> getLists() {
        return lists;
    }
    
    Collection addList(String list) {
        lists.add(list);
        return this;
    }

    void setLists(ArrayList<String> lists) {
        this.lists = lists;
    }
    
    void clearLists() {
        lists.clear();
    }
    
    public T getItem(int i) {
        if (0 <= i && i < items.size())
            return (T) items.keySet().toArray()[i];
        else
            return null;
    }
    
    public CollectionItem getCurrent() {
        return getItem(current);
    }
    void setCurrent(int i) {
        if(0 <= i && i < items.size())
            current = i;
        else
            current = -1;
    }
    
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Collection other = (Collection) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.clazz.getSimpleName());
    }

    @Override
    public int getRowCount() {
        
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int i) {
        switch(i) {
            case 0: return this.name;
            case 1: return "Liste";
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int i) {
         switch(i) {
            case 0: return getClass();
            case 1: return String.class;
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        switch(i1) {
            case 0: return false;
            case 1: return true;
            default: return false;
        }
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch(i1) {
            case 0: return getItem(i);
            case 1: return items.values().toArray()[i];
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        switch(i1) {
            case 0: break;
            case 1: 
                T item = (T) getValueAt(i, 0);
                System.out.println("Item Selectionné : " + item);
                items.put(item, o.toString());
                break;
            default: break;
        }
    }
}
