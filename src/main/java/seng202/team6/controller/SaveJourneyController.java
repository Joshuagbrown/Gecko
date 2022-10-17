package seng202.team6.controller;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Journey;
import seng202.team6.services.AlertMessage;


/**
 * Controller class for the save journey screen.
 */
public class SaveJourneyController implements ScreenController {

    private static final Logger log = LogManager.getLogger();
    private MainScreenController controller;
    private Journey selectedJourney;
    private int selectedJourneyIndex;
    private int stopAmount;
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

        midPoints.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());
        startPoint.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());
        endPoint.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());

        startPoint.setCellValueFactory(new PropertyValueFactory<>("start"));
        endPoint.setCellValueFactory(new PropertyValueFactory<>("end"));
        midPoints.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(String.join("\n", cellData.getValue().getMidPoints()
                        .stream()
                        .map(value -> "• " + value)
                        .collect(Collectors.joining("\n")))));

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

    /**
     * This sets the selected journey to what journey has been clicked on.
     * @param mouseEvent Station is clicked.
     */
    public void clickItem(MouseEvent mouseEvent) {
        selectedJourney = journeyTable.getSelectionModel().getSelectedItem();
        selectedJourneyIndex = journeyTable.getSelectionModel().getSelectedIndex();
    }

    /**
     * Deletes the selected journey when the delete journey button is pushed.
     * The selected journey is the journey that is highlighted on the journey table
     * when the delete button is clicked. If no journey is selected an error
     * popup is displayed.
     * @param actionEvent Delete button is clicked event.
     */
    public void deleteJourney(ActionEvent actionEvent) {
        if (selectedJourney != null
                && journeyTable.getSelectionModel().isSelected(selectedJourneyIndex)) {
            controller.getDataService().deleteJourney(selectedJourney);
            journeyTable.getItems().remove(selectedJourneyIndex);
            selectedJourney = null;
            selectedJourneyIndex = -1;
        } else {
            AlertMessage.createMessage("No journey is selected",
                    "Please select a journey and try again");
        }
    }

    /**
     * when show journey is clicked it calls a function in the maptoolbar and goes to the map page.
     * @param actionEvent when show journey is clicked
     */
    public void showJourney(ActionEvent actionEvent) {
        if (selectedJourney != null
                && journeyTable.getSelectionModel().isSelected(selectedJourneyIndex)) {
            if (selectedJourney.getMidPoints() == null) {
                stopAmount = 0;
            } else {
                stopAmount = selectedJourney.getMidPoints().size();
            }
            List<String> allAddresses = selectedJourney.getAddresses();
            controller.getMapToolBarController().findRouteFromJourney(stopAmount, allAddresses);
            controller.mapButtonEventHandler();
        } else {
            AlertMessage.createMessage("No journey is selected",
                    "Please select a journey and try again");
        }
    }
}
