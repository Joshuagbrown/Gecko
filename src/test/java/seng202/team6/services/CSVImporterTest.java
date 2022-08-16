package seng202.team6.services;

import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.CSVFileException;
import seng202.team6.exceptions.CSVException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.io.CSVImporter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test CSVImporter implementation
 */
public class CSVImporterTest {
    // TODO: Not sure how to make an invalid csv file
//    @Test
//    public void readFromInvalidCSVFileShouldThrow() {
//        CSVImporter csvImporter = new CSVImporter();
//        assertThrows(CSVFileException.class,
//            () -> csvImporter.readFromFile(new File(getClass().getResource("/invalidfile.csv").toURI())));
//    }

    @Test
    public void readFromValidCSVReadsTheCorrectNumberOfStations() throws URISyntaxException, CSVException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations =  csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(4, stations.size());
    }

    @Test
    public void invalidCSVLinesShouldBeSkippedWhenReadingFile() throws URISyntaxException, CSVFileException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/invalidline.csv").toURI()));
        assertEquals(3, stations.size());
    }

    @Test
    public void validCSVLinesShouldBeParsedProperly() throws URISyntaxException, CSVFileException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(stations.get(0), new Station(new Position(1366541.2354, 5153202.1642), "YHA MT COOK"));
        assertEquals(stations.get(1), new Station(new Position(1570148.5238, 5173542.4743), "CHRISTCHURCH ADVENTURE PARK"));
        assertEquals(stations.get(2), new Station(new Position(1822955.3955, 5488854.3202), "PUKAHA NATIONAL WILDLIFE CENTRE"));
    }

}
