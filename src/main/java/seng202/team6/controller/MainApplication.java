package seng202.team6.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seng202.team6.gui.MainController;

import java.io.IOException;

public class MainApplication extends Application {
    /*@Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/
    //public class App extends javafx.application.Application {

        /*@Override
        public void start(Stage primaryStage) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
            MainScreenController control = new MainScreenController();
            loader.setController(control);
            //loader.setController(this);
            Stage stage = primaryStage;
            stage.show();"/fxml/MainScreen.fxml"
        }*/
        @Override
        public void start(Stage primaryStage) throws IOException {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root, 300, 275));
                primaryStage.show();
            }catch (Exception e){

        }

    }



    public static void main(String[] args) {
        launch();
    }
}
