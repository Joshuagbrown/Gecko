package seng202.team6.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller for the main.fxml window
 * @author seng202 teaching team
 */
public class MainController {

    private static final Logger log = LogManager.getLogger();

    @FXML
    private Label defaultLabel;

    @FXML
    private Button defaultButton;


    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {
    }

}
