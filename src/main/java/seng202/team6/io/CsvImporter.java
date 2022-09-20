package seng202.team6.io;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Station importer from CSV files.
 * Based off SaleCSVImporter from seng202-advanced-fx-public by Morgan English.
 *
 * @author Philip Dolbel
 *
 * @version 1.0
 */
public class CsvImporter implements Importable<Station> {

    //A Logger object is used to log messages for a specific system
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
                    log.warn(String.format("Skipping invalid line: " , i , ": " , e));
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
            int objectId = Integer.parseInt(line[2]);
            String operator = line[4];
            String owner = line[5];
            String address = line[6];
            boolean is24Hours = Boolean.parseBoolean(line[7]);
            int carparkCount = Integer.parseInt(line[8]);
            boolean carparkCost = Boolean.parseBoolean(line[9]);
            int maxTimeLimit;
            try {
                maxTimeLimit = Integer.parseInt(line[10]);
            } catch (NumberFormatException e) {
                maxTimeLimit = 0;
            }
            boolean hasTouristAttraction = Boolean.parseBoolean(line[11]);

            double latitude = Double.parseDouble(line[12]);
            double longitude = Double.parseDouble(line[13]);
            Position coordinates = new Position(latitude, longitude);

            List<Charger> chargers = parseConnectorsList(line[17]);
            boolean hasChargingCost = Boolean.parseBoolean(line[18]);

            return new Station(coordinates, name, objectId, operator, owner, address,
                    maxTimeLimit, is24Hours, chargers, carparkCount, carparkCost, hasChargingCost,
                    hasTouristAttraction);
        } catch (NumberFormatException e) {
            throw new CsvLineException(e);
        }
    }

    static List<Charger> parseConnectorsList(String field) throws CsvLineException {
        int index = 0;
        List<Charger> chargers = new ArrayList<Charger>();
        while ((index = field.indexOf('{', index)) != -1) {
            int closingBracket = field.indexOf('}', index);
            if (closingBracket == -1) {
                throw new CsvLineException(new Exception("Invalid connectors list entry"));
            }
            try {
                String[] chargerInfo;
                    chargerInfo = field.substring(index + 1, closingBracket).trim().split(",");
                String plugType = chargerInfo[2];
                String operative = chargerInfo[3].trim().split(" ")[1];
                int wattage = Integer.parseInt(chargerInfo[1].trim().split(" ")[0]);
                chargers.add(new Charger(plugType, operative, wattage));
                index = closingBracket;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new CsvLineException(new Exception("Invalid connectors list entry"));
            }
        }
        return chargers;
    }
}