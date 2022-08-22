package seng202.team6.controller;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MapToolBarController implements ToolBarController {
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }
}
