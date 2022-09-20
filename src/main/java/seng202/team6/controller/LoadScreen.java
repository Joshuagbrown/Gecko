package seng202.team6.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class LoadScreen {

    /**
     *
     * @param stage
     * @param screen
     * @param controller
     * @return
     * @throws IOException
     */
    public Pair<Parent, ScreenController> loadBigScreen(Stage stage, String screen, MainScreenController controller) throws IOException {

        // Load our sales_table.fxml file
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource(screen));
        // Get the root FXML element after loading
        Parent dataViewParent = viewLoader.load();
        // Get access to the controller the FXML is using
        ScreenController screenController = viewLoader.getController();

        // Initialise the controller
        screenController.init(stage, controller);

        // Set the root of our new component to the center of the borderpane
        return new Pair<Parent, ScreenController>(dataViewParent, screenController);


    }


}

