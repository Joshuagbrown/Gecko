package seng202.team6.services;

import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import seng202.team6.exceptions.CsvException;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.io.StationCsvImporter;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test CSVImporter implementation
 */
public class StationCsvImporterTest {
    @Test
    @Disabled
    /*
     * Not sure how to create an invalid csv file
     */
    void invalidCsvFileTest() {
        StationCsvImporter csvImporter = new StationCsvImporter();
        assertThrows(CsvFileException.class,
            () -> csvImporter.readFromFile(new File(getClass().getResource("/invalidfile.csv").toURI()), new ArrayList<>()));
    }

    @Test
    void validCsvFileTest() throws URISyntaxException, CsvException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        ObservableList<CsvLineException> warnings = FXCollections.observableArrayList();
        List<Station> stations =  stationCsvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()), warnings);
        assertEquals(4, stations.size());
        assertEquals(0, warnings.size());
    }

    @Test
    void invalidCsvLinesTest() throws URISyntaxException, CsvFileException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<CsvLineException> warnings = new ArrayList<>();
        List<Station> stations = stationCsvImporter.readFromFile(new File(getClass().getResource("/invalidline.csv").toURI()), warnings);
        assertEquals(3, stations.size());
        assertEquals(1, warnings.size());

    }

    @Test
    void validCsvLinesTest() throws URISyntaxException, CsvFileException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        ObservableList<CsvLineException> warnings = FXCollections.observableArrayList();
        List<Station> stations = stationCsvImporter.readFromFile(new File(getClass().getResource("/valid.csv").toURI()), warnings);
        assertEquals(stations.get(0).getCoordinates(), new Position(-43.73745, 170.100913));
        assertEquals("YHA MT COOK", stations.get(0).getName() );
        assertEquals(stations.get(1).getCoordinates(), new Position(-43.59049, 172.630201));
        assertEquals("CHRISTCHURCH ADVENTURE PARK", stations.get(1).getName());
    }
}
