package seng202.team6.controller;

import javafx.stage.Stage;

public class DataToolBarController implements ScreenController {
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }
}
