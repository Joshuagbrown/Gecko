package seng202.team6.controller;


import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
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

    public void loadFAQ(ActionEvent actionEvent) throws IOException {

        LoadScreen screen = new LoadScreen();
        controller.getMainBorderPane().setCenter(screen.LoadBigScreen(this.stage, "/fxml/FAQ.fxml",controller));



    }

    public void loadMapHelp(ActionEvent actionEvent) throws IOException {
        LoadScreen screen = new LoadScreen();
        controller.getMainBorderPane().setCenter(screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",controller));
    }
}
