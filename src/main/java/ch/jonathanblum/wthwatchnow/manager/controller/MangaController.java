/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.controller;

import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManager;
import ch.jonathanblum.wthwatchnow.manager.model.Manga;
import ch.jonathanblum.wthwatchnow.manager.view.MangaView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class MangaController extends AbstractCollectionController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MangaController(CollectionManager cm) {
        super(cm);
    }

    @Override
    public JInternalFrame getView(CollectionItem item) {
        if(item == null)
            return null;
        view  = (JInternalFrame) new MangaView((Manga) item);
        return view;
    }

    @Override
    public void changeItem(CollectionItem item) {
        changeItem((Manga) item);
    }
    
    public void changeItem(Manga manga) {
        if(getCm().getCurrentCollection().contains(manga)) {
            LOGGER.log(Level.INFO, "Manga selected {0}", manga);
            ((MangaView) view).changeManga(manga);
        }
    }
}
