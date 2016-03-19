package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class RoomManagerImpl implements RoomManager {

    public void createRoom(Room room) {
        if (room == null)
            throw  new IllegalArgumentException();
    }

    public void updateRoom(Room room) {

    }

    public void deleteRoom(Room room) {
        if (room == null)
            throw  new IllegalArgumentException();
    }

    public Room getRoom(Long id) {
        return null;
    }

    public List<Room> getAvailableRooms() {
        return null;
    }

    public List<Room> getAllRooms() {
        return null;
    }
}
