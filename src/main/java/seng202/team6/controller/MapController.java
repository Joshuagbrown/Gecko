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


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;



public class MapController implements ScreenController{

    private static final Logger log = LogManager.getLogger();
    private JSObject javaScriptConnector;

    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private Stage stage;

    /**
     * Initialise the map view
     * @param stage current stage
     */
    public void init(Stage stage,MainScreenController controller) {
        this.stage = stage;
        initMap();
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










}
