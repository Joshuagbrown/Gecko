package seng202.team6.controller;

import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MapToolBarController implements ToolBarController {
    private Stage stage;
    private ScrollPane pane;

    @Override
    public void init(Stage stage, ScrollPane pane) {
        this.stage = stage;
        this.pane = pane;
    }
}
