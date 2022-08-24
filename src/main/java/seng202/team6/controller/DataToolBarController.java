package seng202.team6.controller;

import com.sun.tools.javac.Main;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DataToolBarController implements ToolBarController {
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }
}
