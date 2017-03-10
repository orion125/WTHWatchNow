/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.controller;

import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import ch.jonathanblum.wthwatchnow.manager.model.Serie;
import ch.jonathanblum.wthwatchnow.manager.view.SerieView;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class SerieController extends AbstractCollectionController {

    public SerieController(CollectionManager cm) {
        super(cm);
    }

      
    @Override
    public JInternalFrame getView(CollectionItem item) {
        if(item == null)
            return null;
        view  = (JInternalFrame) new SerieView((Serie) item);
        return view;   
    }

    @Override
    public void changeItem(CollectionItem item) {
        changeItem((Serie) item);
    }

    public void changeItem(Serie serie) {
       /* if(getCollection().contains(serie))
            ((SerieView) view).changeSerie(serie);*/
    }
}
