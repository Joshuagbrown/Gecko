package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpToolBarController implements ToolBarController {
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * Loads the frequently asked questions.
     * @param actionEvent Button clicked.
     * @throws IOException if there is an issue loading fxml file
     */
    public void loadFAQ(ActionEvent actionEvent) throws IOException {

        LoadScreen screen = new LoadScreen();
        controller.getMainBorderPane().setCenter(screen.LoadBigScreen(this.stage, "/fxml/FAQ.fxml",controller));



    }

    /**
     * Loads the map help screen.
     * @param actionEvent Button pressed.
     * @throws IOException if there is an issue loading fxml file
     */
    public void loadMapHelp(ActionEvent actionEvent) throws IOException {
        LoadScreen screen = new LoadScreen();
        controller.getMainBorderPane().setCenter(screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",controller));
    }
}
