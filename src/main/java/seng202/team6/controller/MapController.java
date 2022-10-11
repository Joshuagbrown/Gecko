package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import seng202.team6.business.JavaScriptBridge;
import seng202.team6.models.Position;
import seng202.team6.models.Station;


/**
 * Map Controller Class.
 * Based off LeafletOSMViewController from seng202-advanced-fx-public by Morgan English.
 * @author Tara Lipscombe and Lucas Redding
 */
public class MapController implements ScreenController {
    private JSObject javaScriptConnector;
    private JavaScriptBridge javaScriptBridge;
    @FXML
    private WebView webView;

    private MainScreenController controller;

    private Position position = null;
    private String currentAddress;

    private ObservableMap<Integer, Station> stations;
    private Station currentlySelected;

    /**
     * Initialises the map view.
     * @param stage current stage.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        this.javaScriptBridge = new JavaScriptBridge(this::onStationClicked,
                this::setClickLocation, this::setAddress, this::editStation,
                this::addStationToDatabase);
        initMap();
        this.stations = controller.getStations();

    }

    /**
     * Function to call the edit station pop-up.
     * @param stationId the stationId of the station
     */
    public void editStation(String stationId) throws IOException, InterruptedException {
        if (controller.getCurrentUserId() == 0) {
            Alert alert = AlertMessage.noAccess();
            ButtonType button = alert.getButtonTypes().get(0);
            ButtonType result = alert.showAndWait().orElse(button);

            if (button.equals(result)) {
                controller.loginButtonEventHandler(null);
            }

        } else {
            loadEditStationWindow(Integer.parseInt(stationId));
        }
    }

    /**
     * Function to call the add station pop-up.
     * @param address the string of the address
     */
    public void addStationToDatabase(String address) throws IOException, InterruptedException {
        if (controller.getCurrentUserId() == 0 || controller.getCurrentUserId() == -1) {
            Alert alert = AlertMessage.noAccess();
            ButtonType button = alert.getButtonTypes().get(0);
            ButtonType result = alert.showAndWait().orElse(button);

            if (button.equals(result)) {
                controller.loginButtonEventHandler(null);
            }
        } else {
            loadAddStationWindow(address);
        }
    }

    /**
     * Adds a single station to the map.
     * @param station A station.
     */
    public void addStation(Station station) {
        javaScriptConnector.call(
                "addMarker", station.getName(), station.getCoordinates().getLatitude(),
                station.getCoordinates().getLongitude(), station.getStationId());
    }

    /**
     * Function to initialize and load the station pop-up.
     * @param id the station id number
     * @throws IOException exception thrown
     */
    public void loadEditStationWindow(Integer id) throws IOException,
            InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        EditStationController editStationController = new EditStationController();
        loader.setController(editStationController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Current Station");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        editStationController.init(stage, scene, controller, id);
    }


    /**
     * Function to initialize and load thew station pop-up.
     * @param address the address of the station
     */
    public void loadAddStationWindow(String address) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        AddStationController addStationController = new AddStationController();
        loader.setController(addStationController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add a New Station");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        addStationController.init(stage, scene, controller, address);

    }



    /**
     * Function to set the current address, if address is given, else, indicate a
     * progress bar.
     * @param address the new address to set.
     */
    public void setAddress(String address) throws IOException {

        currentAddress = address;

    }

    /**
     * Function that cll to display the station information when the station marker is clicked.
     * @param stationId the id of the station.
     */
    public void onStationClicked(int stationId) throws IOException {
        Station station = stations.get(stationId);
        currentlySelected = station;
        setClickLocation(station.getCoordinates().getLatitude(),
                        station.getCoordinates().getLongitude());
        setAddress(station.getAddress());
        controller.setTextAreaInMainScreen(station.toString());
        controller.changeToEditButton();
    }

    /**
     * When a place on the map is clicked this sets the latitude and longitude.
     * @param lat latitude.
     * @param lng longitude
     */
    public void setClickLocation(double lat, double lng) {
        position = new Position(lat, lng);
        controller.changeToAddButton();
    }

    /**
     * Function call to get two float value of the latitude and longitude.
     * @return a list with two float value of the latitude and longitude.
     */
    public Position getLatLng() {
        return position;
    }

    /**
     * Function to return the currently selected station in the map.
     * @return the currently select station
     */
    public Station getCurrentlySelected() {
        return currentlySelected;
    }


    /**
     * Initialises the map by loading the html into the webengine.
     * Also sets up a logger to log all console.logs from js at info level.
     */
    private void initMap() {
        WebEngine webEngine;
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.loadContent(getHtml());

        webEngine.getLoadWorker().stateProperty().addListener(
            (ov, oldState, newState) -> {
                // if javascript loads successfully
                if (newState == Worker.State.SUCCEEDED) {
                    // set our bridge object
                    JSObject window = (JSObject) webEngine.executeScript("window");

                    window.setMember("javaScriptBridge", javaScriptBridge);
                    // get a reference to the js object that has a reference
                    // to the js methods we need to use in java
                    javaScriptConnector = (JSObject) webEngine.executeScript("jsConnector");

                    javaScriptConnector.call("initMap");

                    controller.getStations().addListener((MapChangeListener<Integer, Station>)
                            change -> {
                            if (change.wasAdded()) {
                                addStation(change.getValueAdded());
                            }

                            if (change.wasRemoved()) {
                                removeStation(change.getValueRemoved().getStationId());
                            }
                        });

                    stations.forEach((integer, station) -> addStation(station));

                }
            });
    }

    /**
     * Function to load map html file into string.
     * @return String of html file.
     */
    private String getHtml() {
        InputStream is = getClass().getResourceAsStream("/html/leaflet_osm_map.html");
        assert is != null;
        return new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    /**
     * Function call to get the javascript connector.
     * @return javascript connector.
     */
    public JSObject getJavaScriptConnector() {
        return javaScriptConnector;
    }


    /**
     * Remove a station from the map.
     * @param stationId the objectId of the station
     */
    public void removeStation(int stationId) {
        javaScriptConnector.call("removeMarker", stationId);
    }


    /**
     * Function to get the current address.
     * @return string of the current address.
     */
    public String getAddress() {
        return currentAddress;
    }
}