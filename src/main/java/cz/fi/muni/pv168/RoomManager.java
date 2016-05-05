package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface RoomManager {
    void createRoom(Room room);
    void updateRoom(Room room);
    void deleteRoom(Room room);
    Room getRoom(int number);
    Room getRoom(Long id);

    List<Room> getAllRooms();

}
