package seng202.team6.exceptions;

/**
 * An exception used for trying to parse an invalid file.
 * @author Philip Dolbel.
 */
public class CsvFileException extends CsvException {
    public CsvFileException(Throwable e) {
        super(e);
    }
}
