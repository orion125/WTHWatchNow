/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow;

import ch.jonathanblum.wthwatchnow.core.controller.FrontController;
/**
 * What The Hell Should I Watch Now ?
 * @version 0.0.1
 * @author jonathan Blum <jonathan.blum@eldhar.com>
 */
public class WTHWatchNow {
    
    private static String[] args;

    public static String[] getArgs() { return args; }
    private static void setArgs(String[] args) { WTHWatchNow.args = args; }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        setArgs(args);        
        try {
            new FrontController().Do();
        } catch (Exception ex) {
        }
 
    }
    
}
