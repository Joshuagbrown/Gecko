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
import seng202.team6.models.Position;
import seng202.team6.models.Station;


/**
 * Station importer from CSV files.
 * Based off SaleCSVImporter from seng202-advanced-fx-public by Morgan English.
 *
 * @author Philip Dolbel
 *
 * @version 1.0
 */
public class CsvImporter implements Importable<Station> {
    public static final Logger log = LogManager.getLogger();

    /**
     * Read Stations from csv file.
     *
     * @param file File to read from
     * @return List of stations from csv file
     * @throws CsvFileException if there was an error in reading the file
     */
    @Override
    public List<Station> readFromFile(File file) throws CsvFileException {
        ArrayList<Station> stations = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);
            int i = 0;
            String[] line;
            while ((line = reader.readNext()) != null) {
                i++;
                try {
                    Station station = readStationFromLine(line);
                    stations.add(station);
                } catch (CsvLineException e) {
                    log.warn("Skipping invalid line: " + i + ": " + e);
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new CsvFileException(e);
        }
        return stations;
    }

    /**
     * Helper method to read a single station from a line.
     *
     * @param line Current line of csv file to read
     * @return Station parsed from the line
     * @throws CsvLineException if there was an error with an individual data point
     */
    private Station readStationFromLine(String[] line) throws CsvLineException {
        try {
            String name = line[3];
            double latitude = Double.parseDouble(line[12]);
            double longitude = Double.parseDouble(line[13]);
            Position coordinates = new Position(latitude, longitude);
            return new Station(coordinates, name);
        } catch (NumberFormatException e) {
            throw new CsvLineException(e);
        }
    }
}