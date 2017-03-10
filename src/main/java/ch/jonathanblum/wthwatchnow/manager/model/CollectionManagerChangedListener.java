/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import javax.swing.event.ChangeEvent;

/**
 * Event for change in CollectionManager's collection list
 * @author Jonathan Blum
 */
public interface CollectionManagerChangedListener {
    public void updateCollectionListEvent(ChangeEvent evt);
    public void changeCurrentCollectionEvent(ChangeEvent evt);
}
