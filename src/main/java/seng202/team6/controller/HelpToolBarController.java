package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * Controller class for help toolbar screen fxml.
 */
public class HelpToolBarController implements ScreenController {

    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * Calls the showFileLines function with the Map Help text file as input.
     * @param actionEvent Load Map Help button clicked.
     */
    public void loadMapHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream(
                "/TextFiles/MapHelp.txt"));
    }

    /**
     * Calls the showFileLines function with the Data Help text file as input.
     * @param actionEvent Load Data Help button clicked.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream(
                "/TextFiles/DataHelp.txt"));
    }

}
