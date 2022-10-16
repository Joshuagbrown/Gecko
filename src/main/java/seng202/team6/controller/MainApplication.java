package seng202.team6.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team6.services.DataService;

/**
 * Main application class that initialize and run the project.
 */
public class MainApplication extends Application {
    private DataService dataService = new DataService();
    private final int preMinWidth = 1200;
    private final int preMinHeight = 800;


    /**
     * Function to start and initialize the application.
     * @param primaryStage the primary stage for the application
     * @throws IOException an IO exception
     * @throws URISyntaxException a syntax exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        dataService.createTables();

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = baseLoader.load();
        MainScreenController baseController = baseLoader.getController();
        baseController.init(primaryStage, dataService);

        primaryStage.setTitle("Gecko");
        Scene scene = new Scene(root, preMinWidth, preMinHeight);
        scene.getStylesheets().add(getClass().getResource(
                "/stylesheets/main.css").toExternalForm());
        primaryStage.setMinWidth(preMinWidth);
        primaryStage.setMinHeight(preMinHeight);
        // Add a custom application icon

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The function that call to launch the project.
     * 
     * @param args the args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
