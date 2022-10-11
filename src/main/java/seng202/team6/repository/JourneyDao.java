package seng202.team6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Journey;


/**
 * Journey class which implements the DaoInterface, provides common functionality for
 * database access.
 * @author Lucas Redding
 */
public class JourneyDao implements DaoInterface<Integer,Journey> {
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    private Journey journeyFromResultSet(ResultSet rs,
                                         List<String> addresses)throws SQLException {
        return new Journey(
                addresses,
                rs.getString("username"),
                rs.getInt("journeyId")
        );
    }

    /**
     * Get journeys of a particular user from database.
     */
    public Map<Integer, Journey> getAllFromUser(String username) {
        String sql = "SELECT * from journeys "
                + "INNER JOIN addresses a on journeys.journeyId = a.journeyId "
                + "WHERE journeys.username = (?)"
                + "ORDER BY journeys.journeyId, a.addressOrder";


        Map<Integer, Journey> journeys = new HashMap<>();
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement ps2 = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps2.setString(1, username);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            List<String> addresses = new ArrayList<>();

            boolean stillGoing = rs.next();

            while (stillGoing) {
                if (rs.getInt("journeyId") != rs2.getInt("journeyId")) {
                    Journey journey = journeyFromResultSet(rs2, new ArrayList<>(addresses));
                    journeys.put(journey.getJourneyId(), journey);
                    addresses.clear();
                }
                addresses.add(rs.getString("address"));
                stillGoing = rs.next();
                if (stillGoing) {
                    rs2.next();
                }
            }
            if (!addresses.isEmpty()) {
                Journey journey = journeyFromResultSet(rs2, addresses);
                journeys.put(journey.getJourneyId(), journey);
            }

            return journeys;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Journey> getAll() {
        String sql = "SELECT * from journeys "
                + "INNER JOIN addresses a on journeys.journeyId = a.journeyId "
                + "ORDER BY journeys.journeyId, a.addressOrder";

        Map<Integer, Journey> journeys = new HashMap<>();
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement ps2 = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             ResultSet rs2 = ps2.executeQuery()) {
            List<String> addresses = new ArrayList<>();
            boolean stillGoing = rs.next();
            while (stillGoing) {
                if (rs.getInt("journeyId") != rs2.getInt("journeyId")) {
                    Journey journey = journeyFromResultSet(rs2, new ArrayList<>(addresses));
                    journeys.put(journey.getJourneyId(), journey);
                    addresses.clear();
                }
                addresses.add(rs.getString("address"));
                stillGoing = rs.next();
                if (stillGoing) {
                    rs2.next();
                }
            }
            if (!addresses.isEmpty()) {
                Journey journey = journeyFromResultSet(rs2, addresses);
                journeys.put(journey.getJourneyId(), journey);
            }

            return journeys;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Journey getOne(int id) {
        throw new UnsupportedOperationException();
    }

    private void addAddresses(List<String> addresses, int journeyId) throws SQLException {
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
            throw new DatabaseException("A database error occurred", e);
        }
    }

    /**
     * Delete a journey.
     * @param id id of journey to delete.
     */
    @Override
    public void delete(int id) {
        String journeySql = "DELETE FROM journeys WHERE journeyId=(?)";

        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(journeySql)) {
            ps.setInt(1, id);
            ps.execute();
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
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAddress(int journeyId, String address, int order) {
        String insertAddressSql = "INSERT INTO addresses (journeyId, address, addressOrder) "
                + "Values (?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps2 = conn.prepareStatement(insertAddressSql)) {
            ps2.setInt(1, journeyId);
            ps2.setString(2, address);
            ps2.setInt(3, order);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Journey toUpdate) {
        throw new UnsupportedOperationException();
    }
}
