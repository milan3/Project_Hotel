package cz.fi.muni.pv168;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;

/**
 * Created by Milan on 15.03.2016.
 */

public class GuestManagerImplTest {
    private EmbeddedDatabase db;
    private GuestManager manager;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder().setType(DERBY).addScript("schema.sql").build();
        manager = new GuestManagerImpl(db);
    }
    
    @After
    public void tearDown() throws SQLException {
        db.shutdown();
    }

    @Test
    public void createGuest() {
        Guest guest = new Guest("John");
        manager.createGuest(guest);

        Long id = guest.getId();

        assertThat("saved guest has null id", id, is(not(equalTo(null))));

        Guest result = manager.getGuest(id);
        assertThat("saved guest wasn't found", result, is(equalTo(guest)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNull() throws Exception {
        manager.createGuest(null);
    }

    @Test
    public void deleteGuest() {

        Guest guest1 = new Guest("John");
        Guest guest2 = new Guest("Peter");
        manager.createGuest(guest1);
        manager.createGuest(guest2);

        assertNotNull(manager.getGuest(guest1.getId()));
        assertNotNull(manager.getGuest(guest2.getId()));

        manager.deleteGuest(guest1);

        assertNull(manager.getGuest(guest1.getId()));
        assertNotNull(manager.getGuest(guest2.getId()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteGuestWithNull() throws Exception {
        manager.deleteGuest(null);
    }

    @Test
    public void getAllGuests() {

        Guest guest1 = new Guest("John");
        Guest guest2 = new Guest("Peter");

        manager.createGuest(guest1);
        manager.createGuest(guest2);

        List<Guest> expected = Arrays.asList(guest1, guest2);
        List<Guest> actual = new ArrayList<>(manager.getAllGuests());

        Collections.sort(actual, idComparator);
        Collections.sort(expected, idComparator);

        assertEquals("saved and retrieved guests differ", expected, actual);
    }

    @Test
    public void updateGuest() {
        Guest guest = new Guest("John");
        manager.createGuest(guest);
        Long guestId = guest.getId();

        guest = manager.getGuest(guestId);
        guest.setFullName("Peter");
        manager.updateGuest(guest);
        guest = manager.getGuest(guestId);
        assertThat("name was not changed", guest.getFullName(), is(equalTo("Peter")));
    }

    @Test
    public void updateGuestWithWrongAttributes() {

        Guest guest = new Guest("John");
        manager.createGuest(guest);
        Long guestId = guest.getId();

        try {
            manager.updateGuest(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            guest = manager.getGuest(guestId);
            guest.setId(null);
            manager.updateGuest(guest);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    public static Guest newGuest(String fullName)
    {
        Guest guest = new Guest();
        guest.setFullName(fullName);
        return guest;
    }

    private static Comparator<Guest> idComparator = (o1, o2) -> o1.getId().compareTo(o2.getId());
}