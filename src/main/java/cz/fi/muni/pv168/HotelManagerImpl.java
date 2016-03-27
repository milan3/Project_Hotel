package cz.fi.muni.pv168;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class HotelManagerImpl implements HotelManager {

    private GuestManager guestManager = null;
    private RoomManager roomManager = null;
    private JdbcTemplate jdbc;

    public HotelManagerImpl(DataSource dataSource, GuestManager guestManager, RoomManager roomManager) {
        this.jdbc = new JdbcTemplate(dataSource);
        this.guestManager = guestManager;
        this.roomManager = roomManager;
    }

    @Override
    public List<Guest> findGuests(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        List<Guest> guests = new ArrayList<>();
        List<Accommodation> accommodations = jdbc.query("SELECT * FROM ACCOMMODATION WHERE room=?", new RowMappers(roomManager, guestManager).accommodationMapper, room.getId());

        for (Accommodation acc : accommodations) {
            guests.add(acc.getGuest());
        }

        return guests;
    }

    @Override
    public List<Room> getRooms(int floor) {
        if (floor < 0) {
            throw new IllegalArgumentException("floor is negative number");
        }

        List<Room> allRooms = roomManager.getAllRooms();
        List<Room> floorRooms = new ArrayList<>();

        for (Room room : allRooms) {
            int firstDigit = Integer.parseInt(Integer.toString(room.getNumber()).substring(0, 1));
            if (firstDigit == floor) {
                floorRooms.add(room);
            }
        }

        return floorRooms;
    }

    @Override
    public void accommodateGuest(Room room, Guest guest) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }

        SimpleJdbcInsert insertAccommodation = new SimpleJdbcInsert(jdbc).withTableName("accommodation").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("arrival", Timestamp.valueOf(LocalDate.now().atStartOfDay()))
                .addValue("departure", Timestamp.valueOf(LocalDate.now().atStartOfDay()))
                .addValue("room", room.getId())
                .addValue("guest", guest.getId());

        insertAccommodation.execute(parameters);
    }

    @Override
    public void cancelAccommodation(Accommodation accommodation) {
        if (accommodation == null) {
            throw new IllegalArgumentException("accommodation is null");
        }

        try {
            jdbc.update("DELETE FROM ACCOMMODATION WHERE ID = ? ", accommodation.getId());
        } catch(EmptyResultDataAccessException ex) {
            throw new ServiceFailureException("Accommodation does not exist");
        }
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> allRooms = roomManager.getAllRooms();
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            if (isAvailable(room)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public boolean isAvailable(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        List<Guest> guests = findGuests(room);

        return guests.size() < room.getNumberOfBeds();
    }
}
