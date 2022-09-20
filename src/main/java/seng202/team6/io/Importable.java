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
     * @return list of type T that read.
     * @param  file the file that want to read.
     * @throws  CsvException the error.
     */
    List<T> readFromFile(File file) throws CsvException;
}
