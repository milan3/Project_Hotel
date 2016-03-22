package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class HotelManagerImpl implements HotelManager {

    private final GuestManager guestManager;
    private final RoomManager roomManager;

    public HotelManagerImpl(GuestManager guestManager, RoomManager roomManager) {
        this.guestManager = guestManager;
        this.roomManager = roomManager;
    }

    @Override
    public List<Guest> findGuests(Room room) {
        return null;
    }

    @Override
    public List<Room> getRooms(int floor) {
        return null;
    }

    @Override
    public Accommodation accommodateGuest(Room room, Guest guest) {
        return null;
    }

    @Override
    public void cancelAccommodation(Accommodation accommodation) {

    }

    @Override
    public List<Room> getAvailableRooms() {
        return null;
    }

    public boolean isAvailable(Room room) { return false; }
}
