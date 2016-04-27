package cz.fi.muni.pv168;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import org.slf4j.Logger;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {
    private JdbcTemplate jdbc;
    static private String GUEST_NULL = "guest is null";
    
    final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
    
    public GuestManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void createGuest(Guest guest) {
        validate(guest);

        if (guest.getId() != null) {
            throw new IllegalArgumentException("guest id is already set");
        }

        SimpleJdbcInsert insertGuest = new SimpleJdbcInsert(jdbc).withTableName("guest").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("fullName", guest.getFullName());

        Number id = insertGuest.executeAndReturnKey(parameters);
        guest.setId(id.longValue());
    }

    @Override
    public void updateGuest(Guest guest) {
        validate(guest);

        if (guest.getId() == null) {
            throw new IllegalArgumentException("guest id is null");
        }
        jdbc.update("UPDATE guest set fullName=? WHERE id=?", guest.getFullName(), guest.getId());
    }

    @Override
    public void deleteGuest(Guest guest) {
        validate(guest);

        if (guest.getId() == null) {
            throw new IllegalArgumentException("guest id is null");
        }

        try {
            jdbc.update("DELETE FROM GUEST WHERE ID = ? ", guest.getId());
        } catch(EmptyResultDataAccessException ex) {
            throw new ServiceFailureException("Guest does not exist");
        }
    }

    @Override
    public Guest getGuest(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        try {
            return jdbc.queryForObject("SELECT * FROM guest WHERE id=?", RowMappers.guestMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Guest> getAllGuests() {
        return jdbc.query("SELECT * FROM guest", RowMappers.guestMapper);
    }

    private void validate(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException(GUEST_NULL);
        }
        if (guest.getFullName() == null || guest.getFullName().equals("")) {
            throw new IllegalArgumentException("guest fullName is unset");
        }
    }
}
