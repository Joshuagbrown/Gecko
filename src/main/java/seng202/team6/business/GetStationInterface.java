package seng202.team6.business;

/**
 * Interface for getting the location of the station from the javascript bridge.
 */
public interface GetStationInterface {
    /**
     * operation take the charging station id.
     * @param id charging station id.
     */
    void operation(int id);
}
