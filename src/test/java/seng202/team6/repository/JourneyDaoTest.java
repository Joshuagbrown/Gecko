package seng202.team6.repository;

import org.junit.jupiter.api.*;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Journey;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyDaoTest {
    static DatabaseManager manager;
    static JourneyDao journeyDao;

    static Journey journey() {
        List<String> addresses = Arrays.asList("15 Alpha Avenue, Strowan 8052, New Zealand",
                "652 Gloucester Street, Linwood 8640, New Zealand");
        return new Journey(addresses, "pdo66");
    }

    static Journey anotherJourney() {
        List<String> addresses = Arrays.asList("54 Bellvue Avenue, Papanui 8052, New Zealand",
                "1/541 Cashel Street, Linwood, Christchurch 8240, New Zealand",
                "16 Rustic Lane, Spreydon 8024, New Zealand");
        return new Journey(addresses, "yamboy1");
    }

    @BeforeAll
    static void setup() throws InstanceAlreadyExistsException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        journeyDao = new JourneyDao();
    }

    @BeforeEach
    void reset() {
        manager.resetDB();
    }

    @Test
    void addTest() throws DatabaseException {
        assertEquals(0, journeyDao.getAll().size());
        int first = journeyDao.add(journey());
        assertEquals(1, journeyDao.getAll().size());
        assertEquals(journey(), journeyDao.getAll().get(first));
    }

    @Test
    void getAllTest() throws DatabaseException {
        assertEquals(0, journeyDao.getAll().size());
        int first = journeyDao.add(journey());
        int second = journeyDao.add(anotherJourney());
        assertEquals(2, journeyDao.getAll().size());
        assertEquals(journey(), journeyDao.getAll().get(first));
        assertEquals(anotherJourney(), journeyDao.getAll().get(second));
    }

    @Test
    @Disabled
    void getOneTest() throws DatabaseException {
        int first = journeyDao.add(journey());
        assertEquals(journey(), journeyDao.getOne(first));
    }
}
