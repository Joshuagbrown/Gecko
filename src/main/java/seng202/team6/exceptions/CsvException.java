package seng202.team6.exceptions;

/**
 * Generic CSV exception class.
 * @author Philip Dolbel.
 */
public class CsvException extends Exception {
    /**
     * The constructor of the csv exception
     * @param e the error that need to throw.
     */
    public CsvException(Throwable e) {
        super(e);
    }
}
