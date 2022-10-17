package seng202.team6.models;


/**
 * Class for the users login details.
 */
public class UserLoginDetails {
    int userId;
    byte[] passwordHash;
    byte[] passwordSalt;

    /**
     * Main constructor.
     * @param userId the user's id
     * @param passwordHash the user's password hash
     * @param passwordSalt the user's password salt
     */
    public UserLoginDetails(int userId, byte[] passwordHash, byte[] passwordSalt) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    /**
     * Function to get the user ID.
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Function to get the password hash.
     * @return the password hash
     */
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Function to get the password salt.
     * @return the password salt
     */
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }
}
