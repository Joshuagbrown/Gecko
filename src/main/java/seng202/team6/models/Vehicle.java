package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a vehicle in the system.
 */
public class Vehicle {

    private int vehicleId = -1;
    String make;
    String model;
    String plugType;
    int year;

    int userId;

    /**

     * Constructor class, takes in vehicle information.
     * @param make The vehicle make, or brand
     * @param model The model of the vehicle
     * @param plugType The type of charger on the vehicle
     * @param year The year the vehicle was produced
     * @param userId The user that owns the vehicle
     */
    public Vehicle(String make, String model, String plugType, int year, int userId) {
        this.make = make;
        this.model = model;
        this.plugType = plugType;
        this.year = year;
        this.userId = userId;
    }

    /**

     * Constructor class, takes in vehicle information.
     * @param make The vehicle make, or brand.
     * @param model The model of the vehicle.
     * @param plugType The type of charger on the vehicle.
     * @param year The year the vehicle was produced.
     * @param userId The user that owns the vehicle.
     * @param vehicleId the id of the vehicle from the database.
     */
    public Vehicle(String make, String model, String plugType, int year, int userId,int vehicleId) {
        this.make = make;
        this.model = model;
        this.plugType = plugType;
        this.year = year;
        this.userId = userId;
        this.vehicleId = vehicleId;
    }

    /**
     * Function to get the vehicle id.
     * @return the vehicle id
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * Function to get te make of the vehicle.
     * @return the make of the vehicle
     */
    public String getMake() {
        return make;
    }

    /**
     * Function to set the make of the vehicle.
     * @param make the new make of the vehicle
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Function to get the model of the vehicle.
     * @return model the model of the vehicle
     */
    public String getModel() {
        return model;
    }

    /**
     * Function to set the model.
     * @param model the new model of the vehicle.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Function to get the plugtype of the vehicle.
     * @return the vehicle plugtype
     */
    public String getPlugType() {
        return plugType;
    }

    /**
     * Function to set the vehicle plug type.
     * @param plugType the new plugtype
     */
    public void setPlugType(String plugType) {
        this.plugType = plugType;
    }

    /**
     * Function to get the year of the vehicle.
     * @return the year of the vehicle
     */
    public int getYear() {
        return year;
    }

    /**
     * Function to set the year of the vehicle.
     * @param year the new vehicle year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Function to get the user id.
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Function to check if another vehicle is equal to this one.
     * @param o the other vehicle
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return year == vehicle.year && userId == vehicle.userId && make.equals(vehicle.make)
                && model.equals(vehicle.model) && plugType.equals(vehicle.plugType);
    }

    /**
     * Function to hash the vehicle.
     * @return the hashed representation of the vehicle
     */
    @Override
    public int hashCode() {
        return Objects.hash(make, model, plugType, year, userId);
    }

    /**
     * Overwrite the string info of the vehicle.
     *
     * @return the string info of the vehicle.
     */
    public String toString() {
        String info = String.format("Make : %s\nYear : %s\nModel : %s\n"
                + "Charger Type : %s",make,year,model,plugType);
        return info;

    }
}
