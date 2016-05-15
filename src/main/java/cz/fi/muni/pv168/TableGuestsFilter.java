package cz.fi.muni.pv168;

import javax.swing.*;

/**
 * Created by Milan on 13.05.2016.
 */
public class TableGuestsFilter extends RowFilter<Object, Object> {
    private final String name;
    private final String room;

    public TableGuestsFilter(String name, String room) {
        this.name = name.equals("") ? null : name;
        this.room = room.equals("") ? null : room;
    }

    @Override
    public boolean include(Entry<? extends Object, ? extends Object> entry) {
        String name = (String)entry.getValue(0);
        String room = (String)entry.getValue(1);

        if (this.name != null && this.room != null) {
            return name.startsWith(this.name) && room.startsWith(this.room);
        }
        if (this.name != null) {
            return name.startsWith(this.name);
        } else if (this.room != null){
            return room.startsWith(this.room);
        } else {
            return true;
        }
    }
}
