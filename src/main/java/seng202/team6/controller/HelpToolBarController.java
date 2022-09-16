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

    private HelpController helpController = new HelpController();

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
        helpController.showFileLines("C:\\Users\\64211\\Desktop\\Team_6 project\\team-6\\src\\main\\java\\seng202\\team6\\TextFiles\\MapHelp.txt");
    }

    /**
     * Loads the Data help text file.
     * @param actionEvent Button pressed.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        helpController.showFileLines("C:\\Users\\64211\\Desktop\\Team_6 project\\team-6\\src\\main\\java\\seng202\\team6\\TextFiles\\DataHelp.txt");
    }

    /**
     * Loads the My Details help text file.
     * @param actionEvent Button pressed.
     */
    public void loadMyDetailsHelp(ActionEvent actionEvent) {
        helpController.showFileLines("C:\\Users\\64211\\Desktop\\Team_6 project\\team-6\\src\\main\\java\\seng202\\team6\\TextFiles\\MyDetailsHelp.txt");
    }

    /**
     * Loads the Settings help text file.
     * @param actionEvent Button pressed.
     */
    public void loadSettingsHelp(ActionEvent actionEvent) {
        helpController.showFileLines("C:\\Users\\64211\\Desktop\\Team_6 project\\team-6\\src\\main\\java\\seng202\\team6\\TextFiles\\SettingsHelp.txt");
    }
}
