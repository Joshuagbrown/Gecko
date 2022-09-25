package seng202.team6.business;


import java.io.IOException;

/**
 * Interface for the edit station function, used for javascript bridge
 *
 * Author: Tara Lipscombe
 */
public interface EditStationInterface {

    /**
     * The operation that takes the station Id.
     * @param id
     */
    void operation(String id) throws IOException;

}

