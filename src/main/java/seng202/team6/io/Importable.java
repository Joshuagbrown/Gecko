package seng202.team6.io;

import java.io.File;
import java.io.Reader;
import java.util.List;
import seng202.team6.exceptions.CsvException;
import seng202.team6.exceptions.ImportableLineException;


/**
 * Interface for importable and read the file.
 * @author Philip Dolbel.
 */
public interface Importable<T, E extends ImportableLineException> {

    /**
     * read the file and return a list of importable object.
     * @param inputReader the file that want to read.
     * @return list of type T that read.
     * @throws  CsvException the error.
     */
    List<T> readFromFile(Reader inputReader, List<E> errors) throws CsvException;
}
