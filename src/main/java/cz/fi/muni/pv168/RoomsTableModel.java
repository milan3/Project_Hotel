/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import java.util.ArrayList;
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
                return hm.isAvailable(room);
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
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
}
