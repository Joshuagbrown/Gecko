package seng202.team6.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.io.CSVImporter;
import seng202.team6.models.Station;

import java.io.File;
import java.util.List;

public class DataController {

    private static final Logger log = LogManager.getLogger();

    @FXML
    public TableView<Station> table;

    @FXML
    public TableColumn<Station, String> nameColumn;

    @FXML
    public TableColumn<Station, Double> xColumn;

    @FXML
    public TableColumn<Station, Double> yColumn;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getY()));

        try {
            CSVImporter csvImporter = new CSVImporter();
            List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/full.csv").toURI()));
            table.getItems().addAll(stations);
        } catch (Exception e) {
            log.error(e);
        }

    }

}
