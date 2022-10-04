package seng202.team6.controller;

import java.util.stream.Collectors;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Journey;
import seng202.team6.models.Station;

public class SaveJourneyController implements ScreenController {

    private static final Logger log = LogManager.getLogger();
    private MainScreenController controller;
    @FXML
    private TableView<Journey> journeyTable;
    @FXML
    private TableColumn<Journey, String> startPoint;
    @FXML
    private TableColumn<Journey, String> midPoints;
    @FXML
    private TableColumn<Journey, String> endPoint;


    /**
     * Initialise the window.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        loadData();
    }

    /**
     * Loads the data into the table.
     */
    public void loadData() {
        journeyTable.getItems().clear();

        midPoints.setCellFactory(stationStringTableColumn -> new MultiLineCell());

        startPoint.setCellValueFactory(new PropertyValueFactory<>("start"));
        endPoint.setCellValueFactory(new PropertyValueFactory<>("end"));
        midPoints.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAddresses()
                        .stream()
                        .collect(Collectors.joining("\n"))));

        controller.getJourneys().addListener((MapChangeListener<Integer, Journey>) change -> {
            if (change.wasAdded()) {
                journeyTable.getItems().add(change.getValueAdded());
            }
            if (change.wasRemoved()) {
                journeyTable.getItems().remove(change.getValueRemoved());
            }
        });

        journeyTable.getItems().addAll(controller.getJourneys().values());

    }
}
