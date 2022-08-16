package seng202.team6.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.CSVFileException;
import seng202.team6.io.CSVImporter;
import seng202.team6.models.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.awt.SystemColor.text;

public class TableController {

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
