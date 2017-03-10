/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import ch.jonathanblum.wthwatchnow.manager.controller.AbstractCollectionController;
import ch.jonathanblum.wthwatchnow.manager.controller.*;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class CollectionControllerFactory {
    
    public static AbstractCollectionController getCollectionController(CollectionManager cm) {
        switch(cm.getCurrentCollection().getType()) {
            case "Anime":
                return getAnimeController(cm);
            case "Manga":
                return getMangaController(cm);

            default:
                return null;    
        }
    }
    
    public static AnimeController getAnimeController(CollectionManager cm) {
        return new AnimeController(cm);
    }
    
    public static MangaController getMangaController(CollectionManager cm) {
        return new MangaController(cm);
    }
   
}
