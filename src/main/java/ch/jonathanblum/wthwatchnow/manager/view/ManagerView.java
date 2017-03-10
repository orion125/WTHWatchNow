/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.manager.view;

import ch.jonathanblum.wthwatchnow.core.view.AboutView;
import ch.jonathanblum.wthwatchnow.core.model.ConfigurationManager;
import ch.jonathanblum.wthwatchnow.manager.controller.ManagerController;
import ch.jonathanblum.wthwatchnow.manager.model.Collection;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionUpdateListener;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionItem;
import ch.jonathanblum.wthwatchnow.manager.model.CollectionManagerChangedListener;
import ch.jonathanblum.wthwatchnow.users.controller.UsersController;
import ch.jonathanblum.wthwatchnow.users.model.User;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Main view for Managing collections
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public final class ManagerView extends javax.swing.JFrame implements CollectionUpdateListener, CollectionManagerChangedListener {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final ManagerController controller;
    private TableModel tbModel;
    private JInternalFrame currentInternalFrame;
    
    /**
     * Creates new form ManagerView
     * @param controller
     */
    public ManagerView(ManagerController controller) {
        this.controller = controller;
        initComponents();
        initListeners();
        initData();
    }
    
    private void initListeners() {
        tfCollectionListFilter.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent de) { filterCollectionTable(); }
            public void removeUpdate(DocumentEvent de) { filterCollectionTable(); }
            public void changedUpdate(DocumentEvent de) { filterCollectionTable(); }           
        });
        
        tbCollectionContentTable.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            dtpCollectionItemInfo.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
            setInternalFrameContent();
        });              
    }
    
    private void initData() {
        setTitle(ConfigurationManager.getInstance().getConf().getFullname());
        User user = UsersController.getInstance().getCurrentUser();
        if(user.getAccess() == User.Access.Admin) {
            miManageUsers.setEnabled(false); // TODO true when no longer NIY
            miManageUsers.setVisible(true);
        } else {
            miManageUsers.setEnabled(false);
            miManageUsers.setVisible(false);            
        }
        this.setTitle(ConfigurationManager.getInstance().getConf().getFullname());
        miChangeUser.setText(String.format("Changer l'utilisateur (%s)", Character.toUpperCase(user.getUsername().charAt(0))+user.getUsername().substring(1)));
        updateCollectionSelector();
    }
    
    private Collection getSelectedCollection() {
        return (Collection) cbCollectionSelector.getSelectedItem();
    }
    
    private void changeSelectedCollection() {
        closeInternalFrame();
        controller.getCollectionManager().getCurrentCollection().removeCollectionChangedListener(this);
        controller.getCollectionManager().setCurrentCollection(getSelectedCollection());
    }
    
    private CollectionItem getSelectedItem() {
        int index = tbCollectionContentTable.getSelectedRow();
        if(index < 0)
            return null;
        else {
            CollectionItem item = (CollectionItem) tbCollectionContentTable.getValueAt(
                    index,
                    0
            );
            return item;
        }
    }
    private void setInternalFrameContent() {
        if(getSelectedItem() != null && currentInternalFrame == null)
            openInternalFrame();
        
        controller.getCollectionController().changeItem(getSelectedItem());
        LOGGER.log(Level.FINE, "Display data for {0}", getSelectedItem());
        
    }
    
    
    private void openInternalFrame() {  
        LOGGER.log(Level.FINE, "Opening Internal Window", getSelectedItem());
        try {
            currentInternalFrame = controller.getCollectionController().getView(getSelectedItem());
        } catch (NullPointerException ex) {
            LOGGER.log(Level.WARNING, "No Controller was returned for {0}", controller.getCollectionManager().getCurrentCollection());
            return;
        }

        currentInternalFrame.show();
        dtpCollectionItemInfo.add(currentInternalFrame);
        try {
            currentInternalFrame.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            LOGGER.log(Level.SEVERE, "Exception when maximizing internal view", ex);
        }
        pack();
    }
    
    private void closeInternalFrame() {
        try {
            if(currentInternalFrame!= null)
                currentInternalFrame.setClosed(true);
            
        } catch (PropertyVetoException ex) { }
        currentInternalFrame = null;
    }
    
    
    public void close() {
        // Call controller to exit application   
        
        // TODO Exit Manager to persist changes
        System.exit(0);
    }
    
    protected void filterCollectionTable() {
        if(!tfCollectionListFilter.getText().isEmpty()) {
            String[] matches = tfCollectionListFilter.getText().toLowerCase().split(" ");
            
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                @Override
                public boolean include(RowFilter.Entry entry) {          
                 
                    String val = "";
                    for (int i = 0; i < entry.getValueCount(); i++)
                        val += "*" + entry.getStringValue(i).toLowerCase();

                    Boolean found = true;
                    for(String s : matches)
                        if(!val.contains(s)) 
                            found=false;
                    
                    return found;
                }
            };
        
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbModel);
            sorter.setRowFilter(filter);
            tbCollectionContentTable.setRowSorter(sorter);    
        } else {
            tbCollectionContentTable.setRowSorter(null);
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

        pMainWindow = new javax.swing.JPanel();
        cbCollectionSelector = new javax.swing.JComboBox();
        btCollectionSelector = new javax.swing.JButton();
        spCollectionContent = new javax.swing.JSplitPane();
        pCollectionList = new javax.swing.JPanel();
        tfCollectionListFilter = new javax.swing.JTextField();
        spCollectionContentTable = new javax.swing.JScrollPane();
        tbCollectionContentTable = new javax.swing.JTable();
        dtpCollectionItemInfo = new javax.swing.JDesktopPane();
        btAddItem = new javax.swing.JButton();
        btEditItem = new javax.swing.JButton();
        btDelItem = new javax.swing.JButton();
        mbMain = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        miChangeUser = new javax.swing.JMenuItem();
        miManageUsers = new javax.swing.JMenuItem();
        miClose = new javax.swing.JMenuItem();
        mnCollections = new javax.swing.JMenu();
        miAddCollection = new javax.swing.JMenuItem();
        miEditCollection = new javax.swing.JMenuItem();
        miRemoveCollection = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miManageLists = new javax.swing.JMenuItem();
        mnItem = new javax.swing.JMenu();
        miAddElement = new javax.swing.JMenuItem();
        miEditElement = new javax.swing.JMenuItem();
        miDelElement = new javax.swing.JMenuItem();
        mnHelp = new javax.swing.JMenu();
        miAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("What the Hell Should I Watch Now ?");

        cbCollectionSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Animes", "Mangas", "Séries", "Films", "Livres" }));
        cbCollectionSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCollectionSelectorActionPerformed(evt);
            }
        });

        btCollectionSelector.setText("Charger");
        btCollectionSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCollectionSelectorActionPerformed(evt);
            }
        });

        pCollectionList.setMinimumSize(new java.awt.Dimension(150, 100));

        tfCollectionListFilter.setToolTipText("Filter by adding words as name or status");
        tfCollectionListFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCollectionListFilterActionPerformed(evt);
            }
        });

        tbCollectionContentTable.setAutoCreateRowSorter(true);
        tbCollectionContentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbCollectionContentTable.setMinimumSize(new java.awt.Dimension(100, 800));
        tbCollectionContentTable.setPreferredSize(new java.awt.Dimension(100, 800));
        tbCollectionContentTable.setRequestFocusEnabled(false);
        tbCollectionContentTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbCollectionContentTablePropertyChange(evt);
            }
        });
        spCollectionContentTable.setViewportView(tbCollectionContentTable);

        javax.swing.GroupLayout pCollectionListLayout = new javax.swing.GroupLayout(pCollectionList);
        pCollectionList.setLayout(pCollectionListLayout);
        pCollectionListLayout.setHorizontalGroup(
            pCollectionListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tfCollectionListFilter)
            .addComponent(spCollectionContentTable)
        );
        pCollectionListLayout.setVerticalGroup(
            pCollectionListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCollectionListLayout.createSequentialGroup()
                .addComponent(tfCollectionListFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spCollectionContentTable, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE))
        );

        spCollectionContent.setLeftComponent(pCollectionList);

        javax.swing.GroupLayout dtpCollectionItemInfoLayout = new javax.swing.GroupLayout(dtpCollectionItemInfo);
        dtpCollectionItemInfo.setLayout(dtpCollectionItemInfoLayout);
        dtpCollectionItemInfoLayout.setHorizontalGroup(
            dtpCollectionItemInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 820, Short.MAX_VALUE)
        );
        dtpCollectionItemInfoLayout.setVerticalGroup(
            dtpCollectionItemInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );

        spCollectionContent.setRightComponent(dtpCollectionItemInfo);

        btAddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actions-list-add-icon.png"))); // NOI18N
        btAddItem.setToolTipText("Ajouter un élément");
        btAddItem.setAlignmentY(0.0F);
        btAddItem.setAutoscrolls(true);
        btAddItem.setMaximumSize(new java.awt.Dimension(27, 27));
        btAddItem.setMinimumSize(new java.awt.Dimension(27, 27));
        btAddItem.setPreferredSize(new java.awt.Dimension(27, 27));
        btAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddItemActionPerformed(evt);
            }
        });

        btEditItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actions-document-edit-icon.png"))); // NOI18N
        btEditItem.setToolTipText("Éditer l'élément sélectionné");
        btEditItem.setEnabled(false);
        btEditItem.setMaximumSize(new java.awt.Dimension(27, 27));
        btEditItem.setMinimumSize(new java.awt.Dimension(27, 27));
        btEditItem.setPreferredSize(new java.awt.Dimension(27, 27));
        btEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditItemActionPerformed(evt);
            }
        });

        btDelItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Actions-edit-delete-icon.png"))); // NOI18N
        btDelItem.setToolTipText("Supprimer l'élément sélectionné");
        btDelItem.setEnabled(false);
        btDelItem.setMaximumSize(new java.awt.Dimension(27, 27));
        btDelItem.setMinimumSize(new java.awt.Dimension(27, 27));
        btDelItem.setPreferredSize(new java.awt.Dimension(27, 27));
        btDelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDelItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pMainWindowLayout = new javax.swing.GroupLayout(pMainWindow);
        pMainWindow.setLayout(pMainWindowLayout);
        pMainWindowLayout.setHorizontalGroup(
            pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMainWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spCollectionContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1303, Short.MAX_VALUE)
                    .addGroup(pMainWindowLayout.createSequentialGroup()
                        .addComponent(cbCollectionSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCollectionSelector)
                        .addGap(18, 18, 18)
                        .addComponent(btAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEditItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btDelItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pMainWindowLayout.setVerticalGroup(
            pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMainWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pMainWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCollectionSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btCollectionSelector))
                        .addComponent(btDelItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btEditItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spCollectionContent, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );

        mnFile.setText("Fichier");

        miChangeUser.setText("Changer d'utilisateur");
        miChangeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miChangeUserActionPerformed(evt);
            }
        });
        mnFile.add(miChangeUser);

        miManageUsers.setText("Gérer les utilisateurs");
        miManageUsers.setEnabled(false);
        miManageUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miManageUsersActionPerformed(evt);
            }
        });
        mnFile.add(miManageUsers);

        miClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        miClose.setText("Quitter");
        miClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCloseActionPerformed(evt);
            }
        });
        mnFile.add(miClose);

        mbMain.add(mnFile);

        mnCollections.setText("Collections");

        miAddCollection.setText("Ajouter une collection");
        miAddCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddCollectionActionPerformed(evt);
            }
        });
        mnCollections.add(miAddCollection);

        miEditCollection.setText("Renommer la collection");
        miEditCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditCollectionActionPerformed(evt);
            }
        });
        mnCollections.add(miEditCollection);

        miRemoveCollection.setText("Supprimer la collection");
        miRemoveCollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRemoveCollectionActionPerformed(evt);
            }
        });
        mnCollections.add(miRemoveCollection);
        mnCollections.add(jSeparator1);

        miManageLists.setText("Gérer les listes");
        mnCollections.add(miManageLists);

        mbMain.add(mnCollections);

        mnItem.setText("Élements");

        miAddElement.setText("Ajouter un élément");
        miAddElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddElementActionPerformed(evt);
            }
        });
        mnItem.add(miAddElement);

        miEditElement.setText("Modifier l'élément sélectionné");
        miEditElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditElementActionPerformed(evt);
            }
        });
        mnItem.add(miEditElement);

        miDelElement.setText("Supprimer l'élément sélectionné");
        miDelElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDelElementActionPerformed(evt);
            }
        });
        mnItem.add(miDelElement);

        mbMain.add(mnItem);

        mnHelp.setText("Aide");
        mnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHelpActionPerformed(evt);
            }
        });

        miAbout.setText("À Propos");
        miAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAboutActionPerformed(evt);
            }
        });
        mnHelp.add(miAbout);

        mbMain.add(mnHelp);

        setJMenuBar(mbMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMainWindow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMainWindow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCollectionSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCollectionSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCollectionSelectorActionPerformed

    private void tfCollectionListFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCollectionListFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCollectionListFilterActionPerformed

    private void miCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCloseActionPerformed
        close();
    }//GEN-LAST:event_miCloseActionPerformed

    private void btCollectionSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCollectionSelectorActionPerformed
       changeSelectedCollection();
    }//GEN-LAST:event_btCollectionSelectorActionPerformed

    private void tbCollectionContentTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbCollectionContentTablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tbCollectionContentTablePropertyChange

    private void miRemoveCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRemoveCollectionActionPerformed
        CollectionDeleteView collectionDeleteView = new CollectionDeleteView(this, true);
        collectionDeleteView.setLocationRelativeTo(null);
        collectionDeleteView.setVisible(true);
        if(collectionDeleteView.getReturnStatus() == CollectionDeleteView.RET_OK)
            controller.getCollectionManager().deleteCurrentCollection();
    }//GEN-LAST:event_miRemoveCollectionActionPerformed

    private void miAddCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddCollectionActionPerformed
        CollectionAddEditView dialog_add = new CollectionAddEditView(this, true, controller.getCollectionManager(), false);
        dialog_add.setLocationRelativeTo(this);
        dialog_add.setVisible(true);
        // if(dialog_add.getReturnStatus() == CollectionAddEditView.RET_OK)
        //
    }//GEN-LAST:event_miAddCollectionActionPerformed

    private void miEditCollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditCollectionActionPerformed
        CollectionAddEditView dialog_add = new CollectionAddEditView(this, true, controller.getCollectionManager(), true);
        dialog_add.setLocationRelativeTo(this);
        dialog_add.setVisible(true);

    }//GEN-LAST:event_miEditCollectionActionPerformed

    private void miAddElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddElementActionPerformed
        controller.search();
    }//GEN-LAST:event_miAddElementActionPerformed

    private void miChangeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miChangeUserActionPerformed
        setVisible(false);
        dispose();
        UsersController.getInstance().authenticate();   
    }//GEN-LAST:event_miChangeUserActionPerformed

    private void btDelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDelItemActionPerformed
        delItem();
    }//GEN-LAST:event_btDelItemActionPerformed

    private void btAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddItemActionPerformed
        addItem();
    }//GEN-LAST:event_btAddItemActionPerformed

    private void btEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditItemActionPerformed
        editItem();
    }//GEN-LAST:event_btEditItemActionPerformed

    private void miEditElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditElementActionPerformed
        editItem();
    }//GEN-LAST:event_miEditElementActionPerformed

    private void miDelElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDelElementActionPerformed
        delItem();
    }//GEN-LAST:event_miDelElementActionPerformed

    private void mnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHelpActionPerformed

    }//GEN-LAST:event_mnHelpActionPerformed

    private void miAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAboutActionPerformed
        AboutView about = AboutView.getInstance();
        about.pack();
        about.setVisible(true);
        about.setLocationRelativeTo(this);
    }//GEN-LAST:event_miAboutActionPerformed

    private void miManageUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miManageUsersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_miManageUsersActionPerformed

    private void addItem() {
        controller.search();
    }
    private void editItem() {
        
    }
    private void delItem() {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddItem;
    private javax.swing.JButton btCollectionSelector;
    private javax.swing.JButton btDelItem;
    private javax.swing.JButton btEditItem;
    private javax.swing.JComboBox cbCollectionSelector;
    private javax.swing.JDesktopPane dtpCollectionItemInfo;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar mbMain;
    private javax.swing.JMenuItem miAbout;
    private javax.swing.JMenuItem miAddCollection;
    private javax.swing.JMenuItem miAddElement;
    private javax.swing.JMenuItem miChangeUser;
    private javax.swing.JMenuItem miClose;
    private javax.swing.JMenuItem miDelElement;
    private javax.swing.JMenuItem miEditCollection;
    private javax.swing.JMenuItem miEditElement;
    private javax.swing.JMenuItem miManageLists;
    private javax.swing.JMenuItem miManageUsers;
    private javax.swing.JMenuItem miRemoveCollection;
    private javax.swing.JMenu mnCollections;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenu mnHelp;
    private javax.swing.JMenu mnItem;
    private javax.swing.JPanel pCollectionList;
    private javax.swing.JPanel pMainWindow;
    private javax.swing.JSplitPane spCollectionContent;
    private javax.swing.JScrollPane spCollectionContentTable;
    private javax.swing.JTable tbCollectionContentTable;
    private javax.swing.JTextField tfCollectionListFilter;
    // End of variables declaration//GEN-END:variables
    
    private void updateCollectionSelector() {
        LOGGER.log(Level.FINE, "Updating list of avaiable collections");
        // Update Selector
        cbCollectionSelector.setModel(
                new DefaultComboBoxModel(controller.getCollectionManager()
                                                   .getCollections().toArray())
        );
        // Select first item in case of no collection selected
        if((controller.getCollectionManager().getCurrentCollection() == null) 
            && (cbCollectionSelector.getItemCount() > 0)) {
            cbCollectionSelector.setSelectedIndex(0);
            updateCurrentSelectedCollection();
        }
     }
    
    private void updateCurrentSelectedCollection() {
        LOGGER.log(Level.FINE, "Updating Collection content: {0}", controller.getCollectionManager().getCurrentCollection());
  
        // Close internal window
        closeInternalFrame();
        
        // Safeguard
        if(controller.getCollectionManager().getCurrentCollection() == null) {
            LOGGER.log(Level.SEVERE, "Trying to update current collection butthere's no current collection");
            //return;
        }
         
        // Select the right item if it's not the current one
        if(controller.getCollectionManager().getCurrentCollection() != (Collection)cbCollectionSelector.getSelectedItem())
           cbCollectionSelector.setSelectedItem(controller.getCollectionManager().getCurrentCollection());       

        // Get Current Collection
        controller.getCollectionManager().getCurrentCollection().addCollectionChangedListener(this);
        tbModel = controller.getCollectionManager().getCurrentCollection();
        
        try {          
            tbCollectionContentTable.setModel(tbModel);
            JComboBox cbListe = new JComboBox(((Collection) tbModel).getLists().toArray());
            TableColumn col = tbCollectionContentTable.getColumnModel().getColumn(1);
            col.setCellEditor(new DefaultCellEditor(cbListe));    
        } catch (IllegalArgumentException ex) {
            tbCollectionContentTable.setModel(new DefaultTableModel());
        }
        
        // Update Menu
        mnItem.setText(controller.getCollectionManager().getCurrentCollection().toString());
        
        // Select first line
        if(tbCollectionContentTable.getRowCount() > 0)
            tbCollectionContentTable.setRowSelectionInterval(0, 0);
    }


    @Override
    public void updateCollectionListEvent(ChangeEvent evt) {
        LOGGER.log(Level.FINE,"changeCollectionListEvent received");
        updateCollectionSelector();
    }

    @Override
    public void changeCurrentCollectionEvent(ChangeEvent evt) {
        LOGGER.log(Level.FINE,"changeCurrentCollection received");
        updateCurrentSelectedCollection();
    }

    @Override
    public void updateCollectionEvent(ChangeEvent evt) {
        tbCollectionContentTable.repaint();
    }
    
    
}
