package cz.fi.muni.pv168;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {
    private JdbcTemplate jdbc;

    @Override
    public void createGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
    }

    @Override
    public void updateGuest(Guest guest) {

    }

    @Override
    public void deleteGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
    }

    @Override
    public Guest getGuest(Long id) {
        return null;
    }

    @Override
    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();



        return guests;
    }
}
