/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import javax.swing.event.ChangeEvent;

/**
 *
 * @author Jonathan Blum
 */
public interface CollectionUpdateListener {
    public void updateCollectionEvent(ChangeEvent evt);
}
