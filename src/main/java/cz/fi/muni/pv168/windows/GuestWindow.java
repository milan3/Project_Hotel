/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168.windows;


import cz.fi.muni.pv168.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.util.ResourceBundle;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class GuestWindow extends javax.swing.JFrame {
    private final GuestManager gm;
    private final HotelManager hm;
    private final GuestsTableModel model;
    private TableRowSorter<GuestsTableModel> sorter;
    private static final ResourceBundle rs = StaticBundle.getInstance();
    /**
     * Creates new form GuestWindow
     */
    public GuestWindow() {
        initComponents();
        model = (GuestsTableModel) guestsTable.getModel();
        gm = GuestManagerImpl.getInstance();
        hm = HotelManagerImpl.getInstance();

        guestsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (guestsTable.getRowCount() > 0) {
                    buttonEdit.setEnabled(true);
                    buttonRemove.setEnabled(true);
                    buttonChangeAcc.setEnabled(true);
                } else {
                    buttonEdit.setEnabled(false);
                    buttonRemove.setEnabled(false);
                    buttonChangeAcc.setEnabled(false);
                }
            }
        });
        sorter = new TableRowSorter<GuestsTableModel>(model);
        guestsTable.setRowSorter(sorter);

        new SwingWorker<Void,Void>(){
            @Override
            public Void doInBackground() throws Exception {
                GuestManager gm = GuestManagerImpl.getInstance();
                model.addAll(gm.getAllGuests());
                filteredGuestsLabel.setText(String.valueOf(rs.getString("filtered_guests") + ": " + guestsTable.getRowCount()));
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        filterLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        filteredGuestsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        guestsTable = new javax.swing.JTable();
        buttonChangeAcc = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        roomLabel = new javax.swing.JLabel();
        roomTextField = new javax.swing.JTextField();
        buttonRemove = new javax.swing.JButton();
        butonAdd = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        filterLabel.setText("Filter");
        filterLabel.setToolTipText("");

        filteredGuestsLabel.setText(rs.getString("0_guests_filtered"));

        guestsTable.setModel(new GuestsTableModel());
        jScrollPane1.setViewportView(guestsTable);

        buttonChangeAcc.setText(rs.getString("change_accommodation"));
        buttonChangeAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeAccActionPerformed(evt);
            }
        });

        nameLabel.setText(rs.getString("name"));

        roomLabel.setText(rs.getString("room"));
        roomLabel.setToolTipText("");

        buttonRemove.setText(rs.getString("remove"));
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });

        butonAdd.setText(rs.getString("add"));
        butonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonAddActionPerformed(evt);
            }
        });

        buttonEdit.setText(rs.getString("edit"));
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        jMenu1.setText(rs.getString("file"));

        jMenuItem1.setText(rs.getString("exit"));
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(rs.getString("guests"));

        jMenuItem2.setText(rs.getString("manage_guests"));
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText(rs.getString("rooms"));

        jMenuItem3.setText(rs.getString("manage_rooms"));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameTextFieldKeyReleased(evt);
            }
        });

        roomTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                roomTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(filterLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(nameLabel)
                                                                        .addComponent(roomLabel))
                                                                .addGap(39, 39, 39)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(roomTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(filteredGuestsLabel)
                                                                        .addGap(202, 202, 202))
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(buttonChangeAcc)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonRemove)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(butonAdd)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonEdit)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(filterLabel)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(roomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(roomLabel))
                                .addGap(36, 36, 36)
                                .addComponent(filteredGuestsLabel)
                                .addGap(5, 5, 5)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonChangeAcc)
                                        .addComponent(buttonRemove)
                                        .addComponent(butonAdd)
                                        .addComponent(buttonEdit))
                                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void buttonChangeAccActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog w = new GuestAccommodationDialog(this, true, hm.getAccommodationByGuest(getSelectedGuest()), getSelectedGuest());
        w.setVisible(true);
        this.setVisible(false);
        updateSorter();
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame w = new RoomWindow();
        w.setVisible(true);
        this.setVisible(false);
    }

    private void butonAddActionPerformed(java.awt.event.ActionEvent evt) {
        final GuestDialog w = new GuestDialog(this, true);
        Guest guest = new Guest();
        w.setGuest(guest);
        w.setVisible(true);

        new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                if (guest.getId() != null) {
                    model.addGuest(gm.getGuest(guest.getId()));
                    selectLastRow();
                }
                updateSorter();
                return null;
            }
        }.execute();
        
    }

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {
        final GuestDialog w = new GuestDialog(this, true);
        int selectedRow = getSelectedRow();
        Guest guest = getSelectedGuest();
        w.setGuest(guest);
        w.setVisible(true);

        new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                model.setGuestAt(selectedRow, gm.getGuest(guest.getId()));
                updateSorter();
                return null;
            }
        }.execute();
        
    }

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        int selectedRow = getSelectedRow();
        Guest guest = getSelectedGuest();
        model.deleteGuest(selectedRow, guest);

        new SwingWorker<Void, Void>(){
            @Override
            public Void doInBackground() throws Exception {
                gm.deleteGuest(guest);
                Accommodation accommodation = hm.getAccommodationByGuest(guest);
                if (accommodation != null) {
                    hm.cancelAccommodation(guest);
                }
                updateSorter();
                return null;
            }
        }.execute();

        selectRow(selectedRow);
        
    }

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
            java.util.logging.Logger.getLogger(GuestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuestWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestWindow().setVisible(true);
            }
        });
    }

    private void nameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {                                           
        updateSorter();
    }

    private void roomTextFieldKeyReleased(java.awt.event.KeyEvent evt) {                                           
        updateSorter();
    }

    private void selectRow(int index) {
        if (guestsTable.getRowCount() > index) {
            guestsTable.setRowSelectionInterval(index, index);
        } else if (guestsTable.getRowCount() > 0){
            selectLastRow();
        }
    }

    private void selectFirstRow() {
        selectRow(0);
    }

    private void selectLastRow() {
        selectRow(guestsTable.getRowCount() - 1);
    }

    private Guest getSelectedGuest() {
        return model.getGuestAt(guestsTable.convertRowIndexToModel(getSelectedRow()));
    }

    private int getSelectedRow() {
        return guestsTable.getSelectedRow();
    }

    private void updateSorter() {
        String name = null;
        String room = null;
        try {
            name = nameTextField.getText();
        } catch (NullPointerException e) {}     ///if text field is empty do nothing

        try {
            room = roomTextField.getText();
        } catch (NullPointerException e) {}     ///if text field is empty do nothing

        sorter.setRowFilter(new TableGuestsFilter(name, room));

        filteredGuestsLabel.setText(String.valueOf(rs.getString("filtered_guests") + ": " +guestsTable.getRowCount()));
        selectFirstRow();

    }




    // Variables declaration - do not modify
    private javax.swing.JButton butonAdd;
    private javax.swing.JButton buttonChangeAcc;
    private javax.swing.JButton buttonEdit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JLabel filteredGuestsLabel;
    private javax.swing.JTable guestsTable;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel roomLabel;
    private javax.swing.JTextField roomTextField;
    // End of variables declaration
}