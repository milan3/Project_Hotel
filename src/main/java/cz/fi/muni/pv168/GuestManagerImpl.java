package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {

    public void createGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
    }

    public void updateGuest(Guest guest) {

    }

    public void deleteGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
    }

    public Guest getGuest(Long id) {
        return null;
    }

    public List<Guest> getAllGuests() {
        return null;
    }
}
