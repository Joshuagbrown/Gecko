package seng202.team6.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.services.AlertMessage;

public class MyDetailsToolBarController implements ScreenController {
    private final Logger log = LogManager.getLogger();

    @FXML
    public Button generalButton;
    @FXML
    public Button registerVehicleButton;
    @FXML
    public Button savedJourneyButton;

    private MainScreenController controller;

    private Button currentlySelected;
    private Button prevSelected;

    /**
     * Initialise the controller.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        setSelected(generalButton);
    }


    /**
     * Changes style of new selected button and resets the style of the previously selected
     * button.
     * @param button the new selected button.
     */
    public void setSelected(Button button) {
        if (currentlySelected != null) {
            currentlySelected.setStyle("");
            prevSelected = currentlySelected;
        }
        if (button != null) {
            currentlySelected = button;
            button.setStyle("-fx-border-color: #FFFFFF");
        }

    }


    /**
     * Load the register vehicle pop up.
     */
    public void loadRegisterVehicle(ActionEvent actionEvent) {
        try {
            setSelected(registerVehicleButton);
            controller.loadRegisterVehicleScreen();
        } catch (IOException e) {
            AlertMessage.createMessage("Error", "There was an error loading the register"
                                                + "vehicle screen. Please see the "
                                                + "log for more details.");
            log.error("Error loading the register vehicle screen", e);
        }
        setSelected(prevSelected);
    }

    /**
     * Load the saved journeys section.
     */
    public void loadSavedJourneys() {
        controller.loadJourneysButtonEventHandler();
        setSelected(savedJourneyButton);
    }


    /**
     * Load the general section.
     */
    public void loadGeneral() {
        controller.loadMyDetailsViewAndToolBars();
        setSelected(generalButton);
    }

    /**
     * Logs the user out.
     * @param actionEvent When log out is clicked
     */
    public void logOut(ActionEvent actionEvent) {
        controller.setCurrentUser(null);
        controller.setCurrentUserId(0);
        controller.loadSignUpViewAndToolBars();
        controller.mapButtonEventHandler();
        controller.getMapController().removeHomeAddress();
    }
}
