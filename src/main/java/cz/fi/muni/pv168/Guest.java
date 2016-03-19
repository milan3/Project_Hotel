package cz.fi.muni.pv168;



/**
 * Created by Milan on 15.03.2016.
 */
public class Guest {
    private Long id;
    private String fullName;

    public Guest(String name) {
        fullName = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        if (!id.equals(guest.id)) return false;
        return fullName != null ? fullName.equals(guest.fullName) : guest.fullName == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
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
}
