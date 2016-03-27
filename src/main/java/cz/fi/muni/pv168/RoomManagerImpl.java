package cz.fi.muni.pv168;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImpl implements RoomManager {

    private final JdbcTemplate jdbc;

    public RoomManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void createRoom(Room room) {
        validate(room);

        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");
        }

        SimpleJdbcInsert insertRoom = new SimpleJdbcInsert(jdbc).withTableName("room").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("number", room.getNumber())
                .addValue("numberOfBeds", room.getNumberOfBeds())
                .addValue("balcony", room.hasBalcony())
                .addValue("price", room.getPrice());

        Number id = insertRoom.executeAndReturnKey(parameters);
        room.setId(id.longValue());
    }

    public void updateRoom(Room room) throws ServiceFailureException {
        validate(room);

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }

        jdbc.update("UPDATE room set number=?, numberOfBeds = ?, balcony = ?, price = ? WHERE id=?", room.getNumber(), room.getNumberOfBeds(), room.hasBalcony(), room.getPrice(), room.getId());
    }

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

    public List<Room> getAllRooms() {
        return jdbc.query("SELECT * FROM room", RowMappers.roomMapper);
    }


    private void validate(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getNumber() < 0) {
            throw new IllegalArgumentException("number of room is negative");
        }
        if (room.getNumberOfBeds() < 0) {
            throw new IllegalArgumentException("number of beds is negative");
        }
        if (room.getPrice().doubleValue() < 0) {
            throw new IllegalArgumentException("price is negative");
        }
    }
}
