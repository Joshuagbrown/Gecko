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
    public BorderPane mainBorderPane;

    private String currentStage = null;

    private Stage stage;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    void init(Stage stage) {

        this.stage = stage;
        //LoadMapViewAndToolBars(stage);
        try {
            loadMapAndToolBar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();
    }
    public Stage getStage() {
        return this.stage;
    }
    public ScrollPane getScrollPane() {
        return this.ScrollPaneMainScreen;
    }
    public BorderPane getToolBarPane() {
        return this.getToolBarPane();
    }

    public BorderPane getMainBorderPane() {return mainBorderPane;}
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
            mapController.init(stage,this);

            // Set the root of our new component to the center of the borderpane

            ScrollPaneMainScreen.setContent(mapViewParent);

            FXMLLoader mapToolBarLoader = new FXMLLoader(getClass().getResource("/fxml/MapToolBar.fxml"));
            // Get the root FXML element after loading
            Parent mapToolBarParent = mapToolBarLoader.load();
            // Get access to the controller the FXML is using
            MapController mapToolBarController = mapToolBarLoader.getController();
            // Initialise the controller
            mapController.init(stage,this);

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
    public void LoadMapViewAndToolBars(ActionEvent actionEvent) throws IOException {

        LoadScreen screen = new LoadScreen();
        mainBorderPane.setCenter(screen.LoadBigScreen(stage,"/fxml/Map.fxml",this));
        screen.LoadToolBar(stage,"/fxml/MapToolBar.fxml",toolBarPane,this);
        currentStage = "Map";
        mainBorderPane.setRight(null);


    }

    /**
     * The action handler that linked to the map button on main screen
     *
     * @param actionEvent Top level container for this window
     */
    public void LoadDataViewAndToolBars(ActionEvent actionEvent) throws IOException {

        LoadScreen screen = new LoadScreen();
        mainBorderPane.setCenter(screen.LoadBigScreen(this.stage,"/fxml/Data.fxml",this));
        screen.LoadToolBar(this.stage,"/fxml/DataToolBar.fxml",toolBarPane,this);
        currentStage = "Data";
        mainBorderPane.setRight(null);
    }

    public void loadHelpScreenAndToolBar(ActionEvent actionEvent) throws IOException {

        if (currentStage == "Map") {
            LoadScreen screen = new LoadScreen();
            mainBorderPane.setRight(screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",this));





        } else if (currentStage == "Data") {

        } else {
            LoadScreen screen = new LoadScreen();
            mainBorderPane.setCenter(screen.LoadBigScreen(stage, "/fxml/Help.fxml",this));
            screen.LoadToolBar(stage, "/fxml/HelpToolBar.fxml", toolBarPane, this);
        }

    }

    public void loadMapAndToolBar() throws IOException {
        LoadScreen screen = new LoadScreen();

        mainBorderPane.setCenter(screen.LoadBigScreen(stage,"/fxml/Map.fxml",this));
        screen.LoadToolBar(stage,"/fxml/MapToolBar.fxml",toolBarPane,this);
    }

    public void loadMyDetail(ActionEvent actionEvent) {
        currentStage = "Detail";
        mainBorderPane.setRight(null);
    }


    public void loadSetting(ActionEvent actionEvent) throws IOException {
        LoadScreen screen = new LoadScreen();

        mainBorderPane.setCenter(screen.LoadBigScreen(stage,"/fxml/Setting.fxml",this));
        screen.LoadToolBar(stage,"/fxml/SettingToolBar.fxml",toolBarPane,this);

    }
}
