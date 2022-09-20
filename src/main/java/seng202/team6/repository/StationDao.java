package seng202.team6.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Station class which implements the DaoInterface, provides common functionality for
 * database access.
 * @author Philip Dolbel
 */
public class StationDao implements DaoInterface<Station> {
    private final DatabaseManager databaseManager;
    private static final Logger log = LogManager.getLogger();

    /**
     * Creates new StationDAO object and gets reference to the database singleton.
     */
    public StationDao() {
        databaseManager = DatabaseManager.getInstance();
    }

    private Station stationFromResultSet(ResultSet rs) throws SQLException {
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
                null,
                rs.getInt("numberOfCarparks"),
                rs.getBoolean("carparkCost"),
                rs.getBoolean("chargingCost"),
                rs.getBoolean("hasTouristAttraction")
        );
    }

    @Override
    public List<Station> getAll(String sql) {
        List<Station> stations = new ArrayList<>();
        if (sql == null)
        {
            sql = "SELECT * FROM stations";
        }
        //String
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stations.add(stationFromResultSet(rs));
            }
            return stations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Station getOne(int id) {
        String sql = "SELECT * FROM stations WHERE stationId == (?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            return stationFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a station to the database.
     * @param toAdd object of type T to add
     * @return The id of the station in the db, or -1 if there was an error
     */
    @Override
    public int add(Station toAdd) {
        String stationSql = "INSERT INTO stations (objectId, name, operator, owner," +
                "address, timeLimit, is24Hours, numberOfCarparks, carparkCost," +
                "chargingCost, hasTouristAttraction, lat, long)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        String chargerSql = "INSERT INTO chargers (stationId, plugType, wattage, operative)" +
                "values (?,?,?,?)";
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
                PreparedStatement ps2 = conn.prepareStatement(chargerSql);
                ps2.setInt(1, insertId);
                ps2.setString(2, charger.getPlugType());
                ps2.setInt(3, charger.getWattage());
                ps2.setString(4, charger.getOperative());
                ps2.executeUpdate();
            }
            return insertId;
        } catch (SQLException sqlException) {
            log.error(sqlException);
            return -1;
        }
    }

    @Override
    public void delete(int id) {
        //delete station is not implemented yet

    }

    @Override
    public void update(Station toUpdate) {
        //update station is not implemented yet
    }

    @Override
    public Station getStation(int stationId) {
        return null;
    }


}
