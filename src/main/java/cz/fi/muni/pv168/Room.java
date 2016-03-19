package cz.fi.muni.pv168;

import java.math.BigDecimal;

/**
 * Created by Milan on 15.03.2016.
 */
public class Room {
    private Long id;
    private int numberOfBeds;
    private int floor;
    private boolean balcony;
    private BigDecimal price;
    private int number;

    public Room(int numberOfBeds, int floor, boolean balcony, BigDecimal price) {
        this.numberOfBeds = numberOfBeds;
        this.floor = floor;
        this.balcony = balcony;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isBalcony() {
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

}
