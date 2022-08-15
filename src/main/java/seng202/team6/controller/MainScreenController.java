package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    public ScrollPane ScrollPaneMainScreen;
    @FXML
    public BorderPane toolBarPane;

    private Stage stage;

    void init(Stage stage) {
        this.stage = stage;

        LoadMapViewAndToolBars(stage);
        stage.sizeToScene();
    }
    @FXML
    void LoadMapViewAndToolBar(Stage stage) {LoadMapViewAndToolBars(stage);}


    private void LoadMapViewAndToolBars(Stage stage){
        try {
            // Load our sales_table.fxml file
            FXMLLoader mapViewLoader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
            // Get the root FXML element after loading
            Parent mapViewParent = mapViewLoader.load();
            // Get access to the controller the FXML is using
            MapController mapController = mapViewLoader.getController();
            // Initialise the controller
            mapController.init(stage);

            // Set the root of our new component to the center of the borderpane

            ScrollPaneMainScreen.setContent(mapViewParent);

            FXMLLoader mapToolBarLoader = new FXMLLoader(getClass().getResource("/fxml/MapToolBar.fxml"));
            // Get the root FXML element after loading
            Parent mapToolBarParent = mapToolBarLoader.load();
            // Get access to the controller the FXML is using
            MapController mapToolBarController = mapToolBarLoader.getController();
            // Initialise the controller
            mapController.init(stage);

            // Set the root of our new component to the center of the borderpane

            toolBarPane.setCenter(mapToolBarParent);


        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
