package seng202.team6.business;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import seng202.team6.controller.MapController;


import java.io.IOException;


/**
 * A class created to provide the ability to "bridge" from javascript (used within the OSM html)
 * to java.
 *
 */
public class JavaScriptBridge {

    private static final Logger log = LogManager.getLogger();
    private GetStationInterface getStationInterface;

    public JavaScriptBridge(GetStationInterface getStationLambda) {
        getStationInterface = getStationLambda;
    }
}











