package seng202.team6.controller;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Map Controller Class
 * Based off LeafletOSMViewController from seng202-advanced-fx-public by Morgan English
 * @author Tara Lipscombe and Lucas Redding
 */
public class MapController {

    private static final Logger log = LogManager.getLogger();
    private JSObject javaScriptConnector;
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private Stage stage;

    /**
     * Initialises the map view
     * @param stage current stage
     */
    public void init(Stage stage) {
        this.stage = stage;
        initMap();
        stage.sizeToScene();
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
                        //window.setMember("javaScriptBridge", javaScriptBridge);
                        // get a reference to the js object that has a reference to the js methods we need to use in java
                        javaScriptConnector = (JSObject) webEngine.executeScript("jsConnector");

                        javaScriptConnector.call("initMap");

                        addStationsToMap();
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


    private void addStationsToMap() {
        Position firstPos = new Position(-43.557139, 172.680089);
        Station firstStation = new Station(firstPos, "The Tannery");

        Position secondPos = new Position(-43.539238, 172.607516);
        Station secondStation = new Station(secondPos, "Tower Junction");

        Position thirdPos = new Position(-43.5531026851514, 172.556282579727);
        Station thirdStation = new Station(thirdPos, "SILKY OTTER CINEMA");

        Position fourthPos = new Position(-43.53300448, 172.6418037);
        Station fourthStation = new Station(fourthPos, "LES MILLS CHRISTCHURCH");

        List<Station> stations = new ArrayList<Station>();
        stations.add(firstStation);
        stations.add(secondStation);
        stations.add(thirdStation);
        stations.add(fourthStation);

        for (Station station : stations) {
            addStation(station);
        }
    }


    private void addStation(Station station) {
        javaScriptConnector.call("addMarker", station.getName(), station.getCoordinates().getX(), station.getCoordinates().getY());
    }












}
