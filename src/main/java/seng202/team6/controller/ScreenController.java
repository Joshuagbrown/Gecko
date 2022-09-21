package seng202.team6.controller;

import javafx.stage.Stage;

/**
 * Interface for all Screens in the app.
 */
public interface ScreenController {
    /**
     * Initializes the Controller.
     *
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    void init(Stage stage, MainScreenController controller);
}
