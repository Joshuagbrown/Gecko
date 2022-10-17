package seng202.team6.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Vehicle;
import seng202.team6.services.AlertMessage;


/**
 * Class the controls the information of vehicles in the database.
 */
public class VehicleDao implements DaoInterface<Integer, Vehicle> {

    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    /**
     * Function to get all vehicles from the database.
     * @return a hashmap of integers and vehicles
     */
    @Override
    public Map<Integer, Vehicle> getAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Function to get a vehicle from the database given its id.
     * @param id id of object to get.
     * @return the vehicle
     */
    @Override
    public Vehicle getOne(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the vehicle data of the user from the database with sql query.
     * @param userid the user id of user.
     * @return vehicle list of the user.
     */
    public List<Vehicle> getUserVehicle(int userid) {
        List<Vehicle> vehicles = new ArrayList<>();
        String vehicleSql = "SELECT make,model,plugType,year,userId,vehicleId "
                + "FROM vehicles WHERE userId = ?";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(new Vehicle(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }
            return vehicles;
        } catch (SQLException e) {
            AlertMessage.createMessage("Error", "An error occurred getting vehicles from the "
                                                + "database. Please see the"
                                                + "log for more details.");
            log.error("Error getting vehicles from database", e);
        }
        return new ArrayList<>();
    }

    /**
     * Get the possible plug type of vehicle.
     * @return list of plug type.
     */
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
            AlertMessage.createMessage("Error", "An error occurred getting plugtypes from the "
                                                + "database. Please see the "
                                                + "log for more details.");
            log.error("Error getting plugtypes from database", e);
        }
        return new ArrayList<>();
    }

    /**
     * Add the vehicle to the database.
     * @param toAdd object of type T to add.
     * @return the insert id.
     * @throws DatabaseException the database error.
     */
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


    /**
     * Function called ot delete a vehicle from the database.
     * @param id id of object to delete.
     * @throws DatabaseException a database error
     */
    @Override
    public void delete(int id) throws DatabaseException {
        String vehicleSql = "DELETE FROM vehicles WHERE  vehicleId = ? ";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(vehicleSql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("An error occurred", e);
        }
    }


    /**
     * Function called to update a vehicle in the database.
     * @param toUpdate Object that needs to be updated (this object must be able to
     *                 identify itself and its previous self).
     */
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
