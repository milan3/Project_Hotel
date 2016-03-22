package cz.fi.muni.pv168;

import java.time.LocalDate;

/**
 * Created by Milan on 15.03.2016.
 */
public class Accommodation {
    private Long id;
    private LocalDate arrival;
    private LocalDate departure;
    private Room room;
    private Guest guest;

    public Accommodation() {}

    public Accommodation(Accommodation accommodation) {
        this.id = accommodation.id;
        this.arrival = accommodation.arrival;
        this.departure = accommodation.departure;
        this.room = accommodation.room;
        this.guest = accommodation.guest;
    }

    public Accommodation(LocalDate arrival, LocalDate departure, Room room, Guest guest) {
        this.arrival = arrival;
        this.departure = departure;
        this.room = room;
        this.guest = guest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
