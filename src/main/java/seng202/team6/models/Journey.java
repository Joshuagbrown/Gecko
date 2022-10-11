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

    public List<String> getAddresses() {
        return addresses;
    }

    public List<String> getMidPoints() {
        return midPoints;
    }

    public String getUsername() {
        return username;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
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
        return midPoints.equals(journey.midPoints) && username.equals(journey.username)
                && start.equals(journey.start) && end.equals(journey.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, midPoints, username);
    }

}
