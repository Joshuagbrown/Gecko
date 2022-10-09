package seng202.team6.repository;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.io.StationCsvImporter;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the station dao
 */
class StationDaoTest {
    static DatabaseManager manager;
    static StationDao stationDao;

    static Station stationMultipleChargers() {
        List<Charger> chargerList = Arrays.asList(
                new Charger("CHAdeMO", "Operative", 45),
                new Charger("Type 2 CCS", "Not Operative", 10));
        return station(chargerList, 2);
    }

    static Station station() {
        List<Charger> chargerList = new ArrayList<>();
        Charger charger = new Charger("CHAdeMO", "Operative", 45);
        chargerList.add(charger);
        return station(chargerList, 1);
    }

    static Station station(List<Charger> chargerList, int objectId) {
        Position coordinates = new Position(-43, 171);
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
        int first = stationDao.add(station());
        assertEquals(1, stationDao.getAll().size());
        assertEquals(station(), stationDao.getOne(first));
    }

    @Test
    void addDuplicateThrows() throws DatabaseException {
        stationDao.add(station());
        assertThrowsExactly(DatabaseException.class, () -> stationDao.add(station()),
                "A duplicate entry was provided");
    }

    @Test
    void getOneWithMultipleChargers() throws DatabaseException {
        int first = stationDao.add(stationMultipleChargers());
        assertEquals(stationMultipleChargers(), stationDao.getOne(first));
    }

    @Test
    void getOneInvalid() throws DatabaseException {
        int first = stationDao.add(station());
        int second = stationDao.add(stationMultipleChargers());
        // Arbitrary number that is guaranteed to not be either first or second
        assertNull(stationDao.getOne(first + second + 1));
    }

    @Test
    void getAllMultipleStations() throws DatabaseException {
        int first = stationDao.add(station());
        int second = stationDao.add(stationMultipleChargers());
        assertEquals(2, stationDao.getAll().size());
        assertEquals(station(), stationDao.getAll().get(first));
        assertEquals(stationMultipleChargers(), stationDao.getAll().get(second));
    }

    @Test
    void getChargerTypes() throws DatabaseException {
        stationDao.add(station());
        stationDao.add(stationMultipleChargers());
        List<String> chargerTypes = stationDao.getChargerTypes();
        assertEquals(2, stationDao.getChargerTypes().size());
        assertEquals(Arrays.asList("CHAdeMO", "Type 2 CCS"), chargerTypes);
    }

    @Test
    void addCharger() throws DatabaseException {
        int first = stationDao.add(station());
        Station station = stationDao.getOne(first);
        station.addCharger(new Charger("Type 2 CCS", "Not Operative", 10));
        stationDao.update(station);
        assertEquals(station, stationDao.getOne(first));
    }

    @Test
    void updateCharger() throws DatabaseException {
        int first = stationDao.add(station());
        Station station = stationDao.getOne(first);
        station.getChargers().get(0).setOperative("Not Operative");
        stationDao.update(station);
        assertEquals(station, stationDao.getOne(first));
    }

    @Test
    void deleteCharger() throws DatabaseException {
        int first = stationDao.add(station());
        assertEquals(1, stationDao.getAll().size());
        stationDao.delete(first);
        assertEquals(0, stationDao.getAll().size());
    }

    @Test
    void addAllChargers() throws DatabaseException {
        assertEquals(0, stationDao.getAll().size());
        List<Station> stations = Arrays.asList(station(), stationMultipleChargers());
        IntegerProperty value = new SimpleIntegerProperty();
        value.addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        stationDao.addAll(stations, value);
        assertEquals(2, value.getValue());
        assertEquals(2, stationDao.getAll().size());
    }

    @Test
    void addAllChargersFull() throws DatabaseException, URISyntaxException, CsvFileException {
        assertEquals(0, stationDao.getAll().size());
        StationCsvImporter importer = new StationCsvImporter();
        List<Station> stations = importer.readFromFile(new File(getClass().getResource("/full.csv").toURI()), new ArrayList<>());
        IntegerProperty value = new SimpleIntegerProperty();
        value.addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        stationDao.addAll(stations, value);
        assertEquals(341, value.getValue());
        assertEquals(341, stationDao.getAll().size());
    }
}
