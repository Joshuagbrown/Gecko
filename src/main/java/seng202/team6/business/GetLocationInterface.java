package seng202.team6.business;

/**
 * Interface for getting the location from the javascript bridge.
 * @author Tara
 */
public interface GetLocationInterface {

    /**
     * operation which take the longitude and latitude of a location
     * @param lat  latitude of a location.
     * @param lng longitude of a location.
     */
    void operation(float lat, float lng);

}
