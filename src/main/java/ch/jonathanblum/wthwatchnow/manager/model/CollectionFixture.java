/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.model;

import ch.jonathanblum.wthwatchnow.broker.model.AnilistBroker;
import ch.jonathanblum.wthwatchnow.broker.model.BrokerUnreachableException;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Injection of dummy data for testing purpose
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 * @param <T> A CollectionItem Type
 */
public class CollectionFixture<T extends CollectionItem> {/*
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Collection getAnimes() {
        Collection<Anime> col = new Collection<>(1, "Animes", Anime.class);
        col.addList("En cours").addList("Prévu").addList("Complété");
        
        AnilistBroker broker = new AnilistBroker();
              
        Anime a = new Anime("Arslan Senki");
        a.setStatus(Anime.STATUS_FINISHED);
        a.setSynopsis("An 317 du calendrier Parse. Le royaume occidental de Lusitania, monothéiste, envahit le royaume de Maryam dans sa guerre sainte pour imposer la foi en Yaldobaoth, son dieu unique, aux royaumes orientaux. Andragoras III, roi de Parse, mène son armée et écrase la force d'invasion des Lusitaniens.\n" +
            "\n" +
            "Le roi Andragoras III est un roi guerrier, un féroce combattant qui est considéré comme invaincu à la tête de son armée, connue pour l'efficacité de sa cavalerie lourde reconnue comme la meilleure du monde. Il est marié à la reine Tahaminé, considérée comme la plus belle femme du royaume, et a eu d'elle un fils, le prince Arslân (du mot turque \"Aslan\", qui veut dire \"lion\") qui a 11 ans. Ce jeune prince, sensible et doté d'une carrure fragile et d'un physique peu viril, n'est pas apprécié aussi bien par son père que par sa mère.\n" +
            "\n" +
            "Après la victoire du roi de Parse sur les lusitaniens, une période de paix de trois ans s'ensuit.\n" +
            "\n" +
            "En l'an 320 du calendrier Parse, les Lusitaniens relancent une attaque contre Parse après leur conquête de Maryam, tombé plus rapidement que lors de la précédente invasion.\n" +
            "\n" +
            "Dirigeant sa vaste armée, le roi Andragoras III se porte à la rencontre des Lusitaniens dans la plaine d'Atropatènes. Il est accompagné de ses meilleurs généraux, dont les généraux Kahllahn et Darîun, ainsi que du jeune prince qui va livrer sa première bataille à 14 ans.\n" +
            "\n" +
            "Mais Atropatènes se révèle être un piège mortel, tendu par le général Kahllahn, qui a trahit Parse, et par le dénommé \"Masque d'Argent\", un mystérieux étranger qui est général dans l'armée des Lusitaniens.\n" +
            "\n" +
            "L'armée Parse est défaite, subissant des pertes effroyables, et Andragoras est fais prisonnier par Masque d'Argent.\n" +
            "\n" +
            "Le jeune Arslân, que Kahllahn voulait tuer lui même, est sauvé par Darîun. Le prince et le général s'enfuient ensuite pour aller trouver un ami de Darîun, l'ancien seigneur Narsus, un noble Parse excentrique, fin stratège et philosophe, chassé des années plus tôt de la cour de Parse par le roi qui n'aimait pas sa franchise. Avec Narsus et Elam, son serviteur, puis avec d'autres compagnons, le petit groupe se lance dans la quête de réorganiser l'armée Parse et de sauver le royaume, même si la capitale, Ecbâtana, est tombée aux mains des Lusitaniens entretemps...\n" +
            "\n" +
            "Conseillé par ses nouveaux camarades, le jeune Arslân devra apprendre a grandir et a changer sa vision du monde s'il veut libérer son pays de l'envahisseur Lusitanien et de l'usurpateur au masque d'argent...");
        a.addProducer("Production IG");
        a.addProducer("Kadokawa");
        a.addGenre("Action");
        a.addGenre("Adventure");
        a.addGenre("Drama");
        a.addGenre("Fantasy");
        a.addGenre("Historical");
        a.setMaxEp(20);
        a.setAiredEp(17);
        a.setDlEp(15);
        a.setViewedEp(13);
        a.setAiringDate((new GregorianCalendar(2015,4,5)).getTime());
        a.setEndDate((new GregorianCalendar(2015,9,27)).getTime());
        col.addItem(a, "Complété");
        
        int[] ids = {20832, 21218, 20849};
        
        for (int id : ids) {
            try {
                a = broker.getAnime(id).toAnime();
                int idlist = new Random().nextInt(col.getLists().size());
                col.addItem(a, col.getLists().get(idlist));
            } catch (BrokerUnreachableException ex) {
                LOGGER.log(Level.SEVERE, "Cannot get Anime ID {0}, Broker unreachable", id);
            }
        }
      
        return col;
    }
    
    public static Collection getMangas() {
        AnilistBroker broker = new AnilistBroker();
        
        Collection<Manga> col = new Collection<>(2, "Mangas", Manga.class);
        col.addList("En cours").addList("Prévu").addList("Complété");  
          
        int[] ids = {55143, 55155, 55934};
        
        for (int id : ids) {
            Manga m;
            try {
                m = broker.getManga(id).toManga();
            int idlist = new Random().nextInt(col.getLists().size());
            col.addItem(m, col.getLists().get(idlist));
            } catch (BrokerUnreachableException ex) {
                LOGGER.log(Level.SEVERE, "Cannot get Manga ID {0}, Broker unreachable", id);
            }
        }
        
        return col;
    }*/
    /*
    public static Collection getSeries() {
        return new Collection(3, "Séries", Collection.Types.Serie);
    }
    
    public static Collection getFilms() {
        return new Collection(4, "Films", Collection.Types.Film);
    }
    
    public static Collection getLivres() {
        return new Collection(5, "Livres", Collection.Types.Livre);
    }*/
    
}
