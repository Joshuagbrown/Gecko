package seng202.team6.repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO implements DAOInterface<Station> {
    private final DatabaseManager databaseManager;
    private static final Logger log = LogManager.getLogger();

    /**
     * Creates new StationDAO object and gets reference to the database singleton
     */
    public StationDAO() {
        databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public List<Station> getAll() {
        List<Station> stations = new ArrayList<>();
        String sql = "SELECT * FROM stations";
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stations.add(new Station(
                        new Position(
                                rs.getDouble("lat"),
                                rs.getDouble("long")
                        ),
                        rs.getString("name")
                ));
            }
            return stations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Station getOne(int id) {
        return null;
    }

    /**
     * Adds a station to the database
     * @param toAdd object of type T to add
     * @return The id of the station in the db, or -1 if there was an error
     */
    @Override
    public int add(Station toAdd) {
        String sql = "INSERT INTO stations (lat, long, name) values (?,?,?);";
        try (Connection conn = databaseManager.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, toAdd.getCoordinates().getX());
            ps.setDouble(2, toAdd.getCoordinates().getY());
            ps.setString(3, toAdd.getName());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            return insertId;
        } catch (SQLException sqlException) {
            log.error(sqlException);
            return -1;
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Station toUpdate) {

    }
}
