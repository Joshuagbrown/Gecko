package seng202.team6.controller;

import javafx.stage.Stage;

public class HelpController implements ScreenController {
    private Stage stage;

    @Override
    public void init(Stage stage,MainScreenController controller) {
        this.stage = stage;
    }
}
