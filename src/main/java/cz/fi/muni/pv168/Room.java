package cz.fi.muni.pv168;

import java.math.BigDecimal;

/**
 * Created by Milan on 15.03.2016.
 */
public class Room {
    private Long id;
    private int number;
    private int numberOfBeds;
    private boolean balcony;
    private BigDecimal price;

    public Room() {}

    public Room(Room room) {
        this.id = room.id;
        this.number = room.number;
        this.numberOfBeds = room.numberOfBeds;
        this.balcony = room.balcony;
        this.price = room.price;
    }

    public Room(int number, int numberOfBeds, boolean balcony, BigDecimal price) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
        this.balcony = balcony;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean hasBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room(" + number + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id != null ? id.equals(room.id) : room.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
