package seng202.team6.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team6.controller.MapController;

import java.io.IOException;

/**
 * Class starts the javaFX application window
 * @author seng202 teaching team
 */
public class MainWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/Data.fxml
     * @param primaryStage The current fxml stage, handled by javaFX Application class
     * @throws IOException if there is an issue loading fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
        Parent root = baseLoader.load();

        MapController baseController = baseLoader.getController();
        baseController.init(primaryStage,null);

        primaryStage.setTitle("Revolt App");
        Scene scene = new Scene(root, 580, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the FXML application, this must be called from another class (in this cass App.java) otherwise JavaFX
     * errors out and does not run
     * @param args command line arguments
     */
    public static void main(String [] args) {
        launch(args);
    }

}
