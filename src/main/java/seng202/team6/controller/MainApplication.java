package seng202.team6.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng202.team6.gui.MainController;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.services.DataService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class MainApplication extends Application {
    private DataService dataService = new DataService();

        @Override
        public void start(Stage primaryStage) throws IOException, URISyntaxException {
            dataService.loadDataFromCsv(new File(getClass().getResource("/full.csv").toURI()));
            FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
            Parent root = baseLoader.load();
            MainScreenController baseController = baseLoader.getController();
            baseController.init(primaryStage);

            primaryStage.setTitle("ElecTrip App");
            Scene scene = new Scene(root, 1200, 800);

            // Add a custom application icon

            primaryStage.setScene(scene);
            primaryStage.show();



            }




    public static void main(String[] args) { launch(args); }
}
