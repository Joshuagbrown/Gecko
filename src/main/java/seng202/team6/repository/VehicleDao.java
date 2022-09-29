package seng202.team6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
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
