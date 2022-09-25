package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import seng202.team6.business.JavaScriptBridge;
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

    private StationDao stationDao = new StationDao();
    private MainScreenController controller;
    private double locationLat = 0;
    private double locationLng = 0;
    private String currentAddress;
    private HashMap<Integer, Station> stations;

    /**
     * Initialises the map view.
     * @param stage current stage.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        this.javaScriptBridge = new JavaScriptBridge(this::onStationClicked,
                this::setClickLocation, this::setAddress, this::editStation);
        initMap();
    }

    /**
     * Function to call the edit station pop-up.
     * @param stationId
     */
    public void editStation(String stationId) throws IOException {
        int id = Integer.parseInt(stationId);
        String sql = "SELECT * FROM STATIONS WHERE stationId == " + stationId;
        HashMap<Integer, Station> stationList = stationDao.getAll(sql);
        System.out.println(stationList.isEmpty());
        Station station = stationList.get(id);
        System.out.println(station.getAddress());
        StationController stationController = new StationController(id);
        stationController.setFields(station);
        stationController.loadWindow();
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
     * @param id the id of the station.
     */
    public void onStationClicked(int id) {
        Station station = stations.get(id);
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
        locationLat = lat;
        locationLng = lng;
    }

    /**
     * Function call to get two float value of the latitude and longitude.
     * @return a list with two float value of the latitude and longitude.
     */
    public double[] getLatLng() {
        return new double[]{locationLat, locationLng};
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

                    addStationsToMap(null);

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
     * This calls this add station function, for all the stations that fit the sql query.
     * @param sql Takes a sql query as input.
     */
    public void addStationsToMap(String sql) {

        stations = stationDao.getAll(sql);

        javaScriptConnector.call(
                "cleanUpMarkerLayer");

        for (Station station : stations.values()) {
            addStation(station);
        }

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
     * Function to get the current address.
     * @return string of the current address.
     */
    public String getAddress() {
        return currentAddress;
    }
}