package seng202.team6.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.dialog.ProgressDialog;
import seng202.team6.services.DataService;
import java.io.File;
import java.io.IOException;

public class MainScreenController {

    @FXML
    public BorderPane toolBarPane;
    public BorderPane mainBorderPane;
    public TextArea textAreaInMainScreen;
    public Text geckoTitle;
    private Stage stage;
    public DataService dataService;
    public Parent mapScreen;
    public Parent dataScreen;
    public Parent helpScreen;
    public Parent mapToolBarScreen;
    public Parent dataToolBarScreen;
    public Parent helpToolBarScreen;
    private LoadScreen screen;
    private MapController mapController;
    private DataController dataController;
    private HelpController helpController;
    private MapToolBarController mapToolBarController;
    private DataToolBarController dataToolBarController;

    private HelpToolBarController helpToolBarController;


    /**
     * Initialize the window by loading necessary screen and
     * initialize the parent of different screen.
     *
     * @param stage Top level container for this window.
     * @param dataService Service class to handle accessing and storing the necessary information.
     *
     */
    void init(Stage stage, DataService dataService) {
        Pair pair;

        screen = new LoadScreen();
        this.stage = stage;
        this.dataService = dataService;

        try {
            pair = screen.loadBigScreen(stage, "/fxml/Help.fxml", this);
            helpScreen = (Parent) pair.getKey();
            helpController = (HelpController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/Map.fxml", this);
            mapScreen = (Parent) pair.getKey();
            mapController = (MapController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/Data.fxml", this);
            dataScreen = (Parent) pair.getKey();
            dataController = (DataController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/MapToolBar.fxml", this);
            mapToolBarScreen = (Parent) pair.getKey();
            mapToolBarController = (MapToolBarController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/DataToolBar.fxml", this);
            dataToolBarScreen = (Parent) pair.getKey();
            dataToolBarController = (DataToolBarController) pair.getValue();
            mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);

            pair = screen.loadBigScreen(stage, "/fxml/HelpToolBar.fxml", this);
            helpToolBarScreen = (Parent) pair.getKey();
            helpToolBarController = (HelpToolBarController) pair.getValue();

            loadMapViewAndToolBars();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();
    }

    /**
     * Function to return the map controller.
     * @return mapController.
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Funtion to return the stage.
     * @return stage of main screen controller.
     */
    public Stage getStage() {
        return this.stage;
    }

    public DataService getDataService() {
        return dataService;
    }

    public HelpController getHelpController() {
        return this.helpController;
    }

    public DataController getDataController() {
        return dataController;
    }

    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window.
     */
    public void loadMapViewAndToolBars(ActionEvent actionEvent) {

        textAreaInMainScreen.setText("");
        mainBorderPane.setCenter(mapScreen);
        toolBarPane.setCenter(mapToolBarScreen);
        mainBorderPane.setRight(null);
        mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);
    }

    /**
     * This loads the map view and toolbars.
     */
    public void loadMapViewAndToolBars() {
        loadMapViewAndToolBars(null);
    }

    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window.
     */
    public void loadDataViewAndToolBars(ActionEvent actionEvent) {

        mainBorderPane.setCenter(dataScreen);
        toolBarPane.setCenter(dataToolBarScreen);
        mainBorderPane.setRight(null);
    }

    /**
     * The action handler that linked to the help button on toolbar.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadHelpScreenAndToolBar(ActionEvent actionEvent) {
        mainBorderPane.setCenter(helpScreen);
        toolBarPane.setCenter(helpToolBarScreen);
    }

    public void setTextAreaInMainScreen(String info) {
        textAreaInMainScreen.setText(info);
    }

    /**
     * This function imports the data from a selected file.
     */
    public void importData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Data from CSV file");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call()  {
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
}