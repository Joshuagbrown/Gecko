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


                /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/fxml/MainScreen.fxml"));
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root, 300, 275));
                primaryStage.show();*/
            FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
            Parent root = baseLoader.load();
            MainScreenController baseController = baseLoader.getController();
            baseController.init(primaryStage);

            primaryStage.setTitle("ElecTrip App");
            Scene scene = new Scene(root, 600, 400);
            //scene.getStylesheets().add(getClass().getResource("/stylesheets/login.css").toExternalForm());
            // Add a custom application icon
            //primaryStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toExternalForm()));
            primaryStage.setScene(scene);
            primaryStage.show();



            }




    public static void main(String[] args) { launch(args); }
}
