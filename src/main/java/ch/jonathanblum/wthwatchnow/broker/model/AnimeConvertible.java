/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import ch.jonathanblum.wthwatchnow.manager.model.Anime;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public interface AnimeConvertible {

    Anime toAnime();
    
}
