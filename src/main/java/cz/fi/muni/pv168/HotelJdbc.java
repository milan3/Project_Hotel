/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pv168;

import java.sql.SQLException;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Radoslav Karlik (422358)
 */
public class HotelJdbc {
    private static JdbcTemplate jdbc;
    
    private static final HotelJdbc hotelJdbc = new HotelJdbc();
    
    private static final Logger log = LoggerFactory.getLogger(HotelJdbc.class);
    
    private HotelJdbc() {  
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("hotelDB");
        
        try {
            if (ds.getConnection() == null) {   
                ds.setCreateDatabase("create");
            }
        } catch (SQLException ex) {
            log.error("HotelJdbc()");
        }

        jdbc = new JdbcTemplate(ds);
    }
    
    public static JdbcTemplate getJdbc() {
        return jdbc;
    }
    
    public static void destroy() {
        try {
            jdbc.execute("DROP TABLE GUEST");
            jdbc.execute("DROP TABLE ACCOMMODATION");
            jdbc.execute("DROP TABLE ROOM");
        } catch(DataAccessException e) {
            log.error("destroy()");
        }
    }
    
    public static void init() {
        try {
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
        } catch(DataAccessException e) {
            log.error("init()");
        }
    }
}
