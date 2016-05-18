/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168.windows;

import cz.fi.muni.pv168.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class GuestAccommodationDialog extends javax.swing.JDialog {

    private Accommodation accommodation;
    private Guest guest = null;
    private Room room = null;
    private static final ResourceBundle rs = StaticBundle.getInstance();
    
    /**
     * Creates new form GuestAccommodationDialog
     */
    public GuestAccommodationDialog(java.awt.Frame parent, boolean modal, Accommodation accommodation) {
        super(parent, modal);
        initComponents();
        
        this.accommodation = accommodation;     
        
        if (parent instanceof RoomWindow) {
            btnGuestChange.setVisible(true);
            btnRoomChange.setVisible(false);
        } else {
            btnGuestChange.setVisible(false);
            btnRoomChange.setVisible(true);
        }
        
        if (this.accommodation != null) {
            room = accommodation.getRoom();
            guest = accommodation.getGuest();
            LocalDate from = accommodation.getArrival();
            LocalDate to = accommodation.getDeparture();
            
            lblRoom.setText(String.valueOf(room.getNumber()));
            lblPrice.setText(String.valueOf(room.getPrice()));
            lblGuest.setText(String.valueOf(guest.getFullName()));
            datePickerFrom.setDate(java.sql.Date.valueOf(from));
            datePickerTo.setDate(java.sql.Date.valueOf(to));
            btnGuestChange.setEnabled(false);
            
            buttonSubmit.setEnabled(true);
        } else {
            lblRoom.setText(room != null ? String.valueOf(room.getNumber()) : rs.getString("set_a_room"));
            if (room != null) {
                lblPrice.setText(room.getPrice().toString());
            }
            lblGuest.setText(guest != null ? guest.getFullName() : rs.getString("set_a_guest"));
        }
        
        datePickerFrom.setOpaque(true);
        datePickerTo.setOpaque(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        buttonSubmit = new javax.swing.JButton();
        datePickerFrom = new org.jdesktop.swingx.JXDatePicker();
        datePickerTo = new org.jdesktop.swingx.JXDatePicker();
        btnGuestChange = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblRoom = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblGuest = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        btnRoomChange = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setText(rs.getString("to"));

        jLabel4.setText(rs.getString("room") + ": ");

        jLabel1.setText(rs.getString("price")+": ");

        buttonSubmit.setText(rs.getString("submit"));
        buttonSubmit.setEnabled(false);
        buttonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSubmitActionPerformed(evt);
            }
        });

        datePickerFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePickerFromActionPerformed(evt);
            }
        });

        datePickerTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePickerToActionPerformed(evt);
            }
        });

        btnGuestChange.setText(rs.getString("change"));
        btnGuestChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuestChangeActionPerformed(evt);
            }
        });

        jLabel2.setText(rs.getString("from"));

        jLabel5.setText(rs.getString("guest")+": ");

        lblError.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 51));

        btnRoomChange.setText(rs.getString("change"));
        btnRoomChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoomChangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(lblPrice))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblError))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(buttonSubmit))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRoom))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGuest)))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuestChange)
                            .addComponent(btnRoomChange))))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(129, 129, 129)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(datePickerFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addComponent(datePickerTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(98, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(btnRoomChange)
                            .addComponent(lblRoom))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblGuest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                        .addComponent(lblPrice))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnGuestChange)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSubmit)
                .addGap(35, 35, 35)
                .addComponent(lblError)
                .addGap(21, 21, 21))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(101, 101, 101)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(datePickerFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(datePickerTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(11, 11, 11)
                    .addComponent(jLabel1)
                    .addContainerGap(133, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuestChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuestChangeActionPerformed
        final GuestListDialog gld = new GuestListDialog((Frame) SwingUtilities.getWindowAncestor(this), true, room);
        gld.setVisible(true);
        guest = gld.getGuest();
        lblGuest.setText(guest != null ? guest.getFullName() : rs.getString("set_a_guest"));
        checkEnabled();
    }//GEN-LAST:event_btnGuestChangeActionPerformed

    private void buttonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSubmitActionPerformed
        HotelManager hm = HotelManagerImpl.getInstance();
        if (getLocalDate(datePickerFrom.getDate()).compareTo(getLocalDate(datePickerTo.getDate())) > 0) {
            lblError.setText(rs.getString("DEPARTURE_AFTER_ARRIVAL"));
            datePickerFrom.setBackground(java.awt.Color.red);
            datePickerTo.setBackground(java.awt.Color.red);
            return;
        }
        
        //tu nemoze byt SwingWorker pretoze by sa nemalo dat vratit z okna, ked este korektne neskoncilo pridanie/update a stale sa caka na zavretie okna
        try {
            if (accommodation == null) {
                accommodation = hm.accommodateGuest(room, guest, getLocalDate(datePickerFrom.getDate()), getLocalDate(datePickerTo.getDate()));
            } else {
                        
                accommodation.setArrival(getLocalDate(datePickerFrom.getDate()));
                accommodation.setDeparture(getLocalDate(datePickerTo.getDate()));
                accommodation.setRoom(room);
                hm.updateAccommodation(accommodation);
             }
         } catch(ServiceFailureException e) {
            switch (e.getMessage()) {
                case HotelManagerImpl.DEPARTURE_AFTER_ARRIVAL:
                    lblError.setText(rs.getString("DEPARTURE_AFTER_ARRIVAL"));
                    datePickerFrom.setBackground(java.awt.Color.red);
                    datePickerTo.setBackground(java.awt.Color.red);
                    return;
                 default:
                    return;
                }
            }
        
        dispose();
    }//GEN-LAST:event_buttonSubmitActionPerformed

    private void datePickerFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datePickerFromActionPerformed
        checkEnabled();
    }//GEN-LAST:event_datePickerFromActionPerformed

    private void datePickerToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datePickerToActionPerformed
        checkEnabled();
    }//GEN-LAST:event_datePickerToActionPerformed

    private void btnRoomChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomChangeActionPerformed
        final RoomListDialog rld = new RoomListDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        rld.setVisible(true);
        room = rld.getRoom();
        lblRoom.setText(room != null ? Integer.toString(room.getNumber()) : rs.getString("set_a_room"));
        if (room != null) {
            lblPrice.setText(room.getPrice().toString());
        }
        checkEnabled();
    }//GEN-LAST:event_btnRoomChangeActionPerformed

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
            java.util.logging.Logger.getLogger(GuestAccommodationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuestAccommodationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuestAccommodationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuestAccommodationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GuestAccommodationDialog dialog = new GuestAccommodationDialog(new javax.swing.JFrame(), true, null);
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
        lblRoom.setText(String.valueOf(room.getNumber()));
        lblPrice.setText(String.valueOf(room.getPrice()));
        lblGuest.setText(rs.getString("set_a_guest"));
    }
    
    public void setGuest(Guest guest) {
        this.guest = guest;
        lblGuest.setText(guest.getFullName());
    }
    
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    private LocalDate getLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
    
    private void checkEnabled() {
        if (guest != null && datePickerFrom.getDate() != null && datePickerTo.getDate() != null) {
            buttonSubmit.setEnabled(true);
        } else {
            buttonSubmit.setEnabled(false);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuestChange;
    private javax.swing.JButton btnRoomChange;
    private javax.swing.JButton buttonSubmit;
    private org.jdesktop.swingx.JXDatePicker datePickerFrom;
    private org.jdesktop.swingx.JXDatePicker datePickerTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblGuest;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblRoom;
    // End of variables declaration//GEN-END:variables
}
