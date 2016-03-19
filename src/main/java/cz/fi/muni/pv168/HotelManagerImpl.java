package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class HotelManagerImpl implements HotelManager {

    private GuestManager guestManager;
    private RoomManager roomManager;

    public List<Guest> findGuests(Room room) {
        return null;
    }

    public List<Room> getRooms(int floor) {
        return null;
    }

    public Accommodation accommodateGuest(Room room, Guest guest) {
        return null;
    }

    public void cancelAccommodation(Accommodation accommodation) {

    }

    public List<Room> getAvailableRooms() {
        return null;
    }
}
