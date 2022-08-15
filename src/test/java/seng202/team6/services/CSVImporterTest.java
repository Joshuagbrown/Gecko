package seng202.team6.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import seng202.team6.services.io.CSVImporter;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Test CSVImporter implementation
 * @author Philip Dolbel
 */
public class CSVImporterTest {
    @Test
    public void testReadFromValidFileDoesNotError() throws URISyntaxException {
        CSVImporter importer = new CSVImporter();
        File file = new File(getClass().getResource("/validCSVTest.csv").toURI());
        assertDoesNotThrow(() -> importer.readFromFile(file));
    }
}
