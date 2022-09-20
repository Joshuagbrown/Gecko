package seng202.team6.business;

/**
 * A class created to provide the ability to "bridge" from javascript (used within the OSM html)
 * to java.
 *
 */
public class JavaScriptBridge {

    private GetStationInterface getStationInterface;
    private GetLocationInterface getLocationInterface;
    private GetAddressInterface getAddressInterface;

    /**
     * Constructor for the javascript bridge.
     * @param getStationLambda is a function in the Map Controller class which allows for the map controller
     *                         class to communicate with the leaflet HTML in javascript to get the currently
     *                         selected station.
     * @param getLocationLambda is a function in the Map Controller class which allows for the map controller
     *                          class to communicate with the leaflet HTML in javascript to get the location
     *                          of the click.
     * @param getAddressLambda is a function in the Map Controller class which allows for the map controller
     *                         class to communicate with the leaflet HTML in javascript to get the address of
     *                         the current clicked location.
     */
    public JavaScriptBridge(GetStationInterface getStationLambda, GetLocationInterface getLocationLambda, GetAddressInterface
                            getAddressLambda) {
        getStationInterface = getStationLambda;
        getLocationInterface = getLocationLambda;
        getAddressInterface = getAddressLambda;
    }

    /**
     * function to get location from the station id.
     * @param id the station id.
     */
    public void getStationFromClick(int id) {
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
    public void setAddress(String address) {
        getAddressInterface.operation(address);
    }
}











