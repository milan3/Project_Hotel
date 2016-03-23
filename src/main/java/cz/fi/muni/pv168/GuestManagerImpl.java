package cz.fi.muni.pv168;

import org.springframework.dao.EmptyResultDataAccessException;
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

        SimpleJdbcInsert insertGuest = new SimpleJdbcInsert(jdbc).withTableName("guest").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("fullName", guest.getFullName());

        Number id = insertGuest.executeAndReturnKey(parameters);
        guest.setId(id.longValue());
    }

    @Override
    public void updateGuest(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException();
        }

        if (guest.getId() == null) {
            throw new IllegalArgumentException();
        }
        jdbc.update("UPDATE guest set fullName=? WHERE id=?", guest.getFullName(), guest.getId());
    }

    @Override
    public void deleteGuest(Guest guest) {
        if (guest == null)
            throw  new IllegalArgumentException();
        try {
            jdbc.update("DELETE FROM GUEST WHERE ID = ? ", guest.getId());
        } catch(EmptyResultDataAccessException ex) {
            throw new ServiceFailureException("Guest does not exist");
        }
    }

    @Override
    public Guest getGuest(Long id) {
        try {
            return jdbc.queryForObject("SELECT * FROM guest WHERE id=?", guestMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Guest> getAllGuests() {
        return jdbc.query("SELECT * FROM guest", guestMapper);
    }
}
