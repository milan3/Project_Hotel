package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface HotelManager {
    public List<Guest> findGuests(Room room);
    public List<Room> getRooms(int floor);
    public Accommodation accommodateGuest(Room room, Guest guest);
    public void cancelAccommodation(Accommodation accommodation);
    public List<Room> getAvailableRooms();
}
