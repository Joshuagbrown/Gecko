package seng202.team6.exceptions;

/**
 * An exception used for trying to parse an invalid file.
 * @author Philip Dolbel.
 */
public class CsvFileException extends CsvException {
    /**
     * The constructor of the csv file exception
     * @param e the error that need to throw.
     */
    public CsvFileException(Throwable e) {
        super(e);
    }
}
