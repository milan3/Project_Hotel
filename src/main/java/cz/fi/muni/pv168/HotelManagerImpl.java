package cz.fi.muni.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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

    private GuestManager guestManager;
    private RoomManager roomManager;
    private final JdbcTemplate jdbc;
    
    public static final Logger log = LoggerFactory.getLogger(HotelManagerImpl.class);
    
    private static HotelManager instance = null;
    
    public static HotelManager getInstance() { 
        return instance;
    }
    
    public static void init(DataSource dataSource, GuestManager guestManager, RoomManager roomManager) {
        instance = new HotelManagerImpl(dataSource, guestManager, roomManager);
    }
    
    public HotelManagerImpl(DataSource dataSource, GuestManager guestManager, RoomManager roomManager) {
        this.guestManager = null;
        this.roomManager = null;
        this.jdbc = new JdbcTemplate(dataSource);
        this.guestManager = guestManager;
        this.roomManager = roomManager;
    }

    @Override
    public List<Guest> findGuests(Room room) {
        logDebug("finding guests in room: " + room);

        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        List<Guest> guests = new ArrayList<>();
        
        try {
            List<Accommodation> accommodations = jdbc.query("SELECT * FROM ACCOMMODATION WHERE room=?", new RowMappers(roomManager, guestManager).accommodationMapper, room.getId());
            
            for (Accommodation acc : accommodations) {
                guests.add(acc.getGuest());
            }
        } catch (DataAccessException e) {
            logDebug("finding all guests from room " + room + " failed");
        }
        
        logDebug("returned all guests from room " + room);
        return guests;
    }

    @Override
    public List<Room> getRooms(int floor) {
        logDebug("finding all rooms on floor: " + floor);

        if (floor < 1) {
            throw new IllegalArgumentException("floor is not positive number");
        }

        List<Room> allRooms = roomManager.getAllRooms();
        List<Room> floorRooms = new ArrayList<>();

        for (Room room : allRooms) {
            int firstDigit = Integer.parseInt(Integer.toString(room.getNumber()).substring(0, 1));
            if (firstDigit == floor) {
                floorRooms.add(room);
            }
        }

        logDebug("returned all rooms from floor " + floor);
        return floorRooms;
    }

    @Override
    public void accommodateGuest(Room room, Guest guest, LocalDate arrival, LocalDate departure) {
        logDebug("accomodating guest " + guest + " to room " + room);
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }

        if (!isAvailable(room)) {
            throw new ServiceFailureException("Room is not available");
        }

        if (arrival.compareTo(departure) > 0) {
            throw new ServiceFailureException("departure must be after arrival");
        }

        SimpleJdbcInsert insertAccommodation = new SimpleJdbcInsert(jdbc).withTableName("accommodation").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("arrival", toTimestamp(arrival))
                .addValue("departure", toTimestamp(departure))
                .addValue("room", room.getId())
                .addValue("guest", guest.getId());

        insertAccommodation.execute(parameters);
        
        logDebug("Accommodated guest: " + guest + " to room: " + room);
    }

    @Override
    public void cancelAccommodation(Guest guest) {
        logDebug("cancelling accommodation to guest " + guest);
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }

        Accommodation accommodation;
        
        try {
            accommodation = jdbc.queryForObject("SELECT * FROM ACCOMMODATION WHERE GUEST=?", new RowMappers(roomManager, guestManager).accommodationMapper, guest.getId());
        } catch(DataAccessException e) {
            log.error("cancelAccommodation()", e);
            throw new ServiceFailureException("guest have no accommodation");
        }

        try {
                jdbc.update("DELETE FROM ACCOMMODATION WHERE ID = ? ", accommodation.getId());
        } catch(DataAccessException e) {
            log.error("cancelAccommodation()", e);
            throw new ServiceFailureException("Accommodation does not exist");
        }
        
        logDebug("Canceled accommodation of guest" + guest);
    }

    @Override
    public List<Room> getAvailableRooms() {
        logDebug("getting all available rooms");
        List<Room> allRooms = roomManager.getAllRooms();
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            if (isAvailable(room)) {
                availableRooms.add(room);
            }
        }

        logDebug("returned all available rooms");
        return availableRooms;
    }

    @Override
    public boolean isAvailable(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        List<Guest> guests = findGuests(room);
        boolean result = guests.size() < room.getNumberOfBeds();
        
        logDebug("room(id:" + room.getId() + ") is available: " + result);
        return result;
    }

    private static Timestamp toTimestamp(LocalDate localDate) {
        return Timestamp.valueOf(localDate.atStartOfDay());
    }
    
    private void logDebug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }
}
