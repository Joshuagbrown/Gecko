package seng202.team6.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class VehicleDaoTest {
    static DatabaseManager manager;
    static VehicleDao vehicleDao;

    Vehicle vehicle() {
        return new Vehicle("Nissan", "Leaf", "Type 2 CCS", 2020, 1);
    }

    Vehicle anotherVehicle() {
        return new Vehicle("Toyota", "Corolla", "CHAdeMO", 2022, 1);
    }

    Vehicle differentUserVehicle() {
        return new Vehicle("BMW", "i7", "Type 1 CCS", 2021, 2);
    }

    @BeforeAll
    static void setup() throws InstanceAlreadyExistsException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        vehicleDao = new VehicleDao();
    }

    @BeforeEach
    void reset() {
        manager.resetDB();
    }

    @Test
    void addTest() throws DatabaseException {
        int first = vehicleDao.add(vehicle());
        assertEquals(vehicle(), vehicleDao.getUserVehicle(1).get(0));
    }

    @Test
    void getUserVehicleTest() throws DatabaseException {
        vehicleDao.add(vehicle());
        vehicleDao.add(anotherVehicle());
        vehicleDao.add(differentUserVehicle());
        assertEquals(2, vehicleDao.getUserVehicle(1).size());
        assertEquals(1, vehicleDao.getUserVehicle(2).size());
        assertEquals(0, vehicleDao.getUserVehicle(5).size());
    }

    @Test
    void deleteTest() throws DatabaseException {
        int first = vehicleDao.add(vehicle());
        assertEquals(1, vehicleDao.getUserVehicle(1).size());
        vehicleDao.delete(first);
        assertEquals(0, vehicleDao.getUserVehicle(1).size());
    }

    @Test
    void deleteInvalidTest() {
        assertThrowsExactly(DatabaseException.class, () -> vehicleDao.delete(1),
                "An invalid vehicle id was provided");
    }

    @Test
    void updateTest() throws DatabaseException {
        int first = vehicleDao.add(vehicle());
        Vehicle vehicle = vehicleDao.getUserVehicle(1).get(0);
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setYear(2002);
        vehicle.setPlugType("CHAdeMO");
        vehicleDao.update(vehicle);
        assertEquals(vehicle, vehicleDao.getUserVehicle(1).get(0));
    }
}
