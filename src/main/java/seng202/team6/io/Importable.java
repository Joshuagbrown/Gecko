package seng202.team6.io;

import seng202.team6.exceptions.CSVException;

import java.io.File;
import java.util.List;

public interface Importable<T> {
    List<T> readFromFile(File file) throws CSVException;
}
