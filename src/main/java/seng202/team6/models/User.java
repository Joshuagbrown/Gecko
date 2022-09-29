package seng202.team6.models;

public class User {
    String username;
    byte[] passwordHash;
    byte[] passwordSalt;
    String address;
    String name;

    /**
     * Constructor of the user class.
     * @param username the username of the user
     * @param passwordHash the password hash of the user
     * @param passwordSalt the password salt of the user
     * @param address the home address of the user
     * @param name the name of the user
     */
    public User(String username, byte[] passwordHash, byte[] passwordSalt,
                String address, String name) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.address = address;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
}
