package seng202.team6.business;

import java.io.IOException;

/**
 * A class created to provide the ability to "bridge" from javascript (used within the OSM html)
 * to java.
 * Author: Tara Lipscombe and Philip Dolbel
 */
public class JavaScriptBridge {

    private GetStationInterface getStationInterface;
    private GetLocationInterface getLocationInterface;
    private GetAddressInterface getAddressInterface;
    private EditStationInterface editStationInterface;

    private AddStationInterface addStationInterface;

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
    public JavaScriptBridge(GetStationInterface getStationLambda, GetLocationInterface
            getLocationLambda, GetAddressInterface getAddressLambda,
                            EditStationInterface editStationLambda,
                            AddStationInterface addStationLambda) {
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
    public void editStation(String id) throws IOException, InterruptedException {
        editStationInterface.operation(id);
    }

    /**
     * function to get location from the station id.
     * @param id the station id.
     */
    public void getStationFromClick(int id) throws IOException {
        getStationInterface.operation(id);
    }

    /**
     * function to get the location from longitude and latitude.
     * @param lat latitude of the location.
     * @param lng longitude of the locaiton.
     */
    public void setClickLocation(float lat, float lng) {
        getLocationInterface.operation(lat, lng);
    }

    /**
     * function to get the location form address.
     * @param address the address.
     */
    public void setAddress(String address) throws IOException {
        getAddressInterface.operation(address);
    }

    /**
     * Function to add a new station from marker.
     * @param address the address of marker
     * @throws IOException error from pop-up
     * @throws InterruptedException error from pop-up
     */
    public void addStation(String address) throws IOException, InterruptedException {
        addStationInterface.operation(address);
    }
}











