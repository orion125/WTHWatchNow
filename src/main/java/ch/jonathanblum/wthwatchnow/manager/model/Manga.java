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
public class Manga extends CollectionItem {
    
    protected String synopsis;
    protected ArrayList<String> producers = new ArrayList<>();
    protected ArrayList<String> genres = new ArrayList<>();
    protected String pics;
    protected String type;
    protected String status = "Inconnu";
    protected int maxChap = 0;
    protected int airedChap = 0;
    protected int dlChap = 0;
    protected int readChap = 0;
    protected Date airingDate;
    protected Date endDate;
    
    public Manga(String title) {
        super(title);
    }    

    public String getSynopsis() {
        return synopsis;
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

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxChap() {
        return maxChap;
    }

    public void setMaxChap(int maxChap) {
        this.maxChap = maxChap;
    }

    public int getAiredChap() {
        return airedChap;
    }

    public void setAiredChap(int airedChap) {
        this.airedChap = airedChap;
    }

    public int getDlChap() {
        return dlChap;
    }

    public void setDlChap(int dlChap) {
        this.dlChap = dlChap;
    }

    public int getReadChap() {
        return readChap;
    }

    public void setReadChap(int readChap) {
        this.readChap = readChap;
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
