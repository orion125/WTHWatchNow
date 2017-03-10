/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Blum
 */
public abstract class Broker {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public abstract <T extends SearchResult> ArrayList<T> search(String query, String type) throws BrokerUnreachableException;
    public abstract SearchResult get(int id, String type) throws BrokerUnreachableException;
    @Override
    public abstract String toString();
   
}
