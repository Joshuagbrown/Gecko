package seng202.team6.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the station dao
 */
class StationDaoTest {
    static DatabaseManager manager;
    static StationDao stationDao;

    static Station getStation() {
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
        return new Station(coordinates, name, objectId, operator, owner, address,
                timeLimit, is24Hours, chargerList, numberOfCarParks,carParkCost, chargingCost,
                hasTouristAttraction);
    }

    @BeforeAll
    static void setup() throws InstanceAlreadyExistsException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        stationDao = new StationDao();
    }

    @BeforeEach
    void reset() {
        manager.resetDB();
    }

    @Test
    void addTest() throws DatabaseException {
        assertEquals(0, stationDao.getAll().size());
        stationDao.add(getStation());
        assertEquals(1, stationDao.getAll().size());
        assertEquals(getStation(), stationDao.getOne(1));
    }

    @Test
    void addDuplicateThrows() throws DatabaseException {
        stationDao.add(getStation());
        assertThrowsExactly(DatabaseException.class, () -> stationDao.add(getStation()));
    }
}
