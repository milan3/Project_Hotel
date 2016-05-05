package cz.fi.muni.pv168;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImpl implements RoomManager {

    private JdbcTemplate jdbc;

    public static final String NEGATIVE_BEDS = "number of beds is negative";
    public static final String NEGATIVE_NUMBER = "number of room is negative";
    public static final String NEGATIVE_PRICE = "price is negative";
    
    final static Logger log = LoggerFactory.getLogger(RoomManagerImpl.class);
    
    private static RoomManager instance = null;
    
    public static RoomManager getInstance() { 
        if (instance == null) {
            instance = new RoomManagerImpl(); 
        }
        
        return instance;
    }
    
    private RoomManagerImpl() { 
        this.jdbc = HotelJdbc.getJdbc();
    }

    @Override
    public void createRoom(Room room) {
        logDebug("creating room: " + room);
        validate(room);
        
        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");
        }
        if (roomNumberExists(room)) {
            throw new ServiceFailureException("room with the same number already exists");
        }
        
        SimpleJdbcInsert insertRoom = new SimpleJdbcInsert(jdbc).withTableName("room").usingGeneratedKeyColumns("id");
        
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("number", room.getNumber())
                .addValue("numberOfBeds", room.getNumberOfBeds())
                .addValue("balcony", room.hasBalcony())
                .addValue("price", room.getPrice());

        Number id = insertRoom.executeAndReturnKey(parameters);
        room.setId(id.longValue());
        logDebug("room: " + room + " created");
    }

    @Override
    public void updateRoom(Room room) throws ServiceFailureException {
        logDebug("updating room: " + room);
        validate(room);

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }
        if (roomNumberExists(room)) {
            throw new ServiceFailureException("room with the same number already exists");
        }
        jdbc.update("UPDATE room set number=?, numberOfBeds = ?, balcony = ?, price = ? WHERE id=?", room.getNumber(), room.getNumberOfBeds(), room.hasBalcony(), room.getPrice(), room.getId());
        logDebug("room: " + room + " updated");
    }

    @Override
    public void deleteRoom(Room room) throws RuntimeException {
        logDebug("deleting room: " + room);
        validate(room);

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }

        try {
            jdbc.update("DELETE FROM ROOM WHERE ID = ? ", room.getId());
        } catch(EmptyResultDataAccessException ex) {
            log.error("deleteRoom()", ex);
            throw new ServiceFailureException("Room does not exist");
        }
        logDebug("room: " + room + "deleted");
    }

    @Override
    public Room getRoom(Long id) {
        logDebug("finding room with id: " + id);
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        Room result = null;

        try {
            result = jdbc.queryForObject("SELECT * FROM room WHERE id=?", RowMappers.roomMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            logDebug("room with id: " + id + " wasn't found");
            return null;
        }
        logDebug("room with id: " + id + " found");
        return result;
    }
    
    @Override
    public Room getRoom(int number) {
        logDebug("finding room with number: " + number);
        if (number < 0) {
            throw new IllegalArgumentException("number is negative");
        }

        Room result = null;

        try {
            result = jdbc.queryForObject("SELECT * FROM room WHERE number=?", RowMappers.roomMapper, number);
        } catch (EmptyResultDataAccessException ex) {
            logDebug("room with number: " + number + " wasn't found");
            return null;
        }
        logDebug("room with number: " + number + " found");
        return result;
    }
    
    @Override
    public List<Room> getAllRooms() {
        logDebug("getting all rooms");
        List<Room> result;
        try {
            result = jdbc.query("SELECT * FROM room", RowMappers.roomMapper);
        } catch(DataAccessException e) {
            logDebug("no rooms found");
            return new ArrayList<>();
        }
        logDebug("all rooms returned (" + result.size() + ")");
        return result;

    }

    private void validate(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getNumber() < 100) {
            throw new IllegalArgumentException(NEGATIVE_NUMBER);
        }
        if (room.getNumberOfBeds() < 0) {
            throw new IllegalArgumentException(NEGATIVE_BEDS);
        }
        if (room.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException(NEGATIVE_PRICE);
        }
    }

    private boolean roomNumberExists(Room room) {       
        for (Room other : getAllRooms()) {
            if (    room.getNumber() == other.getNumber() && ((room.getId() == null) ||
                    !room.getId().equals(other.getId()))) {
                return true;
            }
        }

        return false;
    }

    private void logDebug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }
}
