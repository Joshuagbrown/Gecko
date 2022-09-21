package seng202.team6.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The class is to help loading different screen and assign the related controller.
 * @author  Phyu Wai Lwin.
 */
public class LoadScreen {

    /**
     * Funtion is used to load the related screen to the main border pane of the main screen.
     *
     * @param stage the stage .
     * @param screen the string location of the screen that need to load.
     * @param controller the main controller class.
     * @return the parent and screen controller pair that the parent can display on the screen and
     *     controller can save for further use.
     * @throws IOException may throw the io exception.
     */
    public Pair<Parent, ScreenController> loadBigScreen(Stage stage, String screen,
                        MainScreenController controller) throws IOException {

        // Load our sales_table.fxml file
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource(screen));
        // Get the root FXML element after loading
        Parent dataViewParent = viewLoader.load();
        // Get access to the controller the FXML is using
        ScreenController screenController = viewLoader.getController();

        // Initialise the controller
        screenController.init(stage, controller);

        // Set the root of our new component to the center of the borderpane
        return new Pair<>(dataViewParent, screenController);


    }


}

