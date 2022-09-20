package seng202.team6.exceptions;

/**
 * An exception used for invalid data points.
 * @author Philip Dolbel.
 */
public class CsvLineException extends CsvException {
    /**
     * The constructor of the csv line exception.
     * @param e the error that need to throw.
     */
    public CsvLineException(Throwable e) {
        super(e);
    }
}
