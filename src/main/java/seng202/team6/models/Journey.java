package seng202.team6.models;

import java.util.ArrayList;
import java.util.List;

public class Journey {
    private List<String> journeys;
    private String userID;

    public Journey(ArrayList journeys, String userID) {
        this.journeys = journeys;
        this.userID = userID;
    }

    public List<String> getJourneys() {
        return journeys;
    }

    public String getUserID() {
        return userID;
    }

}
