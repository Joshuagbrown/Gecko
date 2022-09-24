package seng202.team6.services;

import org.junit.jupiter.api.Test;

import seng202.team6.exceptions.CsvException;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.io.StationCsvImporter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test CSVImporter implementation
 */
public class StationCsvImporterTest {
    // TODO: Not sure how to make an invalid csv file
//    @Test
//    public void readFromInvalidCSVFileShouldThrow() {
//        CSVImporter csvImporter = new CSVImporter();
//        assertThrows(CSVFileException.class,
//            () -> csvImporter.readFromFile(new File(getClass().getResource("/invalidfile.csv").toURI())));
//    }

    @Test
    void readFromValidCSVReadsTheCorrectNumberOfStations() throws URISyntaxException, CsvException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<Station> stations =  stationCsvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(4, stations.size());
    }

    @Test
    void invalidCSVLinesShouldBeSkippedWhenReadingFile() throws URISyntaxException, CsvFileException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<Station> stations = stationCsvImporter.readFromFile(new File(getClass().getResource("/invalidline.csv").toURI()));
        assertEquals(3, stations.size());
    }

    @Test
    void validCSVLinesShouldBeParsedProperly() throws URISyntaxException, CsvFileException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<Station> stations = stationCsvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(stations.get(0).getCoordinates(), new Position(-43.73745, 170.100913));
        assertEquals("YHA MT COOK", stations.get(0).getName() );
        assertEquals(stations.get(1).getCoordinates(), new Position(-43.59049, 172.630201));
        assertEquals("CHRISTCHURCH ADVENTURE PARK", stations.get(1).getName());
    }
}
