/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.broker.view;

import ch.jonathanblum.wthwatchnow.broker.controller.SearchController;
import ch.jonathanblum.wthwatchnow.broker.controller.SearchController.RegisteredBroker;
import ch.jonathanblum.wthwatchnow.broker.model.BrokerUnreachableException;
import ch.jonathanblum.wthwatchnow.broker.model.SearchResult;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class SearchView extends javax.swing.JFrame {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private final String currentType;
    private ArrayList<SearchResult> currentResults;
    
    SearchController controller;
    /**
     * Creates new form SearchView
     * @param controller
     * @param type
     */
    public SearchView(SearchController controller, String type) {
        this.currentResults = new ArrayList();
        this.controller = controller;
        this.currentType = type;
        initComponents();
        initListeners();
        initData();
    }
    
    private void initListeners() {
        cbBrokersList.addActionListener((ActionEvent ae) -> {
            selectCurrentBroker();
        });
        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent de) { checkUserQuery(); }
            public void removeUpdate(DocumentEvent de) { checkUserQuery(); }
            public void changedUpdate(DocumentEvent de) { checkUserQuery(); }
        });
        tbSearchResults.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            checkElementSelection();
        });      
    }
    private void initData() {
        getBrokersList();

    }
    
    private void getBrokersList() {
        ArrayList<RegisteredBroker> brokers;
        switch(currentType.toLowerCase()) {
            case "anime": brokers = controller.getBrokersForType(SearchController.TYPE_ANIME); break;
            case "manga": brokers = controller.getBrokersForType(SearchController.TYPE_MANGA); break;
            default: brokers = null;
        }
        
        if(brokers != null) {
            cbBrokersList.setModel(new DefaultComboBoxModel(brokers.toArray()));
            LOGGER.log(Level.FINE, "Display Registered Brokers List for {0}", currentType);
            cbBrokersList.setEnabled(cbBrokersList.getItemCount() > 0);
            selectCurrentBroker();
        } else {
            LOGGER.log(Level.INFO, "No brokers found for {0}", currentType);
        }
    }
    
    private void selectCurrentBroker() {
        controller.selectCurrentBroker((SearchController.RegisteredBroker)cbBrokersList.getSelectedItem());
        checkBrokerSelection();
    }
    
    private void checkBrokerSelection() {
        if(controller.getCurrentBroker() != null) {
            tfSearch.setEnabled(true);
            checkUserQuery();
        } else {
            tfSearch.setEnabled(false);
            btSearch.setEnabled(false);                            
        }
    }
    private void checkUserQuery() {
        btSearch.setEnabled(!tfSearch.getText().isEmpty());
    }
    
    private void search(String query) {        
        try {
            
            switch(currentType.toLowerCase()) {
                case "anime": currentResults = controller.search(query.trim(), SearchController.TYPE_ANIME); break;
                case "manga": currentResults = controller.search(query.trim(), SearchController.TYPE_MANGA); break;
                default: currentResults = null;
            }
            DefaultTableModel model;
            if(currentResults != null && currentResults.size() > 0) {
                model = currentResults.get(0).getTableModel();
                currentResults.stream().forEach((el) -> {
                    model.addRow(el.toTableModelRow());
                });
                tbSearchResults.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                tbSearchResults.setModel(model);
                resizeColumnWidth();
            }  else {
                JOptionPane.showMessageDialog(null, "Pas de résultats.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (BrokerUnreachableException ex) {
            JOptionPane.showMessageDialog(null, "Impossible d'effectuer la recherche.\nVérifiez votre connexion internet et que le site choisi soit accessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resizeColumnWidth() {
        final TableColumnModel cmodel = tbSearchResults.getColumnModel();
        for (int column = 0; column < cmodel.getColumnCount(); column++) {
            int width = 20;
            for (int row = 0; row < tbSearchResults.getRowCount(); row++) {
                TableCellRenderer renderer = tbSearchResults.getCellRenderer(row, column);
                Component comp = tbSearchResults.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width+1, width);
            }
            cmodel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    private void checkElementSelection() {
        btAddToCollection.setEnabled(tbSearchResults.getSelectedRowCount() > 0);
    }
    
    public void addElementToCollection() {
        try {
            controller.AddToCurrentCollection(currentResults.get(tbSearchResults.getSelectedRow()), currentType);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Impossible d'effectuer l'ajout.\nVérifiez votre connexion internet et que le site choisi soit accessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbBrokersList = new javax.swing.JLabel();
        cbBrokersList = new javax.swing.JComboBox();
        btSearch = new javax.swing.JButton();
        tfSearch = new javax.swing.JTextField();
        spSearchResults = new javax.swing.JScrollPane();
        tbSearchResults = new javax.swing.JTable();
        paActions = new javax.swing.JPanel();
        btAddToCollection = new javax.swing.JButton();
        btManualAdd = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rechercher");

        lbBrokersList.setText("Fournisseur :");

        cbBrokersList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pas de broker disponible pour cette collection" }));
        cbBrokersList.setEnabled(false);

        btSearch.setText("Rechercher");
        btSearch.setEnabled(false);
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        tfSearch.setEnabled(false);

        tbSearchResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbSearchResults.setEditingColumn(0);
        tbSearchResults.setEditingRow(0);
        spSearchResults.setViewportView(tbSearchResults);

        btAddToCollection.setText("Ajouter à la collection");
        btAddToCollection.setEnabled(false);
        btAddToCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddToCollectionActionPerformed(evt);
            }
        });
        paActions.add(btAddToCollection);

        btManualAdd.setText("Ajout manuel");
        btManualAdd.setEnabled(false);
        paActions.add(btManualAdd);

        btCancel.setText("Annuler");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        paActions.add(btCancel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbBrokersList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbBrokersList)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spSearchResults, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paActions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbBrokersList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbBrokersList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spSearchResults, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddToCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddToCollectionActionPerformed
        addElementToCollection();
    }//GEN-LAST:event_btAddToCollectionActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        search(tfSearch.getText());
    }//GEN-LAST:event_btSearchActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddToCollection;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btManualAdd;
    private javax.swing.JButton btSearch;
    private javax.swing.JComboBox cbBrokersList;
    private javax.swing.JLabel lbBrokersList;
    private javax.swing.JPanel paActions;
    private javax.swing.JScrollPane spSearchResults;
    private javax.swing.JTable tbSearchResults;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
