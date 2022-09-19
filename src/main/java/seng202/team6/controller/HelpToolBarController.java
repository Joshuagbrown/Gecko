package seng202.team6.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.repository.StationDao;


public class HelpToolBarController implements ScreenController {

    private Stage stage;
    private MainScreenController controller;

    LoadScreen screen = new LoadScreen();

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
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/MapHelp.txt"));
    }

    /**
     * Calls the showFileLines function with the Data Help text file as input.
     * @param actionEvent Load Data Help button clicked.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/DataHelp.txt"));
    }

    /**
     * Calls the showFileLines function with the My Details Help text file as input.
     * @param actionEvent Load My Details Help button clicked.
     */
    public void loadMyDetailsHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/MyDetailsHelp.txt"));
    }

    /**
     * Calls the showFileLines function with the Settings Help text file as input.
     * @param actionEvent Load Settings Help button clicked.
     */
    public void loadSettingsHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/SettingsHelp.txt"));
    }
}
