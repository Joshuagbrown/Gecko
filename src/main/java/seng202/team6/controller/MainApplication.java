package seng202.team6.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng202.team6.gui.MainController;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {



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
