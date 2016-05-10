/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168.windows;

import cz.fi.muni.pv168.Room;
import cz.fi.muni.pv168.RoomManager;
import cz.fi.muni.pv168.RoomManagerImpl;
import cz.fi.muni.pv168.RoomsTableModel;
import cz.fi.muni.pv168.TableRoomsFilter;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class RoomWindow extends javax.swing.JFrame {
    private final RoomManager rm;
    private final RoomsTableModel model;
    private TableRowSorter<RoomsTableModel> sorter;
    /**
     * Creates new form RoomWindow
     */
    public RoomWindow() {
        initComponents();
        model = (RoomsTableModel) roomsTable.getModel();
        rm = RoomManagerImpl.getInstance();
        
        roomsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (roomsTable.getRowCount() > 0) {
                    buttonManage.setEnabled(true);
                    buttonEdit.setEnabled(true);
                    buttonRemove.setEnabled(true);
                } else {
                    buttonManage.setEnabled(false);
                    buttonEdit.setEnabled(false);
                    buttonRemove.setEnabled(false);
                }
            }
        });
        
        sorter = new TableRowSorter<RoomsTableModel>(model);
        roomsTable.setRowSorter(sorter);   
                
        new SwingWorker<Void,Void>(){
            @Override
            public Void doInBackground() throws Exception {
                RoomManager rm = RoomManagerImpl.getInstance();      
                model.addAll(rm.getAllRooms()); 
                lblFilteredRooms.setText(String.valueOf(roomsTable.getRowCount()) + " filtered rooms");
                selectFirstRow();
                return null;
            }
        }.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup8 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomsTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFieldNumber = new javax.swing.JTextField();
        chkBoxAvailable = new javax.swing.JCheckBox();
        chkBoxBalcony = new javax.swing.JCheckBox();
        lblFilteredRooms = new javax.swing.JLabel();
        buttonManage = new javax.swing.JButton();
        comboBoxBeds = new javax.swing.JComboBox();
        buttonRemove = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        chkBoxFilter = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roomsTable.setModel(new RoomsTableModel());
        roomsTable.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                roomsTableComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                roomsTableComponentRemoved(evt);
            }
        });
        jScrollPane1.setViewportView(roomsTable);

        jLabel6.setText("Number");

        jLabel7.setText("Beds");

        txtFieldNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFieldNumberKeyReleased(evt);
            }
        });

        chkBoxAvailable.setText("Available");
        chkBoxAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxAvailableActionPerformed(evt);
            }
        });

        chkBoxBalcony.setText("Balcony");
        chkBoxBalcony.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxBalconyActionPerformed(evt);
            }
        });

        lblFilteredRooms.setText("0 filtered rooms");

        buttonManage.setText("manage accommodations");
        buttonManage.setEnabled(false);
        buttonManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonManageActionPerformed(evt);
            }
        });

        comboBoxBeds.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" }));
        comboBoxBeds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxBedsActionPerformed(evt);
            }
        });

        buttonRemove.setText("remove");
        buttonRemove.setEnabled(false);
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });

        jButton4.setText("add");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        buttonEdit.setText("edit");
        buttonEdit.setEnabled(false);
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        chkBoxFilter.setText("Use filter");
        chkBoxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBoxFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkBoxAvailable)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFieldNumber)
                                    .addComponent(comboBoxBeds, 0, 89, Short.MAX_VALUE)))
                            .addComponent(chkBoxBalcony)
                            .addComponent(lblFilteredRooms)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(chkBoxFilter))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(buttonManage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEdit)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(chkBoxFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxBalcony, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxAvailable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFilteredRooms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonManage)
                    .addComponent(buttonRemove)
                    .addComponent(jButton4)
                    .addComponent(buttonEdit))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonManageActionPerformed
        Room room = getSelectedRoom();
                
        final AccommodationsDialog aw = new AccommodationsDialog(this, true);
        aw.setRoom(room);
        aw.setVisible(true);
        
        for (int i = 0; i < model.getRowCount(); i++) {
            model.fireTableCellUpdated(i, 1);
            model.fireTableCellUpdated(i, 4);
        }
    }//GEN-LAST:event_buttonManageActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final RoomDialog w = new RoomDialog(this, true);
        Room room = new Room(); 
        w.setRoom(room);
        w.setVisible(true);
        
        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                if (room.getId() != null) {
                    model.addRoom(rm.getRoom(room.getNumber()));
                    selectLastRow();   
                }
  
                return null;
            }
        }.execute();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        final RoomDialog w = new RoomDialog(this, true);
        int selectedRow = getSelectedRow();
        Room room = getSelectedRoom();
        w.setRoom(room);
        w.setVisible(true);
        
        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                model.setRoomAt(selectedRow, rm.getRoom(room.getNumber()));
                return null;
            }
        }.execute();     
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveActionPerformed
        int selectedRow = getSelectedRow();
        Room room = getSelectedRoom();
        model.deleteRoom(selectedRow, room);
        
        new SwingWorker<Void, Void>(){
            @Override
            public Void doInBackground() throws Exception {
                rm.deleteRoom(room);
                return null;
            }
        }.execute();
        
        selectRow(selectedRow);
    }//GEN-LAST:event_buttonRemoveActionPerformed

    private void txtFieldNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldNumberKeyReleased
        updateSorter();
    }//GEN-LAST:event_txtFieldNumberKeyReleased

    private void chkBoxBalconyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBoxBalconyActionPerformed
        updateSorter();
    }//GEN-LAST:event_chkBoxBalconyActionPerformed

    private void chkBoxAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBoxAvailableActionPerformed
        updateSorter();
    }//GEN-LAST:event_chkBoxAvailableActionPerformed

    private void chkBoxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBoxFilterActionPerformed
        updateSorter();
    }//GEN-LAST:event_chkBoxFilterActionPerformed

    private void roomsTableComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_roomsTableComponentAdded
        lblFilteredRooms.setText(String.valueOf(model.getRowCount()));
    }//GEN-LAST:event_roomsTableComponentAdded

    private void roomsTableComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_roomsTableComponentRemoved
        lblFilteredRooms.setText(String.valueOf(model.getRowCount()));
    }//GEN-LAST:event_roomsTableComponentRemoved

    private void comboBoxBedsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxBedsActionPerformed
        updateSorter();
    }//GEN-LAST:event_comboBoxBedsActionPerformed

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
            java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomWindow().setVisible(true);
            }
        });
    }
    
    private void selectRow(int index) {
        if (roomsTable.getRowCount() > index) {
            roomsTable.setRowSelectionInterval(index, index);
        } else if (roomsTable.getRowCount() > 0){
            selectLastRow();
        }
    }
    
    private void selectFirstRow() {
        selectRow(0);
    }
    
    private void selectLastRow() {
        selectRow(roomsTable.getRowCount() - 1);
    }
    
    private Room getSelectedRoom() {
        return model.getRoomAt(roomsTable.convertRowIndexToModel(getSelectedRow()));
    }
    
    private int getSelectedRow() {
        return roomsTable.getSelectedRow();
    }
    
    private void updateSorter() {
        if (chkBoxFilter.isSelected()) {
            Integer number = !txtFieldNumber.getText().equals("") ? Integer.valueOf(txtFieldNumber.getText()) : null;
            Integer beds = comboBoxBeds.getSelectedIndex() + 1;
            boolean balcony = chkBoxBalcony.isSelected();
            boolean available = chkBoxAvailable.isSelected();

            sorter.setRowFilter(new TableRoomsFilter(number, beds, balcony, available));
            lblFilteredRooms.setText(String.valueOf(roomsTable.getRowCount()) + " filtered rooms");
            selectFirstRow();
        } else {
            sorter.setRowFilter(null);
            lblFilteredRooms.setText(String.valueOf(roomsTable.getRowCount()) + " filtered rooms");
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEdit;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.JButton buttonManage;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JCheckBox chkBoxAvailable;
    private javax.swing.JCheckBox chkBoxBalcony;
    private javax.swing.JCheckBox chkBoxFilter;
    private javax.swing.JComboBox comboBoxBeds;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFilteredRooms;
    private javax.swing.JTable roomsTable;
    private javax.swing.JTextField txtFieldNumber;
    // End of variables declaration//GEN-END:variables
}
