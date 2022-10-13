package seng202.team6.business;

import java.io.IOException;

/**
 * Interface for get the address.
 */
public interface GetAddressInterface {

    /**
     * the operation that take the address.
     * @param address address of the location.
     */
    void operation(String address) throws IOException;
}
