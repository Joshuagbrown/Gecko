package seng202.team6.models;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Journey.
 */
public class Journey {
    private List<String> addresses;
    private String username;
    private int journeyId = -1;

    /**
     * Constructor for a new journey object with a journey id, i.e. from the database
     * @param addresses list of addresses in journey
     * @param username username of user that owns this journey
     * @param journeyId journeyId from the database
     */
    public Journey(List<String> addresses, String username, int journeyId) {
        this(addresses, username);
        this.journeyId = journeyId;
    }

    /**
     * Constructor for a new journey object.
     * @param addresses list of addresses in journey
     * @param username username of user that owns this journey
     */
    public Journey(List<String> addresses, String username) {
        this.addresses = addresses;
        this.username = username;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public String getUsername() {
        return username;
    }

    public int getJourneyId() {
        return journeyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Journey journey = (Journey) o;
        return addresses.equals(journey.addresses) && username.equals(journey.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addresses, username);
    }
}
