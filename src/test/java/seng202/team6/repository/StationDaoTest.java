package seng202.team6.repository;

import junit.framework.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the station dao
 */
class StationDaoTest {
    static DatabaseManager manager;
    static StationDao stationDao;

    @BeforeAll
    static void setup() throws InstanceAlreadyExistsException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        stationDao = new StationDao();
    }

    @Test
    void addTest() throws DatabaseException {
        Charger charger = new Charger("CHAdeMO", "Operative", 45);
        List<Charger> chargerList = new ArrayList<>();
        chargerList.add(charger);

        Position coordinates = new Position(-43, 171);
        int objectId = 1;
        String name = "testName";
        String operator = "testOperator";
        String owner = "testOwner";
        String address = "testAddress";
        int timeLimit = 10;
        boolean is24Hours = true;
        int numberOfCarParks = 1;
        boolean carParkCost = true;
        boolean chargingCost = true;
        boolean hasTouristAttraction = true;
        Station toAdd = new Station(coordinates, name, objectId, operator, owner, address,
                timeLimit, is24Hours, chargerList, numberOfCarParks,carParkCost, chargingCost,
                hasTouristAttraction);


        assertEquals(0, stationDao.getAll().size());
        stationDao.add(toAdd);
        assertEquals(1, stationDao.getAll().size());
        assertEquals(toAdd, stationDao.getOne(1));
    }
}
