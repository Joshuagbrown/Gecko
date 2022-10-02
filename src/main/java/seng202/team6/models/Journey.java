package seng202.team6.models;

import java.util.ArrayList;
import java.util.List;

public class Journey {
    private ArrayList<String> addresses;
    private String username;

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
