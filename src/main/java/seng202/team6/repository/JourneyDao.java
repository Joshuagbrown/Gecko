package seng202.team6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
import seng202.team6.models.Journey;
import seng202.team6.models.Position;
import seng202.team6.models.Station;


/**
 * Journey class which implements the DaoInterface, provides common functionality for
 * database access.
 * @author Lucas Redding
 */
public class JourneyDao implements DaoInterface<Journey> {
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    private Journey journeyFromResultSet(ResultSet rs,
                                         ArrayList<String> addresses) throws SQLException {
        return new Journey(
                addresses,
                rs.getString("username")
        );
    }

    @Override
    public Map<Integer, Journey> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Journey getOne(int id) {
        throw new NotImplementedException();
    }

    private void addAddresses(ArrayList<String> addresses, int journeyId) throws SQLException {
        String addressSql = "INSERT INTO addresses (journeyId, address, addressOrder)"
                + "values (?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(addressSql)) {
            for (int i = 0; i < addresses.size(); i++) {
                try (PreparedStatement ps2 = conn.prepareStatement(addressSql)) {
                    ps.setInt(1, journeyId);
                    ps.setString(2, addresses.get(i));
                    ps.setInt(3, i);
                    ps.executeUpdate();
                }
            }
        }
    }

    /**
     * Adds a journey to the database.
     * @param toAdd object of type T to add.
     * @return The id of the journey in the db, or -1 if there was an error.
     */
    @Override
    public int add(Journey toAdd) throws DatabaseException {
        String journeySql = "INSERT INTO journeys (username)"
                + "values (?);";

        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(journeySql)) {
            ps.setString(1, toAdd.getUsername());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            addAddresses(toAdd.getAddresses(), insertId);
            return insertId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DatabaseException("A duplicate entry was provided", e);
            }
            throw new DatabaseException("A database error occurred", e);
        }
    }

    /**
     * Delete a journey.
     * @param id id of journey to delete.
     */
    @Override
    public void delete(int id) {
        String journeySql = "DELETE FROM journeys WHERE journeyId=?";

        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(journeySql)) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete an address.
     * @param id id of the address to delete
     */
    public void deleteAddress(int id) {
        String addressSql = "DELETE FROM addresses WHERE addressId=?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(addressSql)) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAddress(int journeyId, String address, int order) {
        String insertAddressSql = "INSERT INTO addresses (journeyId, address, order) "
                + "Values (?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps2 = conn.prepareStatement(insertAddressSql)) {
            ps2.setInt(1, journeyId);
            ps2.setString(2, address);
            ps2.setInt(3, order);
            ps2.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Journey toUpdate) {
        throw new NotImplementedException();
    }
}
