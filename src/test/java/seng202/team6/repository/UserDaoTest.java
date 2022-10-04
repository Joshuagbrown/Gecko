package seng202.team6.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    static DatabaseManager manager;
    static UserDao userDao;

    User user() {
        byte[] passwordHash = HexFormat.of().parseHex("C0E64B6AE3A5F1D293E28704712F3663");
        byte[] passwordSalt = HexFormat.of().parseHex("D03FEF3D6CE1AAEDF4255FEDE95DDEA8");

        return new User("user", passwordHash, passwordSalt,
                "54 Bellvue Avenue, Papanui 8052, New Zealand", "Name");
    }

    @BeforeAll
    static void setup() throws InstanceAlreadyExistsException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        userDao = new UserDao();
    }

    @BeforeEach
    void reset() {
        manager.resetDB();
    }

    @Test
    void addTest() throws DatabaseException {
        int first = userDao.add(user());
        assertEquals(user(), userDao.getOne(first));
    }

    @Test
    void addDuplicateTest() throws DatabaseException {
        userDao.add(user());
        assertThrowsExactly(DatabaseException.class,
                () -> userDao.add(user()), "A duplicate entry was found");

    }

    @Test
    void getLoginDetailsTest() throws DatabaseException {
        int first = userDao.add(user());
        UserLoginDetails details = userDao.getLoginDetails(user().getUsername());
        assertEquals(first, details.getUserId());
        assertArrayEquals(user().getPasswordHash(), details.getPasswordHash());
        assertArrayEquals(user().getPasswordSalt(), details.getPasswordSalt());
    }

    @Test
    void getUserInvalidTest() {
        assertNull(userDao.getOne(1));
    }

    @Test
    void getLoginDetailsInvalidTest() {
        assertNull(userDao.getLoginDetails("asdf"));
    }

    @Test
    void updateTest() throws DatabaseException{
        int first = userDao.add(user());
        User user = userDao.getOne(first);
        user.setAddress("16 Rustic Lane, Spreydon 8024, New Zealand");
        user.setName("Bill");
        user.setPasswordHash(HexFormat.of().parseHex("3C2E2226E3F64524481F7325E597788D"));
        userDao.update(user);
        assertEquals(user, userDao.getOne(first));
    }
}
