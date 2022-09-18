



package seng202.team6.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.dialog.ProgressDialog;
import seng202.team6.services.DataService;

import java.io.File;
import java.io.IOException;

public class MainScreenController {

    @FXML
    public ScrollPane scrollPaneMainScreen;
    @FXML
    public BorderPane toolBarPane;
    public BorderPane mainBorderPane;
    public TextArea textAreaInMainScreen;
    public Text geckoTitle;

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
    private HelpController helpController;
    private MapToolBarController mapToolBarController;
    private DataToolBarController dataToolBarController;
    private HelpToolBarController helpToolBarController;
    private SettingController settingController;
    private SettingToolBarController settingToolBarController;

    private Pair p;


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
        try {

            p = screen.LoadBigScreen(stage, "/fxml/Help.fxml",this);
            helpScreen = (Parent) p.getKey();
            helpController= (HelpController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/Map.fxml",this);
            mapScreen = (Parent) p.getKey();
            mapController= (MapController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/Data.fxml",this);
            dataScreen = (Parent) p.getKey();
            dataController= (DataController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/MapToolBar.fxml",this);
            mapToolBarScreen = (Parent) p.getKey();
            mapToolBarController= (MapToolBarController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/DataToolBar.fxml",this);
            dataToolBarScreen = (Parent) p.getKey();
            dataToolBarController= (DataToolBarController) p.getValue();
            mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);

            p = screen.LoadBigScreen(stage, "/fxml/HelpToolBar.fxml",this);
            helpToolBarScreen = (Parent) p.getKey();
            helpToolBarController= (HelpToolBarController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/Setting.fxml", this);
            settingScreen = (Parent) p.getKey();
            settingController= (SettingController) p.getValue();

            p = screen.LoadBigScreen(stage, "/fxml/SettingToolBar.fxml", this);
            settingToolBarScreen = (Parent) p.getKey();
            settingToolBarController= (SettingToolBarController) p.getValue();


            loadFilterSectionOnMapToolBar();


            loadMapViewAndToolBars();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();



    }

    private void loadFilterSectionOnMapToolBar() {
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


        textAreaInMainScreen.setText("");

        mainBorderPane.setCenter(mapScreen);
        toolBarPane.setCenter(mapToolBarScreen);
        currentStage = "Map";
        mainBorderPane.setRight(null);
        mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);


    }
    public void setTextAreaInMainScreen(String info){
        textAreaInMainScreen.setText(info);
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


        mainBorderPane.setCenter(helpScreen);
        toolBarPane.setCenter(helpToolBarScreen);


    }
    public DataController getDataController() {
        return dataController;
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

    public void importData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Data from CSV file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    dataService.loadDataFromCsv(selectedFile);
                    return null;
                }
            };
            ProgressDialog dialog = new ProgressDialog(task);
            dialog.setContentText("Loading data from CSV file...");
            dialog.setTitle("Loading data");
            new Thread(task).start();
            dialog.showAndWait();
            mapController.getJavaScriptConnector().call("cleanUpMarkerLayer");
            mapController.addStationsToMap(null);
            dataController.loadData(null);
        }
    }

    public HelpController getHelpController() {
        return this.helpController;
    }
}
