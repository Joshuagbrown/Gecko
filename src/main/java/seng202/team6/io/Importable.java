package seng202.team6.io;

import seng202.team6.exceptions.CsvException;

import java.io.File;
import java.util.List;


/**
 * Interface for importable and read the file
 * @author Philip Dolbel
 */
public interface Importable<T> {

    /**
     * read the file and return a list of importable object.
     * @param file the name of file to be read.
     * @return list of type T that read.
     */
    List<T> readFromFile(File file) throws CsvException;
}
