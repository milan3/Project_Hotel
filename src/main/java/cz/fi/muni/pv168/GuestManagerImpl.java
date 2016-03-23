package cz.fi.muni.pv168;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {
    private JdbcTemplate jdbc;

    public GuestManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    private RowMapper<Guest> guestMapper = (rs, rowNum) ->  {
        Guest guest = new Guest();
        guest.setId(rs.getLong("id"));
        guest.setFullName(rs.getString("fullName"));

        return guest;
    };

    @Override
    public void createGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();

        SimpleJdbcInsert insertGuest = new SimpleJdbcInsert(jdbc).withTableName("guests").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("fullName", guest.getFullName());

        Number id = insertGuest.executeAndReturnKey(parameters);
        guest.setId(id.longValue());
    }

    @Override
    public void updateGuest(Guest guest) {
        jdbc.update("UPDATE guests set fullName=? WHERE id=?", guest.getFullName());
    }

    @Override
    public void deleteGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
    }

    @Override
    public Guest getGuest(Long id) {
        return jdbc.queryForObject("SELECT * FROM guests WHERE id=?", guestMapper, id);
    }

    @Override
    public List<Guest> getAllGuests() {
        return jdbc.query("SELECT * FROM guests", guestMapper);
    }
}
