/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import cz.fi.muni.pv168.windows.StaticBundle;

import javax.swing.RowFilter;
import java.util.ResourceBundle;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class TableRoomsFilter extends RowFilter<Object, Object>{
    private Integer number;
    private Integer beds;
    private boolean balcony;
    private boolean available;
    private ResourceBundle rs = StaticBundle.getInstance();

    public TableRoomsFilter(Integer number, Integer beds, boolean balcony, boolean available) {
        this.number = number;
        this.beds = beds;
        this.balcony = balcony;
        this.available = available;
    }
    
    @Override
    public boolean include(Entry<? extends Object, ? extends Object> entry) {
        Integer number = (Integer) entry.getValue(0);
        String bedsStr = (String)entry.getValue(1);
        Integer bedsInt = Integer.valueOf(bedsStr.substring(bedsStr.indexOf("/") + 1));
        Boolean balcony = (Boolean) entry.getValue(3);
        Boolean available = ((String) entry.getValue(4)).equals(rs.getString("available")) ? true : false;
               
        if (this.number != null) {
            return number.equals(this.number) && bedsInt.equals(this.beds) && balcony == this.balcony && available == this.available;
        } else {
            return bedsInt.equals(this.beds) && balcony == this.balcony && available == this.available;
        }
    }
    
}
