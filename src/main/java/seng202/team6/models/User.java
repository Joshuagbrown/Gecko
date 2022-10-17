package seng202.team6.models;

import java.util.Arrays;
import java.util.Objects;


/**
 * User class.
 */
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

    /**
     * Function to get the users' username.
     * @return a string representing the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Function to get the password hash.
     * @return the hash of the password
     */
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Function to get the password salt.
     * @return the salt password representation
     */
    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Function to get the address of the user.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Function tog et the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set the password hash.
     * @param passwordHash the password hash
     */
    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Function to set address of the user.
     * @param address the user's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Function to set the name of the user.
     * @param name the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to check if this user is equal to a given user.
     * @param o the other user
     * @return true if equal
     */
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

    /**
     * Function to hash the user object.
     * @return the hashed representation
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(username, address, name);
        result = 31 * result + Arrays.hashCode(passwordHash);
        result = 31 * result + Arrays.hashCode(passwordSalt);
        return result;
    }
}
