package seng202.team6.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team6.services.DataService;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Main application class that initialize and run the project.
 */
public class MainApplication extends Application {
    private DataService dataService = new DataService();

    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        dataService.createTables();
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = baseLoader.load();
        MainScreenController baseController = baseLoader.getController();
        baseController.init(primaryStage, dataService);

        primaryStage.setTitle("Gecko");
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/stylesheets/main.css").toExternalForm());

        // Add a custom application icon

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The function that call to launch the project.
     * @param args the args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
