package cz.fi.muni.pv168;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Milan on 15.03.2016.
 */
public class HotelManagerImplTest {
    private HotelManager hotelManager;
    private GuestManager guestManager;
    private RoomManager roomManager;

    @Before
    public void setUp() {
        hotelManager = new HotelManagerImpl(null, null);
        guestManager = new GuestManagerImpl(null);
        roomManager = new RoomManagerImpl(null);
    }

    @Test
    public void accommodateGuest() {
        Guest guest = new Guest("john");
        guestManager.createGuest(guest);
        Room room = new Room(3, 2, true, new BigDecimal(15));
        hotelManager.accommodateGuest(room, guest);

        List<Guest> result = new ArrayList<>(hotelManager.findGuests(room));
        assertTrue("saved accomodation wasn't found", result.contains(guest));
    }

    @Test
    public void testIsAvailable() throws Exception {
        //-----------------number,beds,...
        Room room1 = newRoom(100, 1, false, BigDecimal.valueOf(100));
        Room room2 = newRoom(101, 3, false, BigDecimal.valueOf(100));

        hotelManager.accommodateGuest(room2, new Guest(""));
        hotelManager.accommodateGuest(room2, new Guest(""));
        hotelManager.accommodateGuest(room1, new Guest(""));

        List<Room> rooms = hotelManager.getAvailableRooms();

        assertFalse("Room shouldnt be available", hotelManager.isAvailable(room1));
        assertTrue("Room should be available", hotelManager.isAvailable(room2));
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