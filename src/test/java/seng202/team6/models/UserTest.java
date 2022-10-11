package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the User class.
 */
public class UserTest {

    /**
     * Test the equals when true.
     */
    @Test
    public void testEqualsSucceed() {
        byte[] byte1 = new byte[]{0,1,2,3};
        User user1 = new User("Admin",byte1,byte1,"12 Cliffod Pl", "Admin");
        User user2 = new User("Admin",byte1,byte1,"12 Cliffod Pl", "Admin");
        assertTrue(user1.equals(user2));
    }

    /**
     * Test the equals when false.
     */
    @Test
    public void testEqualsFailAddress() {
        byte[] byte1 = new byte[]{0,1,2,3};
        byte[] byte2 = new byte[]{0,1,2,4};
        User user1 = new User("Admin",byte1,byte1,"12 Cliffod Pl", "Admin");
        User user2 = new User("Admin",byte2,byte1,"12 Cliffod Pl", "Admin");
        assertFalse(user1.equals(user2));
    }
}