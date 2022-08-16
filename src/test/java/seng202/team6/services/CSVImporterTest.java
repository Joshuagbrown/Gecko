package seng202.team6.services;

import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.FileValidationException;
import seng202.team6.exceptions.ValidationException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.services.io.CSVImporter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test CSVImporter
 * @author Philip Dolbel
 */
public class CSVImporterTest {
    // TODO: Not sure how to make an invalid csv file
//    @Test
//    public void readFromInvalidCSVFileShouldThrow() {
//        CSVImporter csvImporter = new CSVImporter();
//        assertThrows(FileValidationException.class,
//            () -> csvImporter.readFromFile(new File(getClass().getResource("/invalidfile.csv").toURI())));
//    }

    @Test
    public void readFromValidCSVReadsTheCorrectNumberOfStations() throws URISyntaxException, ValidationException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations =  csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(4, stations.size());
    }

    @Test
    public void invalidCSVLinesShouldBeSkippedWhenReadingFile() throws URISyntaxException, FileValidationException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/invalidline.csv").toURI()));
        assertEquals(3, stations.size());
    }

    @Test
    public void validCSVLineShouldBeParsedProperly() throws URISyntaxException, FileValidationException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(stations.get(0), new Station(new Position(1366541.2354, 5153202.1642), "YHA MT COOK"));
    }

}
