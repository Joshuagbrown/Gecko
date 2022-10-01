package seng202.team6.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MyDetailsToolBarController implements ScreenController {

    private MainScreenController controller;

    /**
     * Initialise the controller.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * Load the register vehicle pop up.
     */
    public void loadRegisterVehicle(ActionEvent actionEvent) {
        try {
            controller.loadRegisterVehicleScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load the saved journeys section.
     */
    public void loadSavedJourneys(ActionEvent actionEvent) {
    }

    /**
     * Load the general section.
     */
    public void loadGeneral(ActionEvent actionEvent) {
    }

}
