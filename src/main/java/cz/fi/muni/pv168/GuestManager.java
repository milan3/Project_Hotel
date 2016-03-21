package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface GuestManager {
    void createGuest(Guest guest);
    void updateGuest(Guest guest);
    void deleteGuest(Guest guest);
    Guest getGuest(Long id);
    List<Guest> getAllGuests();
}
