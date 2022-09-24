package seng202.team6.models;

public class UserLoginDetails {
    int userId;
    byte[] passwordHash;
    byte[] passwordSalt;

    /**
     *
     * @param userId
     * @param passwordHash
     * @param passwordSalt
     */
    public UserLoginDetails(int userId, byte[] passwordHash, byte[] passwordSalt) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }
}
