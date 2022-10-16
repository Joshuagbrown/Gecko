package seng202.team6.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.WritableIntegerValue;
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

    private Station stationFromResultSet(ResultSet rs) throws SQLException {
        return new Station(
                new Position(
                        rs.getDouble("lat"),
                        rs.getDouble("long")
                ),
                rs.getString("name"),
                rs.getString("operator"),
                rs.getString("owner"),
                rs.getString("address"),
                rs.getInt("timeLimit"),
                rs.getBoolean("is24Hours"),
                new ArrayList<>(),
                rs.getInt("numberOfCarparks"),
                rs.getBoolean("carparkCost"),
                rs.getBoolean("chargingCost"),
                rs.getBoolean("hasTouristAttraction"),
                rs.getInt("stationId")
        );
    }

    /**
     * Get a map of stations from the database, using the filter builder provided.
     * @param builder The filter builder to use
     */
    public Map<Integer, Station> getFromFilterBuilder(FilterBuilder builder)
            throws DatabaseException {
        Map<Integer, Station> stations = new HashMap<>();
        Map<Integer, List<Charger>> chargers = new HashMap<>();
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = builder.build(conn)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!stations.containsKey(rs.getInt("stationId"))) {
                    stations.put(rs.getInt("stationId"), stationFromResultSet(rs));
                }
                chargers.putIfAbsent(rs.getInt("stationId"), new ArrayList<>());
                chargers.get(rs.getInt("stationId"))
                        .add(chargerFromResultSet(rs));
            }

            for (Station station : stations.values()) {
                for (Charger charger : chargers.getOrDefault(
                        station.getStationId(), new ArrayList<>())) {
                    station.addCharger(charger);
                }
            }
            return stations;
        } catch (SQLException e) {
            throw new DatabaseException("An error occurred getting stations from the database", e);
        }
    }

    //    /**
    //     * Get stations from a filter builder.
    //     * @param builder The builder to use.
    //     */
    //    public Map<Integer, Station> getFromFilterBuilder(FilterBuilder builder) {
    //        Map<Integer, Station> stations = new HashMap<>();
    //        try (Connection conn = databaseManager.connect();
    //             PreparedStatement ps = builder.build(conn);
    //             PreparedStatement ps2 = builder.build(conn);
    //             ResultSet rs = ps.executeQuery();
    //             ResultSet rs2 = ps2.executeQuery()) {
    //            ArrayList<Charger> chargers = new ArrayList<>();
    //            boolean stillGoing = rs.next();
    //            while (stillGoing) {
    //                if (rs.getInt("stationId") != rs2.getInt("stationId")) {
    //                    Station station = stationFromResultSet(rs2, new ArrayList<>(chargers));
    //                    stations.put(station.getStationId(), station);
    //                    chargers.clear();
    //                }
    //                chargers.add(chargerFromResultSet(rs));
    //                stillGoing = rs.next();
    //                if (stillGoing) {
    //                    rs2.next();
    //                }
    //            }
    //            if (!chargers.isEmpty()) {
    //                Station station = stationFromResultSet(rs2, chargers);
    //                stations.put(station.getStationId(), station);
    //            }
    //            return stations;
    //        } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //        }
    //    }

    @Override
    public Map<Integer, Station> getAll() {
        throw new UnsupportedOperationException("Please use getFromFilterBuilder instead");
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
        throw new UnsupportedOperationException("Please use the getFromFilterBuilder");
    }

    private void addChargers(List<Charger> chargers, int stationId,
                             Connection conn) throws SQLException {
        String chargerSql = "INSERT INTO chargers (stationId, plugType, wattage, operative)"
                + "values (?,?,?,?)";
        for (Charger charger : chargers) {
            try (PreparedStatement ps = conn.prepareStatement(chargerSql)) {
                ps.setInt(1, stationId);
                ps.setString(2, charger.getPlugType());
                ps.setInt(3, charger.getWattage());
                ps.setString(4, charger.getOperative());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Add a list of stations to the database.
     * @param stations the list of stations to add
     * @param value A writableintegervalue to use for the progress bar
     */
    public void addAll(List<Station> stations,
                       WritableIntegerValue value) throws DatabaseException {
        try (Connection conn = databaseManager.connect()) {
            conn.setAutoCommit(false);
            for (Station station : stations) {
                add(station, conn);
                value.set(value.get() + 1);
            }
            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseException("A database error occurred", e);
        }
    }

    /**
     * Adds a station to the database.
     * @param toAdd object of type T to add.
     * @return The id of the station in the db
     */
    @Override
    public int add(Station toAdd) throws DatabaseException {
        try (Connection conn = databaseManager.connect()) {
            return add(toAdd, conn);
        } catch (SQLException e) {
            throw new DatabaseException("A database error occurred", e);
        }
    }

    /**
     * Adds a station to the database using the given connection.
     * @param toAdd object of type T to add.
     * @param conn The connection to use, useful for batch (not really) processing
     * @return The id of the station in the db
     */
    private int add(Station toAdd, Connection conn) throws DatabaseException {
        String stationSql = "INSERT INTO stations (name, operator, owner,"
                + "address, timeLimit, is24Hours, numberOfCarparks, carparkCost,"
                + "chargingCost, hasTouristAttraction, lat, long)"
                + "values (?,?,?,?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement ps = conn.prepareStatement(stationSql)) {
            ps.setString(1, toAdd.getName());
            ps.setString(2, toAdd.getOperator());
            ps.setString(3, toAdd.getOwner());
            ps.setString(4, toAdd.getAddress());
            ps.setInt(5, toAdd.getTimeLimit());
            ps.setBoolean(6, toAdd.is24Hours());
            ps.setInt(7, toAdd.getNumberOfCarParks());
            ps.setBoolean(8, toAdd.isCarparkCost());
            ps.setBoolean(9, toAdd.isChargingCost());
            ps.setBoolean(10, toAdd.isHasTouristAttraction());
            ps.setDouble(11, toAdd.getCoordinates().getLatitude());
            ps.setDouble(12, toAdd.getCoordinates().getLongitude());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            addChargers(toAdd.getChargers(), insertId, conn);
            return insertId;
        } catch (SQLException e) {
            log.error("An error occurred", e);
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
     * Function to delete a charger from the database.
     * @param charger the charger to be deleted.
     * @param stationId the station id for the station the charger is to be deleted from
     */
    public void deleteCharger(Charger charger, int stationId) {
        String deleteChargerSql = "DELETE FROM chargers WHERE chargerId = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(deleteChargerSql)) {
            ps.setInt(1, charger.getChargerId());
            ps.execute();
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

            ResultSet rs = ps.getGeneratedKeys();

            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            charger.setChargerId(insertId);
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
                + "WHERE stationId=?";
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
            ps.setInt(13, toUpdate.getStationId());

            ps.executeUpdate();

            for (Charger charger : toUpdate.getChargers()) {
                if (charger.getChargerId() == -1) {
                    addCharger(charger, toUpdate.getStationId());
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
