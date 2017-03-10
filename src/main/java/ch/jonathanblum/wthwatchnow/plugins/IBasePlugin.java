/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.plugins;

/**
 *
 * @author Jonathan Blum
 */
public interface IBasePlugin {
    
    /**
     * Get the caption to show
     * @return the caption as a string
     */
    public String getCaption();
    /**
     * get the plugin category
     * @return the category id
     */
    public int getCategory();
    
    
    public void run();
}
