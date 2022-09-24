package seng202.team6.models;

public class User {
    String username;
    byte[] passwordHash;
    byte[] passwordSalt;
    String address;
    String name;


    public User(String username, byte[] passwordHash, byte[] passwordSalt, String address, String name) {
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
