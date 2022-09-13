package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

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
     * Loads the frequently asked questions.
     * @param actionEvent Button clicked.
     * @throws IOException if there is an issue loading fxml file
     */
    public void loadFAQ(ActionEvent actionEvent) throws IOException {

        Pair p = screen.LoadBigScreen(this.stage, "/fxml/FAQ.fxml",controller);

        controller.getMainBorderPane().setCenter((Parent)p.getKey());

    }

    /**
     * Loads the map help screen.
     * @param actionEvent Button pressed.
     * @throws IOException if there is an issue loading fxml file
     */
    public void loadMapHelp(ActionEvent actionEvent) throws IOException {
        Pair p = screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",controller);

        controller.getMainBorderPane().setCenter((Parent)p.getKey());
    }
}
