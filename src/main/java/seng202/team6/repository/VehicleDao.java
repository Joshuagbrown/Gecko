package seng202.team6.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
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

    public List<Vehicle> getUserVehicle(int userid) {
        List<Vehicle> vehicles = new ArrayList<>();
        String vehicleSql = "SELECT make,model,plugType,year,userId,vehicleId FROM vehicles WHERE userId = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(3));
                vehicles.add(new Vehicle(rs.getString(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6) ));
            }
            return vehicles;
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

    public List<String> getPlugType() {
        List<String> plugType = new ArrayList<>();
        String makeSql = "SELECT DISTINCT plugType FROM chargers";
        try (Connection conn = databaseManager.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(makeSql);
            while (rs.next()) {
                plugType.add(rs.getString(1));
            }
            return plugType;
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
        String vehicleSql = "DELETE FROM vehicles WHERE  vehicleId = ? ";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql)) {

            ps.setInt(1,todelete.getVehicleId());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error((e.getMessage()));
        }
    }


    @Override
    public void update(Vehicle toUpdate) {
        String vehicleSql = "UPDATE vehicles SET make = ? , "
                + "model = ? , plugtype = ? , year = ? Where vehicleId = ? ";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql);) {

            ps.setString(1, toUpdate.getMake());
            ps.setString(2,toUpdate.getModel());
            ps.setString(3,toUpdate.getPlugType());
            ps.setInt(4,toUpdate.getYear());
            ps.setInt(5,toUpdate.getVehicleId());
            ps.executeUpdate();

        } catch (SQLException e) {
            log.error((e.getMessage()));
        }

    }
}
