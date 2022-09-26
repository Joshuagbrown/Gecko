package seng202.team6.exceptions;

/**
 * Wrapper exception class for all database errors.
 */
public class DatabaseException extends Exception {

    /**
     * Main constructor, takes a message and a Throwable.
     * @param message A message to pass through
     * @param e The throwable
     */
    public DatabaseException(String message, Throwable e) {
        super(message, e);
    }
}
