/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import cz.fi.muni.pv168.windows.StaticBundle;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class AccommodationsTableModel extends AbstractTableModel{
    private final List<Accommodation> accommodations = new ArrayList<>();
    private ResourceBundle rs = StaticBundle.getInstance();
    
    @Override
    public int getRowCount() {
        return accommodations.size();
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Accommodation accommodation = accommodations.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return accommodation.getGuest().getFullName();
            case 1:
                return accommodation.getArrival();
            case 2:
                return accommodation.getDeparture();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rs.getString("name");
            case 1:
                return rs.getString("from");
            case 2:
                return rs.getString("to");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return LocalDate.class;
            case 2:
                return LocalDate.class;
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
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
        int lastRow = accommodations.size() - 1;
        fireTableRowsInserted(lastRow, lastRow);
    }
    
    public void addAll(Collection<Accommodation> accommodations) {
        for (Accommodation accommodation : accommodations) {
            addAccommodation(accommodation);
        }
    }
    
    public void clear() {
        accommodations.clear();
        fireTableDataChanged();
    }
    
    public Accommodation getAccommodationAt(int rowIndex) {
        return accommodations.get(rowIndex);
    }
    
    public void deleteAccommodation(int rowIndex, Accommodation acc) {
        accommodations.remove(acc);
        
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    public void setAccommodationAt(int rowIndex, Accommodation accommodation) {
        accommodations.set(rowIndex, accommodation);
        
        for (int i = 0; i < getColumnCount(); i++)
            fireTableCellUpdated(rowIndex, i);
    }
}
