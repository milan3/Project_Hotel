/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class RoomsTableModel extends AbstractTableModel{
    private List<Room> rooms = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return rooms.size();
    }
    
    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room room = rooms.get(rowIndex);
        HotelManager hm = HotelManagerImpl.getInstance();
        
        switch (columnIndex) {
            case 0:
                return room.getNumber();
            case 1:
                return hm.findGuests(room).size() + "/" + room.getNumberOfBeds();
            case 2:
                return room.getPrice();
            case 3:
                return room.hasBalcony();
            case 4: 
                return hm.isAvailable(room) ? "Available" : "Unavailable";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Number";
            case 1:
                return "Beds";
            case 2:
                return "Price";
            case 3:
                return "Balcony";
            case 4:
                return "Status";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return Boolean.class;
            case 4:
                return String.class;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
        int lastRow = rooms.size() - 1;
        fireTableRowsInserted(lastRow, lastRow);
    }
    
    public void addAll(Collection<Room> rooms) {
        for (Room room : rooms) {
            addRoom(room);
        }
    }
    
    public Room getRoomAt(int rowIndex) {
        return rooms.get(rowIndex);
    }
    
    public void setRoomAt(int rowIndex, Room room) {
        rooms.set(rowIndex, room);
        
        for (int i = 0; i < getColumnCount(); i++)
            fireTableCellUpdated(rowIndex, i);
    }
    
    public void deleteRoom(int rowIndex, Room room) {
        rooms.remove(room);
        
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
