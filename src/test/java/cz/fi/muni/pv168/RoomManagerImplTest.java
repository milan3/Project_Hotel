package cz.fi.muni.pv168;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImplTest {

    private RoomManagerImpl manager;

    @Before
    public void setUp() {
        manager = new RoomManagerImpl();
    }

    @Test
    public void createRoom() {
        Room room = new Room(4, 2, true, new BigDecimal(12.5));
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

        Room room = new Room(4, 2, true, new BigDecimal(12.5));
        room.setId(1L);
        try {
            manager.createRoom(room);
            fail("user shouldn't be allowed to set id");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        room = new Room(-1, 2, true, new BigDecimal(12.5));
        try {
            manager.createRoom(room);
            fail("number of beds must be positive number");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        room = new Room(4, 2, true, new BigDecimal(-12.5));
        try {
            manager.createRoom(room);
            fail("price must be positive number");
        } catch (IllegalArgumentException ex) {
            //OK
        }

    }


    @Test
    public void deleteRoom() {

        Room room1 = new Room(4, 2, true, new BigDecimal(12.5));
        Room room2 = new Room(1, 5, false, new BigDecimal(8.5));
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

        //assertTrue(manager.getAllRooms().isEmpty());

        Room room1 = new Room(4, 2, true, new BigDecimal(12.5));
        Room room2 = new Room(1, 5, false, new BigDecimal(8.5));

        manager.createRoom(room1);
        manager.createRoom(room2);
        List<Room> actual = new ArrayList<Room>();
        List<Room> expected = Arrays.asList(room1, room2);
        actual =  manager.getAllRooms();

        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals("saved and retrieved graves differ", expected, actual);
    }


    private static Comparator<Room> idComparator = new Comparator<Room>() {
        public int compare(Room o1, Room o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };
}