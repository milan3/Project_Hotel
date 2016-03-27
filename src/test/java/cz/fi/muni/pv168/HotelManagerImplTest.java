package cz.fi.muni.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Milan on 15.03.2016.
 */
public class HotelManagerImplTest {
    private HotelManager hotelManager;
    private GuestManager guestManager;
    private RoomManager roomManager;
    private DataSource dataSource;

    @Before
    public void setUp() throws  SQLException{
        dataSource = prepareDataSource();
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
        }

        guestManager = new GuestManagerImpl(dataSource);
        roomManager = new RoomManagerImpl(dataSource);
        hotelManager = new HotelManagerImpl(dataSource, guestManager, roomManager);
    }

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:roommanager-test");
        ds.setCreateDatabase("create");
        return ds;
    }

    @Test
    public void accommodateGuest() {
        Guest guest = new Guest("john");
        guestManager.createGuest(guest);
        Room room = newRoom(3, 2, true, new BigDecimal(15));
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest);

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accomomdation wasn't found", result.contains(guest));
    }

    @Test
    public void testIsAvailable() throws Exception {
        //-----------------number,beds,...
        Room room1 = newRoom(100, 1, false, BigDecimal.valueOf(100));
        Room room2 = newRoom(101, 3, false, BigDecimal.valueOf(100));
        roomManager.createRoom(room1);
        roomManager.createRoom(room2);

        Guest guest1 = new Guest("Jozo");
        Guest guest2 = new Guest("Stefan");
        Guest guest3 = new Guest("Meno");
        guestManager.createGuest(guest1);
        guestManager.createGuest(guest2);
        guestManager.createGuest(guest3);

        hotelManager.accommodateGuest(room2, guest1);
        hotelManager.accommodateGuest(room2, guest2);
        hotelManager.accommodateGuest(room1, guest3);

        List<Room> rooms = hotelManager.getAvailableRooms();

        assertThat("Exactly 1 room should be available", rooms.size(), is(equalTo(1)));
        assertFalse("Room shouldnt be available", hotelManager.isAvailable(room1));
        assertTrue("Room should be available", hotelManager.isAvailable(room2));
    }

    @After
    public void tearDown() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DROP TABLE GUEST").executeUpdate();
            connection.prepareStatement("DROP TABLE ACCOMMODATION").executeUpdate();
            connection.prepareStatement("DROP TABLE ROOM").executeUpdate();
        }
    }

    public static Room newRoom(int number, int numberOfBeds, boolean balcony, BigDecimal price)
    {
        Room room = new Room();
        room.setNumber(number);
        room.setNumberOfBeds(numberOfBeds);
        room.setBalcony(balcony);
        room.setPrice(price);

        return room;
    }
}