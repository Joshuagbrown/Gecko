package seng202.team6.models;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return username.equals(user.username) && Arrays.equals(passwordHash, user.passwordHash)
                && Arrays.equals(passwordSalt, user.passwordSalt) && address.equals(user.address)
                && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, address, name);
        result = 31 * result + Arrays.hashCode(passwordHash);
        result = 31 * result + Arrays.hashCode(passwordSalt);
        return result;
    }
}
