/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class HotelDataSource {
    private static DataSource ds = null;
    
    private static void init() throws SQLException {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("hotelDB");
        dataSource.setCreateDatabase("create");
        
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("CREATE TABLE GUEST ("
                    + "id bigint primary key generated always as identity,"
                    + "fullName VARCHAR(255))").executeUpdate();

            connection.prepareStatement("CREATE TABLE ROOM ("
                    + "id bigint primary key generated always as identity,"
                    + "number integer,"
                    + "numberOfBeds integer,"
                    + "balcony boolean,"
                    + "price decimal)").executeUpdate();

            connection.prepareStatement("CREATE TABLE ACCOMMODATION ("
                    + "id bigint primary key generated always as identity,"
                    + "arrival timestamp,"
                    + "departure timestamp,"
                    + "room bigint,"
                    + "guest bigint)").executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        ds = dataSource;
    }
    
    public static void destroy() throws SQLException {
        if (ds != null) {
            try (Connection connection = ds.getConnection()) {
                connection.prepareStatement("DROP TABLE GUEST").executeUpdate();
                connection.prepareStatement("DROP TABLE ACCOMMODATION").executeUpdate();
                connection.prepareStatement("DROP TABLE ROOM").executeUpdate();
            }

            ds = null;
        }
    }
    
    public static DataSource getInstance() throws SQLException {
        if (ds == null) {
            init();
        }
        
        return ds;
    }
    
    private HotelDataSource() {
        
    }
}
