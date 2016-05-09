/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168.windows;

import cz.fi.muni.pv168.Accommodation;
import cz.fi.muni.pv168.AccommodationsTableModel;
import cz.fi.muni.pv168.Guest;
import cz.fi.muni.pv168.GuestManagerImpl;
import cz.fi.muni.pv168.HotelManager;
import cz.fi.muni.pv168.HotelManagerImpl;
import cz.fi.muni.pv168.Room;
import cz.fi.muni.pv168.RoomManager;
import cz.fi.muni.pv168.RoomManagerImpl;
import java.awt.Color;
import java.awt.Frame;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class AccommodationsDialog extends javax.swing.JDialog {
    private final AccommodationsTableModel model;
    private final List<Room> rooms;
    private final RoomManager rm = RoomManagerImpl.getInstance();
    private final HotelManager hm = HotelManagerImpl.getInstance();
    /**
     * Creates new form AccommodationsDialog
     */
    public AccommodationsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tableAccommodations.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tableAccommodations.getRowCount() > 0) {
                    buttonChange.setEnabled(true);
                    buttonRemove.setEnabled(true);
                } else {
                    buttonChange.setEnabled(false);
                    buttonRemove.setEnabled(false);
                }
            }
        });
        
        model = (AccommodationsTableModel)tableAccommodations.getModel();
        rooms = rm.getAllRooms();   
        
        for (Room room : rooms) {
            comboBoxRooms.addItem(room.getNumber());
        }
        
        lblAvailable.setOpaque(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAccommodations = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        buttonAccommodate = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();
        comboBoxRooms = new javax.swing.JComboBox();
        buttonChange = new javax.swing.JButton();
        lblAvailable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tableAccommodations.setModel(new AccommodationsTableModel());
        jScrollPane1.setViewportView(tableAccommodations);

        jLabel1.setText("Room:");

        buttonAccommodate.setText("accommodate guest");
        buttonAccommodate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAccommodateActionPerformed(evt);
            }
        });

        buttonRemove.setText("remove accommodation");
        buttonRemove.setEnabled(false);
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });

        comboBoxRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxRoomsActionPerformed(evt);
            }
        });

        buttonChange.setText("change accommodation");
        buttonChange.setEnabled(false);
        buttonChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(lblAvailable))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonRemove)
                            .addComponent(buttonAccommodate)
                            .addComponent(buttonChange))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboBoxRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAvailable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAccommodate)
                .addGap(18, 18, 18)
                .addComponent(buttonRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(buttonChange)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAccommodateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAccommodateActionPerformed
        Accommodation accommodation = null;
        final GuestAccommodationDialog gaw = new GuestAccommodationDialog((Frame) SwingUtilities.getWindowAncestor(this), true, accommodation);
        gaw.setRoom(getSelectedRoom());
        gaw.setVisible(true);
        accommodation = gaw.getAccommodation();
            
        if (accommodation != null) {
            model.addAccommodation(accommodation);
            selectLastRow();
            
            boolean result = updateAvailableLabel(accommodation.getRoom());
            
            if (result) {
                buttonAccommodate.setEnabled(true);
            } else {
                buttonAccommodate.setEnabled(false);
            }
        }
    }//GEN-LAST:event_buttonAccommodateActionPerformed

    private void comboBoxRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxRoomsActionPerformed
        Room room = getSelectedRoom();
        fillComboBox(room);
        selectFirstRow();
        
        boolean result = updateAvailableLabel(room);
        
        if (result) {
            buttonAccommodate.setEnabled(true);
        } else {
            buttonAccommodate.setEnabled(false);
        }
    }//GEN-LAST:event_comboBoxRoomsActionPerformed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        Room room = getSelectedRoom();
        int selectedRow = getSelectedRow();
        Accommodation acc = getSelectedAccommodation();
        hm.cancelAccommodation(acc.getGuest());
        model.deleteAccommodation(selectedRow, acc);
        selectRow(selectedRow);
        
        boolean result = updateAvailableLabel(room);
        
        if (result) {
            buttonAccommodate.setEnabled(true);
        } else {
            buttonAccommodate.setEnabled(false);
        }
    }//GEN-LAST:event_buttonRemoveActionPerformed

    private void buttonChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangeActionPerformed
        Accommodation accommodation = getSelectedAccommodation();
        final GuestAccommodationDialog gaw = new GuestAccommodationDialog((Frame) SwingUtilities.getWindowAncestor(this), true, accommodation);
        gaw.setVisible(true);
        model.setAccommodationAt(getSelectedRow(), accommodation);
    }//GEN-LAST:event_buttonChangeActionPerformed

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
            java.util.logging.Logger.getLogger(AccommodationsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccommodationsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccommodationsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccommodationsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AccommodationsDialog dialog = new AccommodationsDialog(new javax.swing.JFrame(), true);
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
        comboBoxRooms.setSelectedItem(room.getNumber());
    }
    
    private void fillComboBox(Room room) {
        List<Accommodation> accs = hm.findAccommodations(room);
        
        model.clear();
        model.addAll(hm.findAccommodations(room));
        boolean result = updateAvailableLabel(room);
        
        if (result) {
            buttonAccommodate.setEnabled(true);
        } else {
            buttonAccommodate.setEnabled(false);
        }
    }
    
    private boolean updateAvailableLabel(Room room) {
        if (hm.isAvailable(room)) {
            lblAvailable.setText("Available");
            lblAvailable.setBackground(java.awt.Color.GREEN);
            return true;
        } else {
            lblAvailable.setText("Unavailable");
            lblAvailable.setBackground(java.awt.Color.RED);
            return false;
        }
    }
    
    private Accommodation getSelectedAccommodation() {
        return model.getAccommodationAt(tableAccommodations.convertRowIndexToModel(getSelectedRow()));
    }
    
    private int getSelectedRow() {
        return tableAccommodations.getSelectedRow();
    }
    
    private Room getSelectedRoom() {
        return rm.getRoom(Integer.valueOf(String.valueOf(comboBoxRooms.getSelectedItem())));
    }
    
    private void selectRow(int index) {
        if (tableAccommodations.getRowCount() > index) {
            tableAccommodations.setRowSelectionInterval(index, index);
        } else if (tableAccommodations.getRowCount() > 0){
            selectLastRow();
        }
    }
    
    private void selectFirstRow() {
        selectRow(0);
    }
    
    private void selectLastRow() {
        selectRow(tableAccommodations.getRowCount() - 1);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAccommodate;
    private javax.swing.JButton buttonChange;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JComboBox comboBoxRooms;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvailable;
    private javax.swing.JTable tableAccommodations;
    // End of variables declaration//GEN-END:variables
}
