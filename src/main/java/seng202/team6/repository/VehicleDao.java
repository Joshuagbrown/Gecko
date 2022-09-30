package seng202.team6.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.models.Vehicle;

public class VehicleDao implements DaoInterface<Vehicle> {

    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    @Override
    public Map<Integer, Vehicle> getAll() {
        return null;
    }

    @Override
    public Vehicle getOne(int id) {
        return null;
    }

    public List<String> getMakes() {
        List<String> makes = new ArrayList<>();
        String makeSql = "SELECT DISTINCT make FROM vehicles WHERE userId = -1";
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(makeSql);
            while (rs.next()) {
                makes.add(rs.getString(1));
            }
            return makes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getYear(String make) {
        List<String> makes = new ArrayList<>();
        String makeSql = "SELECT DISTINCT year FROM vehicles WHERE userId = -1 and make = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(makeSql)) {
            ps.setString(1, make);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                makes.add(rs.getString(1));
            }
            return makes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getModel(String make,String year) {
        List<String> makes = new ArrayList<>();
        String makeSql = "SELECT DISTINCT model FROM vehicles WHERE userId = -1 and make = ? and year = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(makeSql)) {
            ps.setString(1, make);
            ps.setString(2,year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                makes.add(rs.getString(1));
            }
            return makes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







    @Override
    public int add(Vehicle toAdd) throws DatabaseException {

        String vehicleSql = "INSERT INTO vehicles (make, model, plugtype, year, userId)"
                + "values(?,?,?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql);) {
            ps.setString(1,toAdd.getMake());
            ps.setString(2,toAdd.getModel());
            ps.setString(3,toAdd.getPlugType());
            ps.setInt(4,toAdd.getYear());
            ps.setInt(5,toAdd.getUserId());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            return insertId;
        } catch (SQLException e) {
            throw new DatabaseException("An error occurred adding a vehicle", e);
        }

    }

    @Override
    public void delete(int id) {

    }

    /**
     * Delete a vehicle from the database.
     * @param todelete the vehicle to delete
     */
    public void deleteVehicle(Vehicle todelete) {
        String vehicleSql = "DELETE FROM vehicles WHERE make = (?) AND "
                + "model = ? AND plugtype = ? AND year = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql)) {

            ps.setString(1, todelete.getMake());
            ps.setString(2,todelete.getModel());
            ps.setString(3,todelete.getPlugType());
            ps.setInt(4,todelete.getYear());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error((e.getMessage()));
        }
    }


    @Override
    public void update(Vehicle toUpdate) {
        String vehicleSql = "UPDATE vehicles SET make = (?) , "
                + "model = ? , plugtype = ? , year = ?";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql);) {

            ps.setString(1, toUpdate.getMake());
            ps.setString(2,toUpdate.getModel());
            ps.setString(3,toUpdate.getPlugType());
            ps.setInt(4,toUpdate.getYear());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error((e.getMessage()));
        }

    }
}
