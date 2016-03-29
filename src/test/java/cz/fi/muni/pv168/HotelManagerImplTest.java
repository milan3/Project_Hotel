package cz.fi.muni.pv168;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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

    @After
    public void tearDown() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DROP TABLE GUEST").executeUpdate();
            connection.prepareStatement("DROP TABLE ACCOMMODATION").executeUpdate();
            connection.prepareStatement("DROP TABLE ROOM").executeUpdate();
        }
    }

    @Test
    public void accommodateGuest() {
        Guest guest = newGuest("john");
        guestManager.createGuest(guest);
        Room room = newRoom(3, 2, true, new BigDecimal(15));
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accommodation wasn't found", result.contains(guest));
    }

    @Test(expected = ServiceFailureException.class)
    public void accommodateMoreGuestsThanCapacity() {
        Guest guest1 = newGuest("john");
        guestManager.createGuest(guest1);
        Guest guest2 = newGuest("jack");
        guestManager.createGuest(guest2);
        Room room = newRoom(3, 1, true, new BigDecimal(15));  // room with only one bed
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest1, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accommodation wasn't found", result.contains(guest1));
        assertTrue("1 guest should be found", result.size() == 1);
        hotelManager.accommodateGuest(room, guest2, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));  //should throw ServiceFailureException
    }

    @Test(expected = ServiceFailureException.class)
    public void accommodateGuestWrongDates() {
        Guest guest = newGuest("john");
        guestManager.createGuest(guest);
        Room room = newRoom(3, 2, true, new BigDecimal(15));
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 7, 18));
    }

    @Test
    public void findGuests() {
        Guest guest1 = newGuest("john");
        guestManager.createGuest(guest1);
        Guest guest2 = newGuest("jack");
        guestManager.createGuest(guest2);
        Room room = newRoom(3, 2, true, new BigDecimal(15));
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest1, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));
        hotelManager.accommodateGuest(room, guest2, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accommodations weren't found", result.contains(guest1) && result.contains(guest2));
        assertTrue("2 guests should be found", result.size() == 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findGuestsWithNull() throws Exception {
        hotelManager.findGuests(null);
    }

    @Test
    public void getRooms() {
        Room room1 = newRoom(100, 1, false, BigDecimal.valueOf(100));
        Room room2 = newRoom(101, 3, false, BigDecimal.valueOf(100));
        Room room3 = newRoom(201, 3, false, BigDecimal.valueOf(100));
        roomManager.createRoom(room1);
        roomManager.createRoom(room2);
        roomManager.createRoom(room3);
        List<Room> rooms = hotelManager.getRooms(1);
        assertThat("Exactly 2 rooms should be found", rooms.size(), is(equalTo(2)));
        assertTrue("Wrong rooms found", rooms.contains(room1) && rooms.contains(room2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRoomsWrongParam() {
        hotelManager.getRooms(-1);
    }


    @Test
    public void testIsAvailable() throws Exception {
        //-----------------number,beds,...
        Room room1 = newRoom(100, 1, false, BigDecimal.valueOf(100));
        Room room2 = newRoom(101, 3, false, BigDecimal.valueOf(100));
        roomManager.createRoom(room1);
        roomManager.createRoom(room2);

        Guest guest1 = newGuest("John");
        Guest guest2 = newGuest("Steve");
        Guest guest3 = newGuest("Jack");
        guestManager.createGuest(guest1);
        guestManager.createGuest(guest2);
        guestManager.createGuest(guest3);

        hotelManager.accommodateGuest(room2, guest1, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));
        hotelManager.accommodateGuest(room2, guest2, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));
        hotelManager.accommodateGuest(room1, guest3, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));

        List<Room> rooms = hotelManager.getAvailableRooms();

        assertThat("Exactly 1 room should be available", rooms.size(), is(equalTo(1)));
        assertFalse("Room shouldn't be available", hotelManager.isAvailable(room1));
        assertTrue("Room should be available", hotelManager.isAvailable(room2));
    }


    @Test
    public void cancelAccommodation() {
        Guest guest1 = newGuest("john");
        guestManager.createGuest(guest1);
        Guest guest2 = newGuest("jack");
        guestManager.createGuest(guest2);
        Room room = newRoom(3, 2, true, new BigDecimal(15));
        roomManager.createRoom(room);
        hotelManager.accommodateGuest(room, guest1, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));
        hotelManager.accommodateGuest(room, guest2, LocalDate.of(2016, 7, 20), LocalDate.of(2016, 8, 20));

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accommodations weren't found", result.contains(guest1) && result.contains(guest2));
        assertThat("2 guests should be found", result.size(), is(equalTo(2)));
        hotelManager.cancelAccommodation(guest1);

        result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accommodation wasn't found", result.contains(guest2));
        assertThat("1 guest should be found", result.size(), is(equalTo(1)));

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

    public static Guest newGuest(String fullName) {
        Guest guest = new Guest();
        guest.setFullName(fullName);
        return guest;
    }
}