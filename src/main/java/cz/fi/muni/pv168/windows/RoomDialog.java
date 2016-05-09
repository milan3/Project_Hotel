/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168.windows;

import cz.fi.muni.pv168.Room;
import cz.fi.muni.pv168.RoomManager;
import cz.fi.muni.pv168.RoomManagerImpl;
import cz.fi.muni.pv168.ServiceFailureException;
import java.math.BigDecimal;
import javafx.scene.paint.Color;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class RoomDialog extends javax.swing.JDialog {
    private Room room;
    
    /**
     * Creates new form RoomDialog
     */
    public RoomDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNumber = new javax.swing.JTextField();
        lblPrice = new javax.swing.JTextField();
        chkBoxBalcony = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        comboBoxBeds = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Number:");

        jLabel3.setText("Beds:");

        jLabel4.setText("Price:");

        jLabel5.setText("Balcony:");
        jLabel5.setToolTipText("");

        chkBoxBalcony.setText("Has balcony");

        jButton1.setText("submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblError.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblError.setForeground(new java.awt.Color(153, 0, 51));

        comboBoxBeds.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblNumber))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3))
                                    .addGap(35, 35, 35)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                        .addComponent(comboBoxBeds, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(chkBoxBalcony))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblError)))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(chkBoxBalcony))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(lblError)
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int oldNumber = room.getNumber();
        
        lblNumber.setBackground(java.awt.Color.white);
        comboBoxBeds.setBackground(java.awt.Color.white);
        lblPrice.setBackground(java.awt.Color.white);
        lblError.setText("");
        
        room.setBalcony(chkBoxBalcony.isSelected());
        
        try {
            room.setNumber(Integer.valueOf(lblNumber.getText()));
        } catch(Exception e) {
            lblNumber.setBackground(java.awt.Color.red);
            return;
        }
        
        room.setNumberOfBeds(comboBoxBeds.getSelectedIndex() + 1);
        
        try {
            room.setPrice(BigDecimal.valueOf(Double.valueOf(lblPrice.getText())));
        }
        catch (Exception e) {
            lblPrice.setBackground(java.awt.Color.red);
            return;
        }
        
        RoomManager rm = RoomManagerImpl.getInstance();

        try {
            if (room.getId() == null) {        
                rm.createRoom(room);
            } else {
                rm.updateRoom(room);
            }
            
             dispose();
        } catch(ServiceFailureException e) {
            lblError.setText(e.getMessage());
            
            switch (e.getMessage()) {
                case RoomManagerImpl.NUMBER_EXISTS:
                    lblNumber.setBackground(java.awt.Color.red);
                    return;
                case RoomManagerImpl.WRONG_NUMBER_OF_BEDS:
                    comboBoxBeds.setBackground(java.awt.Color.red);
                    return;
                default:
                    return;
            }
            
        } catch(IllegalArgumentException e) {
            switch (e.getMessage()) {
                case RoomManagerImpl.NEGATIVE_NUMBER:
                    lblNumber.setBackground(java.awt.Color.red);
                    break;
                case RoomManagerImpl.NEGATIVE_PRICE:
                    lblPrice.setBackground(java.awt.Color.red);
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RoomDialog dialog = new RoomDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public void setRoom(Room room) {
        this.room = room;
        
        if (room.getId() != null) {
            chkBoxBalcony.setSelected(room.hasBalcony());
            lblNumber.setText(String.valueOf(room.getNumber()));
            comboBoxBeds.setSelectedIndex(room.getNumberOfBeds() - 1);
            lblPrice.setText(String.valueOf(room.getPrice()));
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkBoxBalcony;
    private javax.swing.JComboBox comboBoxBeds;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblError;
    private javax.swing.JTextField lblNumber;
    private javax.swing.JTextField lblPrice;
    // End of variables declaration//GEN-END:variables
}
