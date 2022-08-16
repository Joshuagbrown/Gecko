package seng202.team6.exceptions;

/**
 * An exception used for trying to parse an invalid file
 */
public class CSVFileException extends CSVException {
    public CSVFileException(Throwable e) {
        super(e);
    }
}
