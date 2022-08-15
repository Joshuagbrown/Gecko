package seng202.team6.services.io;

import java.io.File;
import java.util.List;

public interface Importable<T> {
    List<T> readFromFile(File file);
}
