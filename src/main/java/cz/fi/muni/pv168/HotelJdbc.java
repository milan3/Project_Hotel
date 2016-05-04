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
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class HotelJdbc {
    private static JdbcTemplate jdbc = null;
    
    public static JdbcTemplate getInstance() {
        if (jdbc == null) {
            init();
        }
        
        return jdbc;
    }
    
    public static void destroy() throws SQLException {
        jdbc.execute("DROP TABLE GUEST");
        jdbc.execute("DROP TABLE ACCOMMODATION");
        jdbc.execute("DROP TABLE ROOM");
        
        jdbc = null;
    }
    
    public static void init() {
        if (jdbc == null) {
            EmbeddedDataSource ds = new EmbeddedDataSource();
            ds.setDatabaseName("hotelDB");
            ds.setCreateDatabase("create");

            jdbc = new JdbcTemplate(ds);

            jdbc.execute("CREATE TABLE GUEST ("
                        + "id bigint primary key generated always as identity,"
                        + "fullName VARCHAR(255))");

            jdbc.execute("CREATE TABLE ROOM ("
                        + "id bigint primary key generated always as identity,"
                        + "number integer,"
                        + "numberOfBeds integer,"
                        + "balcony boolean,"
                        + "price decimal)");

            jdbc.execute("CREATE TABLE ACCOMMODATION ("
                        + "id bigint primary key generated always as identity,"
                        + "arrival timestamp,"
                        + "departure timestamp,"
                        + "room bigint,"
                        + "guest bigint)");
            }
    }
}
