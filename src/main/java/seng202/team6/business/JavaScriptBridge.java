package seng202.team6.business;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * A class created to provide the ability to "bridge" from javascript (used within the OSM html)
 * to java.
 * Author: Tara Lipscombe and Philip Dolbel
 */
public class JavaScriptBridge {

    private IntConsumer getStationInterface;
    private BiConsumer<Float, Float> getLocationInterface;
    private Consumer<String> getAddressInterface;
    private Consumer<String> editStationInterface;
    private Consumer<String> addStationInterface;

    /**
     * Constructor for the javascript bridge.
     *
     * @param getStationLambda  is a function in the Map Controller class which allows for the
     *                          map controller class to communicate with the leaflet HTML in
     *                          javascript to get the currently selected station.
     * @param getLocationLambda is a function in the Map Controller class which allows for
     *                          the map controller class to communicate with the leaflet HTML
     *                          in javascript to get the location of the click.
     * @param getAddressLambda  is a function in the Map Controller class which allows for the
     *                          map controller class to communicate with the leaflet HTML in
     *                          javascript to get the address of the current clicked location.
     * @param editStationLambda is a function in the Map Controller class which allows for the
     *                          map controller class to communicate with the leaflet HTML in
     *                          javascript to initiate the pop-up to edit the station
     */
    public JavaScriptBridge(IntConsumer getStationLambda,
                            BiConsumer<Float, Float> getLocationLambda,
                            Consumer<String> getAddressLambda,
                            Consumer<String> editStationLambda,
                            Consumer<String> addStationLambda) {
        getStationInterface = getStationLambda;
        getLocationInterface = getLocationLambda;
        getAddressInterface = getAddressLambda;
        editStationInterface = editStationLambda;
        addStationInterface = addStationLambda;
    }

    /**
     * Function to get initiate the edit station pop-up.
     * @param id the stationID of the station
     */
    public void editStation(String id) {
        editStationInterface.accept(id);
    }

    /**
     * function to get location from the station id.
     * @param id the station id.
     */
    public void getStationFromClick(int id) {
        getStationInterface.accept(id);
    }

    /**
     * function to get the location from longitude and latitude.
     * @param lat latitude of the location.
     * @param lng longitude of the locaiton.
     */
    public void setClickLocation(float lat, float lng) {
        getLocationInterface.accept(lat, lng);
    }

    /**
     * function to get the location form address.
     * @param address the address.
     */
    public void setAddress(String address) {
        getAddressInterface.accept(address);
    }

    /**
     * Function to add a new station from marker.
     * @param address the address of marker
     */
    public void addStation(String address) {
        addStationInterface.accept(address);
    }
}











