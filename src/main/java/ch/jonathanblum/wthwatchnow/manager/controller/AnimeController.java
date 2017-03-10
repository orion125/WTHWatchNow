/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.controller;

import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.Anime;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import ch.jonathanblum.wthwatchnow.manager.view.AnimeView;
import java.util.logging.Level;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class AnimeController extends AbstractCollectionController {

    public AnimeController(CollectionManager cm) {
        super(cm);
    }

    @Override
    public JInternalFrame getView(CollectionItem item) {
        if(item == null)
            return null;
        view = (JInternalFrame) new AnimeView((Anime) item);
        return view;
    }    

    @Override
    public void changeItem(CollectionItem item) {
            changeItem((Anime) item);
    }
    
    public void changeItem(Anime anime) {
        if(getCm().getCurrentCollection().contains(anime)) {
            LOGGER.log(Level.INFO, "Anime selected {0}", anime);
            ((AnimeView) view).changeAnime(anime);
            
        }
    }
}
