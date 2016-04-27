package cz.fi.muni.pv168;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImpl implements RoomManager {

    private final JdbcTemplate jdbc;

    final static Logger logger = LoggerFactory.getLogger(RoomManagerImpl.class);
    
    public RoomManagerImpl(DataSource dataSource) { 
        
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void createRoom(Room room) {
        logger.debug("Creating Room: "+room);
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
        logger.debug("Room successfully created");
    }

    @Override
    public void updateRoom(Room room) throws ServiceFailureException {
        validate(room);

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }
        if (roomNumberExists(room)) {
            throw new ServiceFailureException("room with the same number already exists");
        }
        jdbc.update("UPDATE room set number=?, numberOfBeds = ?, balcony = ?, price = ? WHERE id=?", room.getNumber(), room.getNumberOfBeds(), room.hasBalcony(), room.getPrice(), room.getId());
    }

    @Override
    public void deleteRoom(Room room) throws RuntimeException {
        validate(room);

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }

        try {
            jdbc.update("DELETE FROM ROOM WHERE ID = ? ", room.getId());
        } catch(EmptyResultDataAccessException ex) {
            throw new ServiceFailureException("Room does not exist");
        }
    }

    @Override
    public Room getRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        try {
                return jdbc.queryForObject("SELECT * FROM room WHERE id=?", RowMappers.roomMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Room> getAllRooms() {
        return jdbc.query("SELECT * FROM room", RowMappers.roomMapper);
    }

    private void validate(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getNumber() < 100) {
            throw new IllegalArgumentException("number of room is negative");
        }
        if (room.getNumberOfBeds() < 0) {
            throw new IllegalArgumentException("number of beds is negative");
        }
        if (room.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("price is negative");
        }
    }

    private boolean roomNumberExists(Room room) {
        for (Room other : getAllRooms()) {
            if (    room.getNumber() == other.getNumber() && ((room.getId() == null) ||
                    (room.getId() != null && room.getId() != other.getId()))) {
                return true;
            }
        }

        return false;
    }
}
