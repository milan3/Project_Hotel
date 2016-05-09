package cz.fi.muni.pv168;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface HotelManager {
    List<Accommodation> findAccommodations(Room room);
    List<Guest> findGuests(Room room);
    List<Room> getRooms(int floor);
    Accommodation accommodateGuest(Room room, Guest guest, LocalDate arrival, LocalDate departure);
    void cancelAccommodation(Guest guest);
    List<Room> getAvailableRooms();
    boolean isAvailable(Room room);
    void updateAccommodation(Accommodation accommodation);
}
