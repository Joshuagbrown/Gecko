package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import seng202.team6.business.JavaScriptBridge;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.repository.StationDao;



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

    /**
     * Initialises the map view.
     * @param stage current stage.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        this.javaScriptBridge = new JavaScriptBridge(this::onStationClicked,
                this::setClickLocation, this::setAddress, this::editStation);
        initMap();
        this.stations = controller.getStations();
    }

    /**
     * Function to call the edit station pop-up.
     * @param stationId the stationId of the station
     */
    public void editStation(String stationId) throws IOException {
        loadStationWindow(Integer.parseInt(stationId));
    }

    /**
     * Function to initialize and load the station pop-up.
     * @param id the station id number
     * @throws IOException exception thrown
     */
    public void loadStationWindow(int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Current Station");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        StationController stationController = loader.getController();
        stationController.init(stage, controller, id);
    }

    /**
     * Function to set the current address.
     * @param address the new address to set.
     */
    public void setAddress(String address) {
        currentAddress = address;
    }

    /**
     * Function that cll to display the station information when the station marker is clicked.
     * @param stationId the id of the station.
     */
    public void onStationClicked(int stationId) {
        Station station = stations.get(stationId);
        setClickLocation(station.getCoordinates().getLatitude(),
                        station.getCoordinates().getLongitude());
        setAddress(station.getAddress());
        controller.setTextAreaInMainScreen(station.toString());
    }

    /**
     * When a place on the map is clicked this sets the latitude and longitude.
     * @param lat latitude.
     * @param lng longitude
     */
    public void setClickLocation(double lat, double lng) {
        position = new Position(lat, lng);
    }

    /**
     * Function call to get two float value of the latitude and longitude.
     * @return a list with two float value of the latitude and longitude.
     */
    public Position getLatLng() {
        return position;
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
                                removeStation(change.getValueRemoved().getObjectId());
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
     * Adds a single station to the map.
     * @param station A station.
     */
    public void addStation(Station station) {
        javaScriptConnector.call(
                "addMarker", station.getName(), station.getCoordinates().getLatitude(),
                station.getCoordinates().getLongitude(), station.getObjectId());
    }

    /**
     * Remove a station from the map.
     * @param objectId the objectId of the station
     */
    public void removeStation(int objectId) {
        javaScriptConnector.call("removeMarker", objectId);
    }


    /**
     * Function to get the current address.
     * @return string of the current address.
     */
    public String getAddress() {
        return currentAddress;
    }
}