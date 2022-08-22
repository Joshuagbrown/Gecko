package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

import javax.tools.Tool;
import java.io.IOException;

public class HelpToolBarController implements ToolBarController {
    private Stage stage;
    private ScrollPane pane;

    @Override
    public void init(Stage stage, ScrollPane pane) {
        this.stage = stage;
        this.pane = pane;
    }

    public void loadFAQ(ActionEvent actionEvent) throws IOException {

        LoadScreen screen = new LoadScreen();
        screen.LoadBigScreen(stage,"/fxml/FAQ.fxml",pane);

    }



}
