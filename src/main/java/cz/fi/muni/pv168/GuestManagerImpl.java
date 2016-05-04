package cz.fi.muni.pv168;

import java.util.ArrayList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {
    private final JdbcTemplate jdbc;
    private static final String GUEST_NULL = "guest is null";
    
    final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
    
    private static GuestManager instance = null;
    
    public static GuestManager getInstance() { 
        return instance;
    }
    
    public static void init(DataSource dataSource) {
        instance = new GuestManagerImpl(dataSource);
    }
    
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
        
        logDebug("guest(id: " + id.longValue() + ") created");
    }

    @Override
    public void updateGuest(Guest guest) {
        validate(guest);

        if (guest.getId() == null) {
            throw new IllegalArgumentException("guest id is null");
        }
        
        try {
            jdbc.update("UPDATE guest set fullName=? WHERE id=?", guest.getFullName(), guest.getId());
        } catch(DataAccessException e) {
            log.error("updateGuest()", e);
            throw new ServiceFailureException("Problem with updating guest", e);
        }
        
        logDebug("guest(id:" + guest.getId() + ") updated");
    }

    @Override
    public void deleteGuest(Guest guest) {
        validate(guest);

        if (guest.getId() == null) {
            throw new IllegalArgumentException("guest id is null");
        }

        try {
            jdbc.update("DELETE FROM GUEST WHERE ID = ? ", guest.getId());
        } catch(DataAccessException ex) {
            log.error("deleteGuest()", ex);
            throw new ServiceFailureException("Problem with deleting guest", ex);
        }
        
        logDebug("guest(id:" + guest.getId() + ") deleted");
    }

    @Override
    public Guest getGuest(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        try {
            logDebug("guest(id:" + id + ") returned");
            return jdbc.queryForObject("SELECT * FROM guest WHERE id=?", RowMappers.guestMapper, id);
        } catch (DataAccessException ex) {
            logDebug("guest(id:" + id + ") wasnt found");
            return null;
        }
    }

    @Override
    public List<Guest> getAllGuests() {
        try {
            logDebug("all guests returned");
            return jdbc.query("SELECT * FROM guest", RowMappers.guestMapper);
        } catch(DataAccessException e) {
            logDebug("no guests found");
            return new ArrayList<>();
        }
    }

    private void validate(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException(GUEST_NULL);
        }
        if (guest.getFullName() == null || guest.getFullName().equals("")) {
            throw new IllegalArgumentException("guest fullName is unset");
        }
    }
    
    private void logDebug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }
}
