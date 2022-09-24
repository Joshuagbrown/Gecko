package seng202.team6.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DuplicateEntryException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Station class which implements the DaoInterface, provides common functionality for
 * database access.
 * @author Philip Dolbel
 */
public class StationDao implements DaoInterface<Station> {
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

    @Override
    public HashMap<Integer, Station> getAll(String sql) {
        HashMap<Integer, Station> stations = new HashMap<>();
        if (sql == null) {
            sql = "SELECT * FROM stations "
                    + "INNER JOIN chargers c ON stations.stationId = c.stationId";
        }
        //String
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement();
             Statement stmt2 = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             ResultSet rs2 = stmt2.executeQuery(sql)) {
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
            throw new   RuntimeException(e);
        }
    }

    private Charger chargerFromResultSet(ResultSet rs) throws SQLException {
        return new Charger(
                rs.getString("plugType"),
                rs.getString("operative"),
                rs.getInt("wattage")
        );
    }

    @Override
    public Station getOne(int id) {
        throw new NotImplementedException();
    }

    /**
     * Adds a station to the database.
     * @param toAdd object of type T to add.
     * @return The id of the station in the db, or -1 if there was an error.
     */
    @Override
    public int add(Station toAdd) throws DuplicateEntryException {
        String stationSql = "INSERT INTO stations (objectId, name, operator, owner,"
                + "address, timeLimit, is24Hours, numberOfCarparks, carparkCost,"
                + "chargingCost, hasTouristAttraction, lat, long)"
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        String chargerSql = "INSERT INTO chargers (stationId, plugType, wattage, operative)"
                + "values (?,?,?,?)";
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

            for (Charger charger : toAdd.getChargers()) {
                try (PreparedStatement ps2 = conn.prepareStatement(chargerSql)) {
                    ps2.setInt(1, insertId);
                    ps2.setString(2, charger.getPlugType());
                    ps2.setInt(3, charger.getWattage());
                    ps2.setString(4, charger.getOperative());

                    ps2.executeUpdate();
                } catch (SQLException sqlException) {
                    log.error(sqlException);
                    return -1;
                }
            }
            return insertId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DuplicateEntryException("Duplicate Entry");
            }
            log.error(e.getMessage());
            return -1;
        }
    }

    @Override
    public void delete(int id) {
        throw new NotImplementedException();


    }

    @Override
    public void update(Station toUpdate) {
        String stationSql = "UPDATE  stations SET name = ? , operator = ? , owner = ? ,"
                + "address = ? , timeLimit = ? , is24Hours = ? , numberOfCarparks = ? , carparkCost = ? ,"
                + "chargingCost = ? , hasTouristAttraction = ? , lat = ? , long = ? ";
        String chargerSql = "UPDATE  chargers SET stationId = ? , plugType = ? , wattage = ? , operative = ? ";
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

            ps.executeUpdate();

            for (Charger charger : toUpdate.getChargers()) {
                try (PreparedStatement ps2 = conn.prepareStatement(chargerSql)) {
                    ps2.setInt(1, insertId);
                    ps2.setString(2, charger.getPlugType());
                    ps2.setInt(3, charger.getWattage());
                    ps2.setString(4, charger.getOperative());

                    ps2.executeUpdate();
                } catch (SQLException sqlException) {
                    log.error(sqlException);
                    return -1;
                }
            }
            return insertId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DuplicateEntryException("Duplicate Entry");
            }
            log.error(e.getMessage());
            return -1;
        }
    }



}
