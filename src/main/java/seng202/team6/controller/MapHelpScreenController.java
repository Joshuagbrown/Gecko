package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MapHelpScreenController implements ScreenController {
    private Stage stage;
    MainScreenController controller;

    @Override
    public void init(Stage stage,MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void exitScreen(ActionEvent actionEvent) {
        controller.getMainBorderPane().setRight(null);

    }
}
