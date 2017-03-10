/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.model;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public abstract class SearchResult { 
   protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

   protected String[] columns;
   protected  int id;
   
   public DefaultTableModel getTableModel() {
        return new DefaultTableModel(columns, 0) {           
            public boolean isCellEditable(int i, int i1) { return false; }       
        };
    }
   
   public abstract int getId();
    
   public abstract Object[] toTableModelRow();
}
