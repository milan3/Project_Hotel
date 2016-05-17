package cz.fi.muni.pv168;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImplTest {
    private EmbeddedDatabase db;
    private RoomManager manager;

    @Before
    public void setUp() throws  SQLException{
        db = new EmbeddedDatabaseBuilder().setType(DERBY).addScript("schema.sql").build();
        manager = RoomManagerImpl.getInstance(db);
    }

    @After
    public void tearDown() throws SQLException {
        db.shutdown();
    }

    @Test
    public void createRoom() {
        Room room = newRoom(104, 2, true, new BigDecimal(12.5));
        manager.createRoom(room);

        Long id = room.getId();

        assertThat("saved room has null id", id, is(not(equalTo(null))));

        Room result = manager.getRoom(id);
        assertThat("saved room wasn't found", result, is(equalTo(room)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNull() throws Exception {
        manager.createRoom(null);
    }

    @Test
    public void createWithWrongValues() {

        Room room = newRoom(104, 2, true, new BigDecimal(12.5));
        room.setId(1L);
        try {
            manager.createRoom(room);
            fail("user shouldn't be allowed to set id");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        room = newRoom(99, 2, true, new BigDecimal(12.5));
        try {
            manager.createRoom(room);
            fail("number of room must be more than 99");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        room = newRoom(104, -2, true, new BigDecimal(-12.5));
        try {
            manager.createRoom(room);
            fail("number of beds must be positive number");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        room = newRoom(104, 2, true, new BigDecimal(-12.5));
        try {
            manager.createRoom(room);
            fail("price must be positive number");
        } catch (IllegalArgumentException ex) {
            //OK
        }

    }


    @Test
    public void deleteRoom() {

        Room room1 = newRoom(104, 3, true, new BigDecimal(12.5));
        Room room2 = newRoom(101, 5, false, new BigDecimal(8.5));
        manager.createRoom(room1);
        manager.createRoom(room2);

        assertNotNull(manager.getRoom(room1.getId()));
        assertNotNull(manager.getRoom(room2.getId()));

        manager.deleteRoom(room1);

        assertNull(manager.getRoom(room1.getId()));
        assertNotNull(manager.getRoom(room2.getId()));

    }


    @Test(expected = IllegalArgumentException.class)
    public void deleteRoomWithNull() throws Exception {
        manager.deleteRoom(null);
    }


    @Test
    public void getAllRooms() {
        assertTrue(manager.getAllRooms().isEmpty());

        Room room1 = newRoom(104, 2, true, new BigDecimal(12.5));
        Room room2 = newRoom(101, 5, false, new BigDecimal(8.5));

        manager.createRoom(room1);
        manager.createRoom(room2);
        List<Room> actual;
        List<Room> expected = Arrays.asList(room1, room2);
        actual =  manager.getAllRooms();

        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals("saved and retrieved graves differ", expected, actual);
    }

    @Test
    public void testUpdateRoom() throws Exception {
        Room room = newRoom(100, 2, false, BigDecimal.valueOf(111));
        Long id;

        manager.createRoom(room);
        room.setBalcony(true);
        id = room.getId();

        manager.updateRoom(room);
        room = manager.getRoom(id);

        assertThat("Room should have balcony", room.hasBalcony(), is(equalTo(true)));
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

    private static Comparator<Room> idComparator = (o1, o2) -> o1.getId().compareTo(o2.getId());
}