package seng202.team6.exceptions;

/**
 * An exception used for invalid data points.
 * @author Philip Dolbel.
 */
public class CsvLineException extends CsvException implements ImportableLineException {
    int line = 0;

    /**
     * The constructor of the csv line exception.
     * @param e the error that need to throw.
     */
    public CsvLineException(Throwable e) {
        super(e);
    }


    /**
     * Function to get lines.
     * @return the line of the error
     */
    public int getLine() {
        return line;
    }


    /**
     * Function to set line.
     * @param line the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }
}
