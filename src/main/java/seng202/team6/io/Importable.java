package seng202.team6.io;

import java.io.File;
import java.util.List;
import seng202.team6.exceptions.CsvException;


public interface Importable<T> {
    List<T> readFromFile(File file) throws CsvException;
}
