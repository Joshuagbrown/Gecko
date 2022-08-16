package seng202.team6.services;

import org.junit.jupiter.api.Test;
import seng202.team6.exceptions.ValidationException;
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
    @Test
    public void readFromInvalidCSVShouldThrow() {
        CSVImporter csvImporter = new CSVImporter();
        assertThrows(ValidationException.class,
            () ->csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI())));
    }

    @Test
    public void readFromValidCSVReadsTheCorrectNumberOfStations() throws URISyntaxException, ValidationException {
        CSVImporter csvImporter = new CSVImporter();
        List<Station> stations =  csvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()));
        assertEquals(4, stations.size());
    }

}
