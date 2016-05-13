package cz.fi.muni.pv168;

/**
 * Created by Milan on 13.05.2016.
 */

import cz.fi.muni.pv168.windows.StaticBundle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;


public class GuestsTableModel  extends AbstractTableModel{
    private List<Guest> guests = new ArrayList<>();
    private ResourceBundle rs = StaticBundle.getInstance();


    @Override
    public int getRowCount() {
        return guests.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Guest guest = guests.get(rowIndex);
        HotelManager hm = HotelManagerImpl.getInstance();
        Accommodation accommodation = hm.getAccommodationByGuest(guest);
        switch (columnIndex) {
            case 0:
                return guest.getFullName();
            case 1:
                return accommodation == null ? "-" : accommodation.getRoom().toString();
            case 2:
                return accommodation == null ? "-" : accommodation.getArrival().toString();
            case 3:
                return accommodation == null ? "-" : accommodation.getDeparture().toString();
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
                return rs.getString("room");
            case 2:
                return rs.getString("arrival");
            case 3:
                return rs.getString("departure");
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
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
                return false;
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
        int lastRow = guests.size() - 1;
        fireTableRowsInserted(lastRow, lastRow);
    }

    public void addAll(Collection<Guest> guests) {
        for (Guest guest : guests) {
            addGuest(guest);
        }
    }

    public Guest getGuestAt(int rowIndex) {
        return guests.get(rowIndex);
    }

    public void setGuestAt(int rowIndex, Guest guest) {
        guests.set(rowIndex, guest);

        for (int i = 0; i < getColumnCount(); i++)
            fireTableCellUpdated(rowIndex, i);
    }

    public void deleteGuest(int rowIndex, Guest guest) {
        guests.remove(guest);

        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}