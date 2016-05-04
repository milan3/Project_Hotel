package cz.fi.muni.pv168;


import java.util.Objects;

/**
 * Created by Milan on 15.03.2016.
 */
public class Guest {
    private Long id;
    private String fullName;

    public Guest() {}

    public Guest(Guest guest) {
        this.id = guest.id;
        this.fullName = guest.fullName;
    }

    public Guest(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Guest other = (Guest) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Guest(" + fullName + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
