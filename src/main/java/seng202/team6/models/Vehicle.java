package seng202.team6.models;

public class Vehicle {

    String make;
    String model;
    String type;
    int year;

    int userId;

    public Vehicle(String make, String model, String type, int year, int userId) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.year = year;
        this.userId = userId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
