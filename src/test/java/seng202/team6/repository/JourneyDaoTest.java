package seng202.team6.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Journey;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyDaoTest {
    static DatabaseManager manager;
    static JourneyDao journeyDao;

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
        List<String> addresses = Arrays.asList("15 Alpha Avenue, Strowan 8052, New Zealand",
                "652 Gloucester Street, Linwood 8640, New Zealand");
        Journey journey = new Journey(addresses, "pdo66");
        int first = journeyDao.add(journey);
        assertEquals(1, journeyDao.getAll().size());
        assertEquals(journey, journeyDao.getAll().get(first));
    }
}
