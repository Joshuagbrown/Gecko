package seng202.team6.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.io.CsvImporter;
import seng202.team6.models.Station;

import java.io.File;
import java.util.List;

public class DataController implements ScreenController{

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
    public void init(Stage stage,MainScreenController controller) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getFirst()));
        yColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getSecond()));

        try {
            CsvImporter csvImporter = new CsvImporter();
            List<Station> stations = csvImporter.readFromFile(new File(getClass().getResource("/full.csv").toURI()));
            table.getItems().addAll(stations);
        } catch (Exception e) {
            log.error(e);
        }

    }

}
