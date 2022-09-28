package seng202.team6.io;

import java.io.File;
import java.util.List;

import javafx.collections.ObservableList;
import seng202.team6.exceptions.CsvException;
import seng202.team6.exceptions.ImportableLineException;


/**
 * Interface for importable and read the file.
 * @author Philip Dolbel.
 */
public interface Importable<T, E extends ImportableLineException> {

    /**
     * read the file and return a list of importable object.
     * @param  file the file that want to read.
     * @return list of type T that read.
     * @throws  CsvException the error.
     */
    List<T> readFromFile(File file, List<E> errors) throws CsvException;
}
