package seng202.team6.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A class created to provide the ability to "bridge" from javascript (used within the OSM html)
 * to java.
 *
 */
public class JavaScriptBridge {

    private static final Logger log = LogManager.getLogger();
    private GetStationInterface getStationInterface;
    private GetLocationInterface getLocationInterface;
    private GetAddressInterface getAddressInterface;

    public JavaScriptBridge(GetStationInterface getStationLambda, GetLocationInterface getLocationLambda, GetAddressInterface
                            getAddressLambda) {
        getStationInterface = getStationLambda;
        getLocationInterface = getLocationLambda;
        getAddressInterface = getAddressLambda;
    }

    public void getStationFromClick(int id) {
        getStationInterface.operation(id);
    }

    public void setClickLocation(float lat, float lng) {
        getLocationInterface.operation(lat, lng);
    }

    public void setAddress(String address) {
        getAddressInterface.operation(address);
    }
}











