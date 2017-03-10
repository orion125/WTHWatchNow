/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.controller;

import ch.jonathanblum.wthwatchnow.manager.model.Collection;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class FilmController extends AbstractCollectionController {

    public FilmController(CollectionManager cm) {
        super(cm);
    }


    @Override
    public JInternalFrame getView(CollectionItem item) {
        return null;
    }

    @Override
    public void changeItem(CollectionItem item) {
    }
    
}
