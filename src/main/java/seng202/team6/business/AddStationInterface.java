package seng202.team6.business;


import java.io.IOException;

/**
 * Interface for the add station function, used for javascript bridge.
 * Author: Tara Lipscombe
 */
public interface AddStationInterface {

    /**
     * The operation that takes the station address.
     * @param address The address for the Station
     */
    void operation(String address) throws IOException, InterruptedException;
}
