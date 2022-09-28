package seng202.team6.exceptions;

public interface ImportableLineException {
    /**
     * Get the line this error occurred on.
     */
    int getLine();

    /**
     * Set the line this error occurred on.
     * @param i the line to set
     */
    void setLine(int i);

}
