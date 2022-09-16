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

    public JavaScriptBridge(GetStationInterface getStationLambda, GetLocationInterface getLocationLambda) {
        getStationInterface = getStationLambda;
        getLocationInterface = getLocationLambda;
    }

    public void getStationFromClick(int id) {
        getStationInterface.operation(id);
    }

    public void getClickLocation(float lat, float lng) {
        getLocationInterface.operation(lat, lng);
    }
    public void getAddress() {
    }
}











