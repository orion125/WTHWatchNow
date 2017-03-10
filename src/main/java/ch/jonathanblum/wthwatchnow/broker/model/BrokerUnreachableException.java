/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class BrokerUnreachableException extends Exception {

    /**
     * Creates a new instance of <code>BrokerUnreachable</code> without detail
     * message.
     */
    public BrokerUnreachableException() {
    }

    /**
     * Constructs an instance of <code>BrokerUnreachable</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BrokerUnreachableException(String msg) {
        super(msg);
    }
}
