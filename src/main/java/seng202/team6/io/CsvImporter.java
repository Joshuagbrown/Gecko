package seng202.team6.io;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.CsvLineException;

public abstract class CsvImporter<T> implements Importable<T, CsvLineException> {
    /**
     * A Logger object is used to log messages for a specific system.
     */
    public static final Logger log = LogManager.getLogger();

    /**
     * Read Values from csv file.
     *
     * @param file File to read from.
     * @return List of values from csv file.
     * @throws CsvFileException if there was an error in reading the file.
     */
    @Override
    public List<T> readFromFile(File file,
                                List<CsvLineException> errors) throws CsvFileException {
        List<T> values = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);
            int i = 0;
            String[] line;
            while ((line = reader.readNext()) != null) {
                i++;
                readLine(values, line, i, errors);
            }
        } catch (IOException | CsvValidationException e) {
            throw new CsvFileException(e);
        }
        return values;
    }

    protected int parseIntOrZero(String toParse) {
        int value;
        try {
            value = Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    private void readLine(List<T> values, String[] line, int i,
                          List<CsvLineException> errors) {
        try {
            T value = readValueFromLine(line);
            values.add(value);
        } catch (CsvLineException e) {
            e.setLine(i);
            errors.add(e);
        }
    }

    protected abstract T readValueFromLine(String[] line) throws CsvLineException;
}
