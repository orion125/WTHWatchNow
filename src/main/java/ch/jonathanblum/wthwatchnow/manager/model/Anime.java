/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jonathan Blum
 */
public class Anime extends CollectionItem {
    
    protected String synopsis;
    protected ArrayList<String> producers = new ArrayList<>();
    protected ArrayList<String> genres = new ArrayList<>();
    protected String pics;
    protected String type;
    protected String status = "Inconnu";
    protected int maxEp = 0;
    protected int airedEp = 0;
    protected int dlEp = 0;
    protected int viewedEp = 0;
    protected Date airingDate;
    protected Date endDate;

    public Anime(String title) {
        super(title);
    }
    
    public String getSynopsis() {
        return synopsis;
    }

    public String getPics() {
        return pics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }
    
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public ArrayList<String> getProducers() {
        return producers;
    }
    
    public void addProducer(String p) {
        producers.add(p);
    }

    public void setProducers(ArrayList<String> producers) {
        this.producers = producers;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    
    public void addGenre(String g) {
        genres.add(g);
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxEp() {
        return maxEp;
    }

    public void setMaxEp(int maxEp) {
        this.maxEp = maxEp;
    }

    public int getDlEp() {
        return dlEp;
    }

    public void setDlEp(int dlEp) {
        this.dlEp = dlEp;
    }

    public int getViewedEp() {
        return viewedEp;
    }

    public void setViewedEp(int viewedEp) {
        this.viewedEp = viewedEp;
    }

    public int getAiredEp() {
        return airedEp;
    }

    public void setAiredEp(int airedEp) {
        this.airedEp = airedEp;
    }

    public Date getAiringDate() {
        return airingDate;
    }

    public void setAiringDate(Date airingDate) {
        this.airingDate = airingDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
    
}
