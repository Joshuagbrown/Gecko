package seng202.team6.exceptions;

/**
 * An exception used for invalid data points.
 * @author Philip Dolbel.
 */
public class CsvLineException extends CsvException {
    public CsvLineException(Throwable e) {
        super(e);
    }
}
