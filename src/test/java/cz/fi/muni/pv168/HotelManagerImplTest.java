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
    private HotelManagerImpl hotelManager;
    private GuestManager guestManager;
    private RoomManager roomManager;

    @Before
    public void setUp() {
        hotelManager = new HotelManagerImpl();
        guestManager = new GuestManagerImpl();
        roomManager = new RoomManagerImpl();
    }

    @Test
    public void accommodateGuest() {
        Guest guest = new Guest("john");
        guestManager.createGuest(guest);
        Room room = new Room(3, 2, true, new BigDecimal(15));
        hotelManager.accommodateGuest(room, guest);

        List<Guest> result = new ArrayList<Guest>(hotelManager.findGuests(room));
        assertTrue("saved accomodation wasn't found", result.contains(guest));
    }


}