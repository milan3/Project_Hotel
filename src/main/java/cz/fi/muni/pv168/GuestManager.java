package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface GuestManager {
    public void createGuest(Guest guest);
    public void updateGuest(Guest guest);
    public void deleteGuest(Guest guest);
    public Guest getGuest(Long id);
    public List<Guest> getAllGuests();
}
