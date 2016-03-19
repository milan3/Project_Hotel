package cz.fi.muni.pv168;

import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public interface RoomManager {
    public void createRoom(Room room);
    public void updateRoom(Room room);
    public void deleteRoom(Room room);
    public Room getRoom(Long id);

    public List<Room> getAllRooms();

}
