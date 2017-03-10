/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.view;

import ch.jonathanblum.wthwatchnow.manager.model.Serie;

/**
 *
 * @author Jonathan Blum
 */
public class SerieView extends javax.swing.JInternalFrame {
    private Serie currentSerie;

    /**
     * Creates new form SerieView
     */
    public SerieView(Serie serie) {
        super("Serie", false, false, false, false);
        setBorder(null);
        initComponents();
        changeSerie(serie);
    }
    
    public final void changeSerie(Serie next) {
        currentSerie = next;
        initData();
    }
    
    private void initData() {
        setTitle(currentSerie.getTitle());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
