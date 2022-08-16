package seng202.team6.exceptions;

/**
 * An exception used for invalid data points.
 */
public class CSVLineException extends CSVException {
    public CSVLineException(Throwable e) {
        super(e);
    }
}
