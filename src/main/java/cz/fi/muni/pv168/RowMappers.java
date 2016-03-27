package cz.fi.muni.pv168;

import org.springframework.jdbc.core.RowMapper;

/**
 * Created by Radac on 27.3.2016.
 */
public class RowMappers {
    private RoomManager roomManager;
    private GuestManager guestManager;

    public RowMappers(RoomManager roomManager, GuestManager guestManager) {
        this.roomManager = roomManager;
        this.guestManager = guestManager;
    }

    public static RowMapper<Guest> guestMapper = (rs, rowNum) ->  {
        Guest guest = new Guest();
        guest.setId(rs.getLong("id"));
        guest.setFullName(rs.getString("fullName"));

        return guest;
    };

    public RowMapper<Accommodation> accommodationMapper = (rs, rowNum) ->  {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(rs.getLong("id"));
        accommodation.setArrival(rs.getTimestamp("arrival").toLocalDateTime().toLocalDate());
        accommodation.setDeparture(rs.getTimestamp("departure").toLocalDateTime().toLocalDate());
        accommodation.setGuest(guestManager.getGuest(rs.getLong("guest")));
        accommodation.setRoom(roomManager.getRoom(rs.getLong("room")));

        return accommodation;
    };

    public static RowMapper<Room> roomMapper = (rs, rowNum) -> {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setPrice(rs.getBigDecimal("price"));
        room.setBalcony(rs.getBoolean("balcony"));
        room.setNumber(rs.getInt("number"));
        room.setNumberOfBeds(rs.getInt("numberOfBeds"));

        return room;
    };
}
