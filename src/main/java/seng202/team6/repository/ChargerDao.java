//package seng202.team6.repository;
//
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import seng202.team6.exceptions.DuplicateEntryException;
//import seng202.team6.models.Charger;
//import seng202.team6.models.Station;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public class ChargerDao implements DaoInterface<Charger> {
//    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
//
//    private static final Logger log = LogManager.getLogger();
//
//    public int add(int stationId, Charger charger) {
//        String chargerSql = "INSERT INTO chargers (stationId, plugType, wattage, operative)" +
//                "values (?,?,?,?)";
//        try (Connection conn = databaseManager.connect();
//             PreparedStatement ps = conn.prepareStatement(chargerSql)) {
//            ps.setInt(1, stationId);
//            ps.setString(2, charger.getPlugType());
//            ps.setInt(3, charger.getWattage());
//            ps.setString(4, charger.getOperative());
//
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            int insertId = -1;
//            if (rs.next()) {
//                insertId = rs.getInt(1);
//            }
//            return insertId;
//        } catch (SQLException sqlException) {
//            log.error(sqlException);
//            return -1;
//        }
//    }
//
//    @Override
//    public List<Charger> getAll(String sql) {
//        return null;
//    }
//
//    @Override
//    public Charger getOne(int id) {
//        String sql = "SELECT * FROM chargers WHERE stationId == (?)";
//        try (Connection conn = databaseManager.connect();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//
//            rs.next();
//            return stationFromResultSet(rs);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int add(Charger toAdd) throws DuplicateEntryException {
//        return 0;
//    }
//
//    @Override
//    public void delete(int id) {
//
//    }
//
//    @Override
//    public void update(Charger toUpdate) {
//
//    }
//
//    @Override
//    public Station getStation(int stationId) {
//        return null;
//    }
//}
