package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.lang3.NotImplementedException;
import seng202.team6.services.DataService;

import java.io.IOException;

public class MainScreenController {

    @FXML
    public ScrollPane scrollPaneMainScreen;
    @FXML
    public BorderPane toolBarPane;
    public BorderPane mainBorderPane;
    public TextArea showInfo;

    private String currentStage = null;

    private Stage stage;

    private DataService dataService;

    private Parent mapScreen;
    private Parent dataScreen;
    private Parent helpScreen;

    private Parent settingScreen;
    private Parent mapToolBarScreen;
    private Parent dataToolBarScreen;
    private Parent helpToolBarScreen;

    private Parent settingToolBarScreen;



    private LoadScreen screen = new LoadScreen();

    private MapController mapController;

    private DataController dataController;


    /**
     * Initialize the window by loading necessary screen and
     * initialize the parent of different screen
     *
     *
     * @param stage Top level container for this window
     */
    void init(Stage stage, DataService dataService) {

        this.stage = stage;
        this.dataService = dataService;
        //LoadMapViewAndToolBars(stage);
        try {
            loadMapScreen();
            loadDataScreen();

            //mapScreen = screen.LoadBigScreen(stage, "/fxml/Map.fxml", this);
            //dataScreen = screen.LoadBigScreen(stage, "/fxml/Data.fxml",this);
            Pair p = screen.LoadBigScreen(stage, "/fxml/Help.fxml",this);
            helpScreen = (Parent) p.getKey();

            mapToolBarScreen = screen.LoadToolBar(stage,"/fxml/MapToolBar.fxml", toolBarPane, this);
            dataToolBarScreen = screen.LoadToolBar(stage,"/fxml/DataToolBar.fxml", toolBarPane, this);
            helpToolBarScreen = screen.LoadToolBar(stage, "/fxml/HelpToolBar.fxml", toolBarPane, this);
            p = screen.LoadBigScreen(stage, "/fxml/Setting.fxml", this);
            settingScreen = (Parent) p.getKey();
            settingToolBarScreen = screen.LoadToolBar(stage,"/fxml/SettingToolBar.fxml", toolBarPane, this);

            loadMapViewAndToolBars();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();


    }

    public void loadMapScreen() throws IOException {

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/fxml/Map.fxml"));
        // Get the root FXML element after loading
        mapScreen = viewLoader.load();
        // Get access to the controller the FXML is using
        ScreenController newScreenController = viewLoader.getController();

        // Initialise the controller
        newScreenController.init(stage, this);
        //
        mapController = (MapController) newScreenController;
    }
    public void loadDataScreen() throws IOException {

        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/fxml/Data.fxml"));
        // Get the root FXML element after loading
        dataScreen = viewLoader.load();
        // Get access to the controller the FXML is using
        ScreenController newScreenController = viewLoader.getController();

        // Initialise the controller
        newScreenController.init(stage, this);
        //
        dataController = (DataController) newScreenController;
    }


    public MapController getMapController() { return mapController;}


    public Stage getStage() {
        return this.stage;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPaneMainScreen;
    }

    public BorderPane getToolBarPane() {
        return this.toolBarPane;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public DataService getDataService() {
        return dataService;
    }



    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadMapViewAndToolBars(ActionEvent actionEvent) throws IOException {


        showInfo.setText("");

        mainBorderPane.setCenter(mapScreen);
        System.out.println("got here");
        toolBarPane.setCenter(mapToolBarScreen);

        System.out.println("got here too");
        currentStage = "Map";
        mainBorderPane.setRight(null);


    }
    public void setInfo(String info){
        showInfo.setText(info);
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
            Pair p = screen.LoadBigScreen(stage, "/fxml/MapHelpScreen.fxml",this);
            mainBorderPane.setRight((Parent) p.getKey());





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
