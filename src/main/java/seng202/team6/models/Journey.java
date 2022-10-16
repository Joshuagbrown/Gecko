package seng202.team6.models;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Journey.
 */
public class Journey {
    private String start;
    private String end;
    private List<String> addresses;
    private List<String> midPoints;
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
        this.start = addresses.get(0);
        this.end = addresses.get(addresses.size() - 1);
        if (addresses.size() > 2) {
            this.midPoints = addresses.subList(1, addresses.size() - 1);
        } else {
            this.midPoints = Arrays.asList();
        }
        this.addresses = addresses;
        this.username = username;
    }

    /**
     * Function that gets the addresses.
     * @return a list of strings representing addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * Function to get the mid-points of the journey.
     * @return a list of strings representing mid-points
     */
    public List<String> getMidPoints() {
        return midPoints;
    }

    /**
     * Function to get the username for the journey.
     * @return the string of the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Function to get the journey ID.
     * @return the journey ID
     */
    public int getJourneyId() {
        return journeyId;
    }

    /**
     * Function to get the start of the journey.
     * @return the start of the journey
     */
    public String getStart() {
        return start;
    }

    /**
     * Function to get the end of the journey.
     * @return the end of the journey
     */
    public String getEnd() {
        return end;
    }

    /**
     * Function to see if another journey is equal to this one.
     * @param o the other journey
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
        Journey journey = (Journey) o;
        return midPoints.equals(journey.midPoints) && username.equals(journey.username)
                && start.equals(journey.start) && end.equals(journey.end);
    }

    /**
     * Function to hash the journey.
     * @return the hashed representation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(start, end, midPoints, username);
    }

}
