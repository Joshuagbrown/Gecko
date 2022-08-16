package seng202.team6.services.io;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.FileValidationException;
import seng202.team6.exceptions.LineValidationException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter implements Importable<Station> {
    public static final Logger log = LogManager.getLogger();

    @Override
    public List<Station> readFromFile(File file) throws FileValidationException {
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

                } catch (LineValidationException e) {
                    log.warn("Skipping invalid line: " + i + ": " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new FileValidationException(e);
        }

        return stations;
    }

    private Station readStationFromLine(String[] line) throws LineValidationException {
        // Initialise position
        try {
            float xCoord = Float.parseFloat(line[0]);
            float yCoord = Float.parseFloat(line[1]);
            Position coordinates = new Position(xCoord, yCoord);

            String name = line[3];
            return new Station(coordinates, name);
        } catch (NumberFormatException e) {
            throw new LineValidationException(e);
        }
    }
}