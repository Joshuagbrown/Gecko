package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import seng202.team6.business.JavaScriptBridge;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.services.AlertMessage;


/**
 * Map Controller Class.
 * Based off LeafletOSMViewController from seng202-advanced-fx-public by Morgan English.
 * @author Tara Lipscombe and Lucas Redding
 */
public class MapController implements ScreenController {
    public TextField locationTextBox;
    private Logger log = LogManager.getLogger();
    private JSObject javaScriptConnector;
    private JavaScriptBridge javaScriptBridge;
    @FXML
    private WebView webView;

    private MainScreenController controller;

    private Position position = null;
    private String currentAddress;

    private ObservableMap<Integer, Station> stations;
    private Station currentlySelected;

    private String homeAddress = null;
    private String markerAddress;
    private Position markerPosition;

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
    public void editStation(String stationId) {
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
    public void addStationToDatabase(String address) {
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
     */
    public void loadEditStationWindow(Integer id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        EditStationController editStationController = new EditStationController();
        loader.setController(editStationController);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Current Station");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();
            editStationController.init(stage, scene, controller, id);
        } catch (IOException e) {
            AlertMessage.createMessage("Error", "There was an error loading "
                                                + "the edit station window."
                                                + "See the logs for more detail.");
            log.error("Error loading edit station window", e);
        }
    }


    /**
     * Function to initialize and load the station pop-up.
     * @param address the address of the station
     */
    public void loadAddStationWindow(String address) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        AddStationController addStationController = new AddStationController();
        loader.setController(addStationController);
        JSONObject result = null;
        if (address != null) {
            result = controller.getMapToolBarController().geoCode(address);
            if (result == null) {
                AlertMessage.createMessage("Invalid Address used", "Please place marker"
                        + " in New Zealand and try again.");
                return;
            }
        }
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add a New Station");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();
            addStationController.init(stage, scene, controller, address);
        } catch (IOException e) {
            AlertMessage.createMessage("Error", "There was an error loading"
                    + "the add station window. "
                    + "See the log for more details");
            log.error("Error loading add station window", e);
        }


    }



    /**
     * Function to set the current address.
     * @param address the new address to set.
     */
    public void setAddress(String address) {
        if (address != null) {
            if (address.length() == 0) {
                controller.getMapToolBarController().setAutoFillButtonsOff();
            } else {
                controller.getMapToolBarController().setAutoFillButtonsOn();
            }
        }
        currentAddress = address;

    }

    /**
     * Function that call to display the station information when the station marker is clicked.
     * @param stationId the id of the station.
     */
    public void onStationClicked(Integer stationId) {
        if (stationId.equals(-2)) {
            //Home address marker clicked
            controller.getMapToolBarController().setImagesToHome();
            currentAddress = homeAddress;
            position = getHomePosition();


        } else if (stationId == -1) {
            //Current Location marker clicked
            controller.getMapToolBarController().setImagesToMarker();
            position = markerPosition;

        } else if (stationId == null || stationId == 0) {
            currentlySelected = null;
            setAddress(null);
            controller.setTextAreaInMainScreen("");
            controller.changeToAddButton();
        } else {
            Station station = stations.get(stationId);
            currentlySelected = station;
            setClickLocation(station.getCoordinates().getLatitude(),
                    station.getCoordinates().getLongitude());
            setAddress(station.getAddress());
            controller.setTextAreaInMainScreen(station.toString());
            controller.changeToEditButton();
        }
    }

    /**
     * When a place on the map is clicked this sets the latitude and longitude.
     * @param lat latitude.
     * @param lng longitude
     */
    public void setClickLocation(double lat, double lng) {
        controller.getMapToolBarController().setImagesToMarker();
        position = new Position(lat, lng);
        markerPosition = position;
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


    /**
     * When a user has logged in or signed up, their home address it set, to be used
     * for filtering stations.
     * @param address the home address of the user
     */
    public void setHomeAddress(String address) {
        homeAddress = address;
        String title = "Home: " + address;
        double lat;
        double lon;
        Position pos = findLatLon(address);

        lat = pos.getLatitude();
        lon = pos.getLongitude();
        javaScriptConnector.call("fixAddressMarker", title, lat, lon);
    }


    /**
     * When a user has logged out, remove the address marker from the map.
     */
    public void removeHomeAddress() {
        homeAddress = null;
        javaScriptConnector.call("removeAddressMarker");
    }


    /**
     * Function to return a position representation of the currently set home address.
     * @return the position of the home address
     */
    public Position getHomePosition() {
        if (Objects.equals(homeAddress, "") || homeAddress == null) {
            return null;
        } else {
            return findLatLon(homeAddress);
        }
    }




    /**
     * Finds the corresponding latitude and longitude for the given address.
     * and sets it to the position variable
     */
    private Position findLatLon(String address) {

        JSONObject positionField = controller.getMapToolBarController().geoCode(address);
        if (positionField == null) {
            return null;
        }
        double lat = (double) positionField.get("lat");
        double lng = (double) positionField.get("lng");
        return new Position(lat, lng);

    }


    /**
     * Function called when the user searches for an address on tab at the top of the map.
     * Sets the current location on the map to be the given address.
     * @param actionEvent when the search button is selected
     */
    public void searchLocationOnTheMap(ActionEvent actionEvent) {
        String address = locationTextBox.getText();
        Position location = null;
        location = findLatLon(address);
        if (location == null) {
            AlertMessage.createMessage("Unable to fetch the given Address.",
                    "Please search with a valid Address");
        } else {
            javaScriptConnector.call("findCurrentLocation", address, location.getLatitude(),
                    location.getLongitude());
        }



    }


    /**
     * Deletes Text from text area.
     * @param mouseEvent User clicks on text box
     */
    public void deleteTextFromTestArea(MouseEvent mouseEvent) {
        locationTextBox.setText("");
    }
}