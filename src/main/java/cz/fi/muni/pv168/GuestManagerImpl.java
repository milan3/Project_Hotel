package cz.fi.muni.pv168;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Milan on 15.03.2016.
 */
public class GuestManagerImpl implements GuestManager {
    private final JdbcTemplate jdbc;
    private static final String GUEST_NULL = "guest is null";
    
    final static Logger log = LoggerFactory.getLogger(GuestManagerImpl.class);
    
    public static GuestManager getInstance(DataSource ds) {
        return new GuestManagerImpl(ds);
    }
    
    public static GuestManager getInstance() {
        return new AnnotationConfigApplicationContext(SpringConfig.class).getBean(GuestManager.class);
    }
    
    public GuestManagerImpl(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void createGuest(Guest guest) {
        logDebug("creating guest: " + guest);
        validate(guest);

        if (guest.getId() != null) {
            throw new IllegalArgumentException("guest id is already set");
        }
        SimpleJdbcInsert insertGuest = new SimpleJdbcInsert(jdbc).withTableName("GUEST").usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                                                .addValue("fullName", guest.getFullName());
        
        Number id = insertGuest.executeAndReturnKey(parameters);
        guest.setId(id.longValue());
          
        logDebug("guest: " + guest + "created");
    }

    @Override
    public void updateGuest(Guest guest) {
        logDebug("updating guest: " + guest);
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
        
        logDebug("guest: " + guest + "updated");
    }

    @Override
    public void deleteGuest(Guest guest) {
        logDebug("deleting guest: " + guest);
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

        logDebug("guest: " + guest + "deleted");
    }

    @Override
    public Guest getGuest(Long id) {
        logDebug("finding guest with id: " + id);
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        Guest result = null;
        try {
            result = jdbc.queryForObject("SELECT * FROM guest WHERE id=?", RowMappers.guestMapper, id);
            //return jdbc.queryForObject("SELECT * FROM guest WHERE id=?", RowMappers.guestMapper, id);
        } catch (DataAccessException ex) {
            logDebug("guest with id: " + id + " wasn't found");
            return null;
        }
        logDebug("guest with id: " + id + " found");
        return result;
    }

    @Transactional
    @Override
    public List<Guest> getAllGuests() {
        logDebug("getting all guests");
        List<Guest> result;
        try {
            result = jdbc.query("SELECT * FROM guest", RowMappers.guestMapper);
            //return jdbc.query("SELECT * FROM guest", RowMappers.guestMapper);
        } catch(DataAccessException e) {
            logDebug("no guests found");
            return new ArrayList<>();
        }
        logDebug("all guests returned");
        return result;
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
