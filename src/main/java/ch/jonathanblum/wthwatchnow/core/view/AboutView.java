/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.view;

import ch.jonathanblum.wthwatchnow.core.model.Configuration;
import ch.jonathanblum.wthwatchnow.core.model.ConfigurationManager;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class AboutView extends javax.swing.JFrame {

    private static class SingletonHolder {
        private static final AboutView instance = new AboutView();
    }
    
    public static AboutView getInstance() { return SingletonHolder.instance; }
    /**
     * Creates new form AboutView
     */
    private AboutView() {
        initComponents();
        initData();
    }
    
    private void initData() {
        this.setTitle("À propos" + ConfigurationManager.getInstance().getConf().getShortname());
        lbTitle.setText(ConfigurationManager.getInstance().getConf().getFullname());
        lbVersion.setText(String.format("%s v%s", 
            ConfigurationManager.getInstance().getConf().getShortname(),
            ConfigurationManager.getInstance().getConf().getVersion()
        ));
        getLicense();
        getTechnicalDetails();
        
    }
    
    private void getLicense() {
        
    }
    private void getTechnicalDetails() {
        Configuration conf = ConfigurationManager.getInstance().getConf();
        tpTechnicalDetails.setContentType("text/html");
        String html = "";
           
        html += String.format("<b>Product version</b> : %s<br/>", conf.getVersion());
        html += String.format("<b>Java Version</b> : %s<br/>", System.getProperty("java.version"));
        html += String.format("<b>OS</b> : %s version %s %s <br/>", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"));
        html += String.format("<b>User directory</b> : %s<br/>", System.getProperty("user.home"));
        html += String.format("<b>Configuration directory</b> : %s<br/>", conf.getConfPath());
        html += String.format("<b>Cache directory</b> : %s<br/>", "");
        html += String.format("<b>Log file</b> : %s<br/>", "");
         
        tpTechnicalDetails.setText(html);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        lbTitle = new javax.swing.JLabel();
        lbVersion = new javax.swing.JLabel();
        lbAuthor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taLicense = new javax.swing.JTextArea();
        lbLicense = new javax.swing.JLabel();
        lbTechnicalDetails = new javax.swing.JLabel();
        btClose = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpTechnicalDetails = new javax.swing.JTextPane();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbTitle.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Title");

        lbVersion.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        lbVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbVersion.setText("Version");

        lbAuthor.setText("Auteur: Jonathan Blum <jonathan.blum@eldhar.com>");

        taLicense.setColumns(20);
        taLicense.setRows(5);
        jScrollPane1.setViewportView(taLicense);

        lbLicense.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLicense.setText("License d'utiisation du logiciel");

        lbTechnicalDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTechnicalDetails.setText("Informations techniques");

        btClose.setText("Fermer");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(tpTechnicalDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTechnicalDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbAuthor)
                                .addGap(0, 171, Short.MAX_VALUE))
                            .addComponent(lbLicense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbVersion)
                .addGap(18, 18, 18)
                .addComponent(lbAuthor)
                .addGap(18, 18, 18)
                .addComponent(lbLicense)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTechnicalDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btCloseActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClose;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbAuthor;
    private javax.swing.JLabel lbLicense;
    private javax.swing.JLabel lbTechnicalDetails;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbVersion;
    private javax.swing.JTextArea taLicense;
    private javax.swing.JTextPane tpTechnicalDetails;
    // End of variables declaration//GEN-END:variables
}
