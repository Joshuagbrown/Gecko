package seng202.team6.models;

import java.util.List;

public class Station {
    private Position coordinates;
    private int objectId;
    private String name;
    private String operator;
    private String owner;
    private String address;
    private int timeLimit;
    private List<Charger> chargers;
}
