package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.repository.StationDao;

import java.io.IOException;

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
     * Loads the Map help text file.
     * @param actionEvent Button clicked.
     */
    public void loadMapHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/MapHelp.txt"));
    }

    /**
     * Loads the Data help text file.
     * @param actionEvent Button pressed.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/DataHelp.txt"));
    }

    /**
     * Loads the My Details help text file.
     * @param actionEvent Button pressed.
     */
    public void loadMyDetailsHelp(ActionEvent actionEvent) {
        System.out.println("my details");
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/MyDetailsHelp.txt"));
    }

    /**
     * Loads the Settings help text file.
     * @param actionEvent Button pressed.
     */
    public void loadSettingsHelp(ActionEvent actionEvent) {
        controller.getHelpController().showFileLines(getClass().getResourceAsStream("/TextFiles/SettingsHelp.txt"));
    }
}
