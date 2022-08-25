package seng202.team6.controller;

import javafx.stage.Stage;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ToolBarController {
    private Stage stage;
    private MainScreenController controller;

    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }
}
