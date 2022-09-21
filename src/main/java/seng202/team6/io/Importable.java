package seng202.team6.io;

import java.io.File;
import java.util.List;
import seng202.team6.exceptions.CsvException;


/**
 * Interface for importable and read the file.
 * @author Philip Dolbel.
 */
public interface Importable<T> {

    /**
     * read the file and return a list of importable object.
     * @param  file the file that want to read.
     * @return list of type T that read.
     * @throws  CsvException the error.
     */
    List<T> readFromFile(File file) throws CsvException;
}
