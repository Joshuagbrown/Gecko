package seng202.team6.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.controller.DataController;

import java.io.IOException;

/**
 * Controller for the main.fxml window
 * @author seng202 teaching team
 */
public class MainController {

    private static final Logger log = LogManager.getLogger();

    @FXML
    public ScrollPane scrollPaneMainScreen;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) throws IOException {
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("/fxml/Data.fxml"));
        Parent tableParent = tableLoader.load();

        DataController tableController = tableLoader.getController();
        tableController.init(stage);

        scrollPaneMainScreen.setContent(tableParent);
    }

}
