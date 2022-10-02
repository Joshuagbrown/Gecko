package seng202.team6.controller;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seng202.team6.models.Journey;

public class SaveJourneyController implements ScreenController {

    private MainScreenController controller;
    @FXML
    private TableView<Journey> journeyTable;
    @FXML
    private TableColumn<Journey, ArrayList> locations;
    @FXML
    private TableColumn<Journey, String> userID;

    /**
     * Initialise the window.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }
}
