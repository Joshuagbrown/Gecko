package seng202.team6.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.NotImplementedException;

public class MainScreenController {

    @FXML
    public ScrollPane scrollPaneMainScreen;
    @FXML
    public BorderPane toolBarPane;
    public BorderPane mainBorderPane;

    private String currentStage = null;

    private Stage stage;

    private Parent mapScreen;
    private Parent dataScreen;
    private Parent helpScreen;

    private Parent settingScreen;
    private Parent mapToolBarScreen;
    private Parent dataToolBarScreen;
    private Parent helpToolBarScreen;

    private Parent settingToolBarScreen;



    /**
     * Initialize the window by loading necessary screen and
     * initialize the parent of different screen
     *
     *
     * @param stage Top level container for this window
     */
    void init(Stage stage) {
        LoadScreen screen = new LoadScreen();

        this.stage = stage;
        //LoadMapViewAndToolBars(stage);
        try {
            mapScreen = screen.LoadBigScreen(stage, "/fxml/Map.fxml", this);
            dataScreen = screen.LoadBigScreen(stage, "/fxml/Data.fxml",this);
            helpScreen = screen.LoadBigScreen(stage, "/fxml/Help.fxml",this);
            mapToolBarScreen = screen.LoadToolBar(stage,"/fxml/MapToolBar.fxml", toolBarPane, this);
            dataToolBarScreen = screen.LoadToolBar(this.stage,"/fxml/DataToolBar.fxml", toolBarPane, this);
            helpToolBarScreen = screen.LoadToolBar(stage, "/fxml/HelpToolBar.fxml", toolBarPane, this);
            settingScreen =screen.LoadBigScreen(stage, "/fxml/Setting.fxml", this);
            settingToolBarScreen = screen.LoadToolBar(stage,"/fxml/SettingToolBar.fxml", toolBarPane, this);

            loadMapViewAndToolBars();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();


    }

    public Stage getStage() {
        return this.stage;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPaneMainScreen;
    }

    public BorderPane getToolBarPane() {
        return this.getToolBarPane();
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    //    /**
    //     * Load the map view and the related toolbar when the map button is clicked
    //     *
    //     * @param stage Top level container for this window
    //     */
    //    @FXML
    //    private void LoadMapViewAndToolBars(Stage stage){
    //
    //        try {
    //            // Load our sales_table.fxml file
    //            FXMLLoader mapViewLoader = new FXMLLoader(
    //                    getClass().getResource("/fxml/Map.fxml"));
    //            // Get the root FXML element after loading
    //            Parent mapViewParent = mapViewLoader.load();
    //            // Get access to the controller the FXML is using
    //            MapController mapController = mapViewLoader.getController();
    //            // Initialise the controller
    //            mapController.init(stage,this);
    //
    //            // Set the root of our new component to the center of the borderpane
    //
    //            ScrollPaneMainScreen.setContent(mapViewParent);
    //
    //            FXMLLoader mapToolBarLoader = new FXMLLoader(
    //                    getClass().getResource("/fxml/MapToolBar.fxml"));
    //            // Get the root FXML element after loading
    //            Parent mapToolBarParent = mapToolBarLoader.load();
    //            // Get access to the controller the FXML is using
    //            MapController mapToolBarController = mapToolBarLoader.getController();
    //            // Initialise the controller
    //            mapController.init(stage,this);
    //
    //            // Set the root of our new component to the center of the borderpane
    //
    //            toolBarPane.setCenter(mapToolBarParent);
    //
    //
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //   }


    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadMapViewAndToolBars(ActionEvent actionEvent) throws IOException {


        mainBorderPane.setCenter(mapScreen);
        System.out.println("got here");
        toolBarPane.setCenter(mapToolBarScreen);

        System.out.println("got here too");
        currentStage = "Map";
        mainBorderPane.setRight(null);


    }



    public void loadMapViewAndToolBars() throws IOException {
        loadMapViewAndToolBars(null);

    }

    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadDataViewAndToolBars(ActionEvent actionEvent) throws IOException {

        mainBorderPane.setCenter(dataScreen);
        toolBarPane.setCenter(dataToolBarScreen);
        currentStage = "Data";
        mainBorderPane.setRight(null);
    }

    /**
     * The action handler that linked to the help button on toolbar.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadHelpScreenAndToolBar(ActionEvent actionEvent) throws IOException {

        if (currentStage == "Map") {
            LoadScreen screen = new LoadScreen();
            mainBorderPane.setRight(screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",this));





        } else if (currentStage == "Data") {
            throw new NotImplementedException();
        } else {

            mainBorderPane.setCenter(helpScreen);
            toolBarPane.setCenter(helpToolBarScreen);
        }

    }


    public void loadMyDetail(ActionEvent actionEvent) {
        currentStage = "Detail";
        mainBorderPane.setRight(null);
    }

    /**
     * The action handler that linked to the settings button on toolbar.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadSetting(ActionEvent actionEvent) throws IOException {

        mainBorderPane.setCenter(settingScreen);
        toolBarPane.setCenter(settingToolBarScreen);

    }
}
