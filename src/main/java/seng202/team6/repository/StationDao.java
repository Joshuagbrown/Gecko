package seng202.team6.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

/**
 * Station class which implements the DaoInterface, provides common functionality for
 * database access.
 * @author Philip Dolbel
 */
public class StationDao implements DaoInterface<Integer, Station> {
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    private Station stationFromResultSet(ResultSet rs, List<Charger> chargers) throws SQLException {
        return new Station(
                new Position(
                        rs.getDouble("lat"),
                        rs.getDouble("long")
                ),
                rs.getString("name"),
                rs.getInt("objectId"),
                rs.getString("operator"),
                rs.getString("owner"),
                rs.getString("address"),
                rs.getInt("timeLimit"),
                rs.getBoolean("is24Hours"),
                chargers,
                rs.getInt("numberOfCarparks"),
                rs.getBoolean("carparkCost"),
                rs.getBoolean("chargingCost"),
                rs.getBoolean("hasTouristAttraction")
        );
    }

    /**
     * Get stations from a filter builder.
     * @param builder The builder to use.
     */
    public Map<Integer, Station> getFromFilterBuilder(FilterBuilder builder) {
        Map<Integer, Station> stations = new HashMap<>();
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = builder.build(conn);
             PreparedStatement ps2 = builder.build(conn);
             ResultSet rs = ps.executeQuery();
             ResultSet rs2 = ps2.executeQuery()) {
            ArrayList<Charger> chargers = new ArrayList<>();
            boolean stillGoing = rs.next();
            while (stillGoing) {
                if (rs.getInt("stationId") != rs2.getInt("stationId")) {
                    Station station = stationFromResultSet(rs2, new ArrayList<>(chargers));
                    stations.put(station.getObjectId(), station);
                    chargers.clear();
                }
                chargers.add(chargerFromResultSet(rs));
                stillGoing = rs.next();
                if (stillGoing) {
                    rs2.next();
                }
            }
            if (!chargers.isEmpty()) {
                Station station = stationFromResultSet(rs2, chargers);
                stations.put(station.getObjectId(), station);
            }
            return stations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Station> getAll() {
        return getFromFilterBuilder(new FilterBuilder());
    }

    private Charger chargerFromResultSet(ResultSet rs) throws SQLException {
        return new Charger(
                rs.getString("plugType"),
                rs.getString("operative"),
                rs.getInt("wattage"),
                rs.getInt("chargerId")
        );
    }

    @Override
    public Station getOne(int id) {
        String sql = "SELECT * from stations INNER JOIN chargers "
                + "ON stations.stationId = chargers.stationId "
                + "WHERE stations.stationId = (?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement ps2 = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps2.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<Charger> chargers = new ArrayList<>();
            boolean stillGoing = rs.next();
            while (stillGoing) {
                chargers.add(chargerFromResultSet(rs));
                stillGoing = rs.next();
                if (stillGoing) {
                    rs2.next();
                }
            }
            if (!chargers.isEmpty()) {
                return stationFromResultSet(rs2, chargers);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addChargers(List<Charger> chargers, int stationId) throws SQLException {
        String chargerSql = "INSERT INTO chargers (stationId, plugType, wattage, operative)"
                + "values (?,?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(chargerSql)) {
            for (Charger charger : chargers) {
                try (PreparedStatement ps2 = conn.prepareStatement(chargerSql)) {
                    ps.setInt(1, stationId);
                    ps.setString(2, charger.getPlugType());
                    ps.setInt(3, charger.getWattage());
                    ps.setString(4, charger.getOperative());
                    ps.executeUpdate();
                }
            }
        }
    }

    /**
     * Adds a station to the database.
     * @param toAdd object of type T to add.
     * @return The id of the station in the db, or -1 if there was an error.
     */
    @Override
    public int add(Station toAdd) throws DatabaseException {
        String stationSql = "INSERT INTO stations (objectId, name, operator, owner,"
                + "address, timeLimit, is24Hours, numberOfCarparks, carparkCost,"
                + "chargingCost, hasTouristAttraction, lat, long)"
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(stationSql)) {
            ps.setInt(1, toAdd.getObjectId());
            ps.setString(2, toAdd.getName());
            ps.setString(3, toAdd.getOperator());
            ps.setString(4, toAdd.getOwner());
            ps.setString(5, toAdd.getAddress());
            ps.setInt(6, toAdd.getTimeLimit());
            ps.setBoolean(7, toAdd.is24Hours());
            ps.setInt(8, toAdd.getNumberOfCarParks());
            ps.setBoolean(9, toAdd.isCarparkCost());
            ps.setBoolean(10, toAdd.isChargingCost());
            ps.setBoolean(11, toAdd.isHasTouristAttraction());
            ps.setDouble(12, toAdd.getCoordinates().getLatitude());
            ps.setDouble(13, toAdd.getCoordinates().getLongitude());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            addChargers(toAdd.getChargers(), insertId);
            return insertId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DatabaseException("A duplicate entry was provided", e);
            }
            throw new DatabaseException("A database error occurred", e);
        }
    }

    @Override
    public void delete(int id) {
        String stationSql = "DELETE FROM stations WHERE stationId=?";

        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(stationSql)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a charger.
     * @param id id of the charger to delete
     */
    public void deleteCharger(int id) {
        String chargerSql = "DELETE FROM chargers WHERE chargerId=?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(chargerSql)) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCharger(Charger charger, int stationId) {
        String insertChargerSql = "INSERT INTO chargers (plugType,wattage,operative,stationId) "
                + "Values (?,?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(insertChargerSql)) {
            ps.setString(1, charger.getPlugType());
            ps.setInt(2, charger.getWattage());
            ps.setString(3, charger.getOperative());
            ps.setInt(4, stationId);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCharger(Charger charger) {
        String updateChargerSql = "UPDATE chargers SET plugType =?, wattage=? , operative=? "
                + "WHERE chargerId=?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps2 = conn.prepareStatement(updateChargerSql)) {
            ps2.setString(1, charger.getPlugType());
            ps2.setInt(2, charger.getWattage());
            ps2.setString(3, charger.getOperative());
            ps2.setInt(4, charger.getChargerId());
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Station toUpdate) {
        String stationSql = "UPDATE stations SET name=? , operator=? , owner=?,"
                + "address=? , timeLimit=? , is24Hours=? , numberOfCarparks=?, carparkCost=? ,"
                + "chargingCost=? , hasTouristAttraction=?, lat=? , long=? "
                + "WHERE objectId=?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(stationSql)) {
            ps.setString(1, toUpdate.getName());
            ps.setString(2, toUpdate.getOperator());
            ps.setString(3, toUpdate.getOwner());
            ps.setString(4, toUpdate.getAddress());
            ps.setInt(5, toUpdate.getTimeLimit());
            ps.setBoolean(6, toUpdate.is24Hours());
            ps.setInt(7, toUpdate.getNumberOfCarParks());
            ps.setBoolean(8, toUpdate.isCarparkCost());
            ps.setBoolean(9, toUpdate.isChargingCost());
            ps.setBoolean(10, toUpdate.isHasTouristAttraction());
            ps.setDouble(11, toUpdate.getCoordinates().getLatitude());
            ps.setDouble(12, toUpdate.getCoordinates().getLongitude());
            ps.setInt(13, toUpdate.getObjectId());

            ps.executeUpdate();

            for (Charger charger : toUpdate.getChargers()) {
                if (charger.getChargerId() == -1) {
                    addCharger(charger, toUpdate.getObjectId());
                } else {
                    updateCharger(charger);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * Function to get the distinct charger types from the database.
     * @return the distinct charger types
     */
    public List<String> getChargerTypes() {
        String plugType = "SELECT DISTINCT plugType FROM chargers";
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(plugType);
            List<String> types = new ArrayList<>();
            while (rs.next()) {
                String type = rs.getString(1);
                types.add(type);
            }
            return types;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
