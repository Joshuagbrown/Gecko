package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    public ScrollPane ScrollPaneMainScreen;
    @FXML
    public BorderPane toolBarPane;

    private Stage stage;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    void init(Stage stage) {

        this.stage = stage;
        LoadMapViewAndToolBars(stage);
        stage.sizeToScene();
    }

    /**
     * Load the map view and the related toolbar when the map button is clicked
     *
     * @param stage Top level container for this window
     */
    @FXML
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

    /**
     * Load the data view and the related toolbar when the map button is clicked
     *
     * @param stage Top level container for this window
     */
    @FXML
    private void LoadDataViewAndToolBars(Stage stage){
        try {
            // Load our sales_table.fxml file
            FXMLLoader dataViewLoader = new FXMLLoader(getClass().getResource("/fxml/Data.fxml"));
            // Get the root FXML element after loading
            Parent dataViewParent = dataViewLoader.load();
            // Get access to the controller the FXML is using
            DataController dataController = dataViewLoader.getController();
            // Initialise the controller
            dataController.init(stage);

            // Set the root of our new component to the center of the borderpane

            ScrollPaneMainScreen.setContent(dataViewParent);


            FXMLLoader mapToolBarLoader = new FXMLLoader(getClass().getResource("/fxml/DataToolBar.fxml"));
            // Get the root FXML element after loading
            Parent mapToolBarParent = mapToolBarLoader.load();
            // Get access to the controller the FXML is using
            MapController mapToolBarController = mapToolBarLoader.getController();
            // Initialise the controller
            dataController.init(stage);

            // Set the root of our new component to the center of the borderpane

            toolBarPane.setCenter(mapToolBarParent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The action handler that linked to the map button on main screen
     *
     * @param actionEvent Top level container for this window
     */
    public void LoadMapViewAndToolBars(ActionEvent actionEvent) {
        LoadMapViewAndToolBars(stage);
    }

    /**
     * The action handler that linked to the map button on main screen
     *
     * @param actionEvent Top level container for this window
     */
    public void LoadDataViewAndToolBars(ActionEvent actionEvent) {
        LoadDataViewAndToolBars(stage);
    }
}
