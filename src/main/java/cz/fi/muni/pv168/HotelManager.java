package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface HotelManager {
    List<Guest> findGuests(Room room);
    List<Room> getRooms(int floor);
    Accommodation accommodateGuest(Room room, Guest guest);
    void cancelAccommodation(Accommodation accommodation);
    List<Room> getAvailableRooms();
    boolean isAvailable(Room room);
}
