package seng202.team6.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Journey.
 */
public class Journey {
    private ArrayList<String> addresses;
    private String username;

    /**
     * Constructor for a new journey object.
     * @param addresses list of addresses in journey
     * @param username username of user that owns this journey
     */
    public Journey(ArrayList addresses, String username) {
        this.addresses = addresses;
        this.username = username;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public String getUsername() {
        return username;
    }

}
