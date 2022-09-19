package seng202.team6.controller;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.business.JavaScriptBridge;
import seng202.team6.models.Station;
import seng202.team6.repository.StationDao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Map Controller Class.
 * Based off LeafletOSMViewController from seng202-advanced-fx-public by Morgan English
 * @author Tara Lipscombe and Lucas Redding
 */
public class MapController implements ScreenController {

    private static final Logger log = LogManager.getLogger();
    private JSObject javaScriptConnector;
    private JavaScriptBridge javaScriptBridge;
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private StationDao stationDao = new StationDao();
    private Stage stage;

    @FXML
    private TextField newStationTitle;

    @FXML
    private TextField newStationLatitude;

    @FXML
    private TextField newStationLongitude;

    @FXML
    private TextField startLocation;

    @FXML
    private TextField endLocation;

    @FXML
    private Button newStationButton;
    private MainScreenController controller;
    private float locationLat = 0;
    private float locationLng = 0;
    private String currentAddress;

    /**
     * Initialises the map view.
     * @param stage current stage
     */
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
        this.javaScriptBridge = new JavaScriptBridge(this::onStationClicked, this::setClickLocation, this::setAddress);
        initMap();
    }

    public void setAddress(String address) {
        currentAddress = address;
    }

    public void onStationClicked(int id) {
        Station station = controller.getDataService().getStationById(id);
        controller.setTextAreaInMainScreen(station.toString());
    }

    public void setClickLocation(float lat, float lng) {
        locationLat = lat;
        locationLng = lng;

    }

    public float[] getLatLng() {
        float[] latLng = new float[]{locationLat, locationLng};
        return latLng;
    }


    /**
     * Initialises the map by loading the html into the webengine
     * Also sets up a logger to log all console.logs from js at info level
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.loadContent(getHtml());
        // Forwards console.log() output from any javascript to info log
        WebConsoleListener.setDefaultListener((view, message, lineNumber, sourceId) ->
                log.info(String.format("Map WebView console log line: %d, message : %s", lineNumber, message)));

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
     * @return String of html file
     */
    private String getHtml() {
        InputStream is = getClass().getResourceAsStream("/html/leaflet_osm_map.html");
        return new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public JSObject getJavaScriptConnector() {
        return javaScriptConnector;
    }

    public void addStationsToMap(String sql) {

        List<Station> stations = stationDao.getAll(sql);

        javaScriptConnector.call(
                "cleanUpMarkerLayer");

        for (Station station : stations) {
            addStation(station);
        }

    }


    public void addStation(Station station) {
        javaScriptConnector.call(
                "addMarker", station.getName(), station.getCoordinates().getFirst(),
                station.getCoordinates().getSecond(), station.getObjectId());
    }


    public void deleteStation(Station station) {

    }

    public JavaScriptBridge getJavaScriptBridge() {
        return javaScriptBridge;
    }

    /**
     * Action handler method for add new station to map button.
     *
     * @param actionEvent button pressed event
     */
//    public void addNewStation(Station actionEvent) {
//        String stationTitle = newStationTitle.getText();
//        Double latitude = Double.parseDouble(newStationLatitude.getText());
//        Double longitude = Double.parseDouble(newStationLongitude.getText());
//
//        Position pos = new Position(latitude, longitude);
//        Station newStation = new Station(pos, stationTitle);
//
//        addStation(newStation);
//    }

    public String getAddress() {
        return currentAddress;
    }

}
