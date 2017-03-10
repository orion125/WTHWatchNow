package ch.jonathanblum.wthwatchnow.manager.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Abstract class for Collection's items
 * @author Jonathan Blum
 */
public abstract class CollectionItem implements Comparable<CollectionItem>{
    protected int id;
    protected String title;
    protected String picture;

    public CollectionItem(String title) {
        this(-1, title);
    }

    public CollectionItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    } 

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int compareTo(CollectionItem t) {
        return title.compareTo(t.getTitle());
    }
    
}