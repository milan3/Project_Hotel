package cz.fi.muni.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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
    private JdbcTemplate jdbc;
    
    public static final Logger log = LoggerFactory.getLogger(HotelManagerImpl.class);
    
    public static final String DEPARTURE_AFTER_ARRIVAL = "Departure must be after arrival";
    
    private static HotelManager instance = null;
    
    public static HotelManager getInstance() { 
        if (instance == null) {
            instance = new HotelManagerImpl();
        }
        
        return instance;
    }
   
    public HotelManagerImpl() {
        this.guestManager = null;
        this.roomManager = null;
        this.jdbc = HotelJdbc.getJdbc();
        this.guestManager = GuestManagerImpl.getInstance();
        this.roomManager = RoomManagerImpl.getInstance();
    }

    @Override
    public List<Guest> findGuests(Room room) {
        logDebug("finding guests in room: " + room);

        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        List<Guest> guests = new ArrayList<>();
        List<Accommodation> accommodations = findAccommodations(room);
            
        for (Accommodation acc : accommodations) {
            guests.add(acc.getGuest());
        }
        
        logDebug("returned all guests from room " + room);
        return guests;
    }
    //asi lepsie jak findGuests, zatial takto lebo sa zide
    public List<Accommodation> findAccommodations(Room room) {
        try {
            return jdbc.query("SELECT * FROM ACCOMMODATION WHERE room=?", RowMappers.accommodationMapper, room.getId());
        } catch(DataAccessException e) {
            logDebug("finding all accommodations from room " + room + " failed");
            return new ArrayList<>();
        }
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
    public Accommodation accommodateGuest(Room room, Guest guest, LocalDate arrival, LocalDate departure) {
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
            throw new ServiceFailureException(DEPARTURE_AFTER_ARRIVAL);
        }

        SimpleJdbcInsert insertAccommodation = new SimpleJdbcInsert(jdbc).withTableName("accommodation").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("arrival", toTimestamp(arrival))
                .addValue("departure", toTimestamp(departure))
                .addValue("room", room.getId())
                .addValue("guest", guest.getId());

        insertAccommodation.execute(parameters);
        
        Accommodation result = null;
        
        try {
            result = jdbc.queryForObject("SELECT * FROM ACCOMMODATION WHERE room=? AND guest=?", RowMappers.accommodationMapper, room.getId(), guest.getId());
        } catch(DataAccessException e) {
            log.error("accommodateGuest()", e);
            throw new ServiceFailureException("guest accommodation failed.");
        }
        
        logDebug("Accommodated guest: " + guest + " to room: " + room);
        
        return result;
    }

    @Override
    public void cancelAccommodation(Guest guest) {
        logDebug("cancelling accommodation to guest " + guest);
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }

        Accommodation accommodation;
        
        try {
            accommodation = jdbc.queryForObject("SELECT * FROM ACCOMMODATION WHERE GUEST=?", RowMappers.accommodationMapper, guest.getId());
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

    @Override
    public void updateAccommodation(Accommodation accommodation) {
        logDebug("updating accommodation " + accommodation);
        
        if (accommodation == null) {
            throw new IllegalArgumentException("accommodation is null");
        }
        if (accommodation.getRoom() == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (accommodation.getGuest() == null) {
            throw new IllegalArgumentException("guest is null");
        }

        if (accommodation.getArrival().compareTo(accommodation.getDeparture()) > 0) {
            throw new ServiceFailureException(DEPARTURE_AFTER_ARRIVAL);
        }
        
        if (accommodation.getId() == null) {
            throw new IllegalArgumentException("accommodation id is null");
        }
        
        try {
            jdbc.update("UPDATE accommodation set arrival=?, departure=?, room=?, guest=? WHERE id=?", toTimestamp(accommodation.getArrival()), toTimestamp(accommodation.getDeparture()), accommodation.getRoom().getId(), accommodation.getGuest().getId(), accommodation.getId());
        } catch(DataAccessException e) {
            log.error("updateAccommodation()", e);
            throw new ServiceFailureException("Problem with updating accommodation", e);
        }
        
        logDebug("accommodation: " + accommodation + "updated");
    }
    
    @Override
    public List<Guest> getGuestsWithoutAccommodation() {
        List<Room> rooms = roomManager.getAllRooms();
        List<Guest> allGuests = guestManager.getAllGuests();
        List<Guest> accommodatedGuests = new ArrayList<>();
        List<Guest> withoutAccommodation = new ArrayList<>();
        
        for (Room room : rooms) {
            accommodatedGuests.addAll(findGuests(room));
        }
        
        for (Guest guest : allGuests) {
            if (!accommodatedGuests.contains(guest)) {
                withoutAccommodation.add(guest);
            }
        }
        
        return withoutAccommodation;
    }

    @Override
    public Accommodation getAccommodationByGuest(Guest guest) {
        Accommodation acc = null;

        try {
            acc = jdbc.queryForObject("SELECT * FROM ACCOMMODATION WHERE guest=?", RowMappers.accommodationMapper, guest.getId());
        } catch(DataAccessException e) {
        }

        return acc;
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
