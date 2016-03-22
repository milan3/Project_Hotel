package cz.fi.muni.pv168;

import javax.sql.DataSource;
import javax.xml.ws.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImpl implements RoomManager {

    private final DataSource dataSource;

    public RoomManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createRoom(Room room) {
        validate(room);

        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement st = connection.prepareStatement(
                     "INSERT INTO ROOM (number,numberOfBeds,balcony,price) VALUES (?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS
             )) {

            st.setInt(1, room.getNumber());
            st.setInt(2, room.getNumberOfBeds());
            st.setBoolean(3, room.hasBalcony());
            st.setBigDecimal(4, room.getPrice());

            int addedRows = st.executeUpdate();

            if (addedRows != 1)
            {
                throw new ServiceFailureException("Internal error: More rows inserted while trying to insert room " + room);
            }

            ResultSet keyRs = st.getGeneratedKeys();
            if (keyRs == null) {
                System.out.println("key je null");
            }
            room.setId(getKey(keyRs, room));
        } catch(SQLException ex) {
            throw new ServiceFailureException("Error while inserting room " + room, ex);
        }
    }

    private Long getKey(ResultSet keyRS, Room room) throws RuntimeException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new RuntimeException("Internal Error: Generated key"
                        + "retrieving failed when trying to insert room " + room
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new RuntimeException("Internal Error: Generated key"
                        + "retrieving failed when trying to insert room " + room
                        + " - more keys found");
            }
            return result;
        } else {
            throw new RuntimeException("Internal Error: Generated key"
                    + "retrieving failed when trying to insert room " + room
                    + " - no key found");
        }
    }

    public void updateRoom(Room room) throws ServiceFailureException {
        validate(room);
        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement st = connection.prepareStatement(
                        "UPDATE Room SET number = ?, numberOfBeds = ?, balcony = ?, price = ? WHERE id = ?")) {

            st.setInt(1, room.getNumber());
            st.setInt(2, room.getNumberOfBeds());
            st.setBoolean(3, room.hasBalcony());
            st.setBigDecimal(4, room.getPrice());
            st.setLong(5, room.getId());

            int count = st.executeUpdate();

            if (count == 0) {
                throw new EntityNotFoundException("Room " + room + " was not found in database!");
            } else if (count != 1) {
                throw new ServiceFailureException("Invalid updated rows count detected (one row should be updated): " + count);
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when updating room " + room, ex);
        }
    }

    public void deleteRoom(Room room) throws RuntimeException {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }

        if (room.getId() == null) {
            throw new IllegalArgumentException("room id is null");
        }

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "DELETE FROM room WHERE id = ?")) {

            st.setLong(1, room.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                throw new RuntimeException("Room " + room + " was not found in database!");
            } else if (count != 1) {
                throw new RuntimeException("Invalid deleted rows count detected (one row should be updated): " + count);
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when updating room " + room, ex);
        }

    }

    public Room getRoom(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT id,number,numberOfBeds,balcony,price FROM room WHERE id = ?")) {

            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Room room = resultSetToRoom(rs);

                if (rs.next()) {
                    throw new ServiceFailureException(
                            "Internal error: More entities with the same id found "
                                    + "(source id: " + id + ", found " + room + " and " + resultSetToRoom(rs));
                }

                return room;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving room with id " + id, ex);
        }

    }

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getInt("number"));
        room.setNumberOfBeds(rs.getInt("numberOfBeds"));
        room.setBalcony(rs.getBoolean("balcony"));
        room.setPrice(rs.getBigDecimal("price"));
        return room;
    }

    public List<Room> getAllRooms() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT id,number,numberOfBeds,balcony,price FROM room")) {

            ResultSet rs = st.executeQuery();

            List<Room> result = new ArrayList<>();
            while (rs.next()) {
                result.add(resultSetToRoom(rs));
            }
            return result;

        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all rooms", ex);
        }
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
