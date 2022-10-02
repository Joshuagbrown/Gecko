package seng202.team6.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng202.team6.models.Journey;
import seng202.team6.models.Position;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ScreenController {
    /**
     * Border pane that hold the filter section on map toolbar.
     */
    @FXML
    public BorderPane filterSectionOnMapToolBar;
    /**
     * The grid pane that hold the find routes texts.
     */
    @FXML
    public GridPane planTripGridPane;
    /**
     * Button that click to load the text box.
     */
    @FXML
    public Button addStopButton;
    /**
     * Button that click to autofill the start location on text.
     */
    @FXML
    public Button startAutoFill;
    /**
     * Button that click to autofill the end location on text.
     */
    @FXML
    public Button endAutoFill;
    /**
     * Button that click to find the route.
     */
    @FXML
    public Button findRouteButton;
    /**
     * the text for the start location.
     */
    @FXML
    public Text startLabel;
    /**
     * the text for the end location.
     */
    @FXML
    public Text endLabel;
    /**
     * Button is clicked to remove the route from the map.
     */
    @FXML
    public CheckBox saveJourneyCheck;
    @FXML
    public Button removeRouteButton;
    private MainScreenController controller;
    @FXML
    private TextField startLocation;
    @FXML
    private TextField endLocation;
    private ArrayList<Button> addAddressButton = new ArrayList<>();
    private ArrayList<TextField>  arrayOfTextFields = new ArrayList<>();
    private ArrayList<String> addressMarkerTitles = new ArrayList<>();
    private ArrayList<ArrayList<Double>> addressMarkerLatLng = new ArrayList<>();
    private ArrayList<String> addresses = new ArrayList<>();
    private int numAddresses = 2;
    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;

        arrayOfTextFields.add(startLocation);
        addressMarkerTitles.add(null);
        ArrayList<Double> start = new ArrayList<>();
        start.add(null);
        start.add(null);
        addressMarkerLatLng.add(start);

        arrayOfTextFields.add(endLocation);
        addressMarkerTitles.add(null);
        ArrayList<Double> end = new ArrayList<>();
        end.add(null);
        end.add(null);
        addressMarkerLatLng.add(end);

        addAddressButton.add(addStopButton);
        addStopButton.setOnAction(event -> insertAddressFieldAndButton(addStopButton));
        startAutoFill.setOnAction(event -> autoFillEventHandler(startLocation));
        endAutoFill.setOnAction(event -> autoFillEventHandler(endLocation));

    }

    /**
     * Returns the latitude and longitude, of the address given.
     * @param query the query to geocode
     */
    public JSONObject geoCode(String query) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = null;
        encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String apiKey = "NdSNzsRJvYIyENlbzYj4XzOfYj0iK2Tv0lh0hLxky0w";

        String requestUri = "https://geocode.search.hereapi.com/v1/geocode" + "?apiKey=" + apiKey + "&q=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();
        HttpResponse<String> geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());
        String jsonString =  geocodingResponse.body();
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = null;
        try {
            jsonResponse = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray items = (JSONArray) jsonResponse.get("items");
        if (items == null || items.isEmpty()) {
            return null;
        } else {
            JSONObject bestResult = (JSONObject) items.get(0);
            return (JSONObject) bestResult.get("position");
        }
    }

    /**
     * This function goes through the text fields of locations,
     * calls geocode to get the longitude and latitude,
     * calls add route function with the list of longitude and latitudes.
     * @param actionEvent When find route button is clicked.
     */
    public void findRoute(ActionEvent actionEvent) {
        ArrayList<JSONObject> posArray = new ArrayList<>();
        addresses.clear();
        for (TextField textField : arrayOfTextFields) {
            try {
                if (!Objects.equals(textField.getText(), "")) {
                    addresses.add(textField.getText());
                    JSONObject location = geoCode(textField.getText());
                    posArray.add(location);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean validAddresses = true;
        if (posArray.contains(null)) {
            validAddresses = false;
        }
        if (posArray.size() >= 2 && validAddresses) {
            if (saveJourneyCheck.isSelected()) {
                if (controller.getCurrentUser() != null) {
                    Journey journey = new Journey(addresses, controller.getCurrentUser().getUsername());
                    controller.getDataService().addJourney(journey);
                } else {
                    AlertMessage.createMessage("Only Users can save journeys",
                            "Please unselect the box or sign in and try again");
                    saveJourneyCheck.setSelected(false);
                }
            }
            String json = new Gson().toJson(posArray);
            controller.getMapController().getJavaScriptConnector().call("addRoute", json);
        } else if (!validAddresses) {
            AlertMessage.createMessage("Invalid Address Entered.",
                    "Please input at least two valid destinations.");
        } else {
            AlertMessage.createMessage("Incorrect number of addresses.",
                    "Please input at least two destinations.");
        }
    }

    /**
     * Display filter screen on the map toolbar.
     * @param screen the screen that want to display.
     */
    public void setFilterSectionOnMapToolBar(Parent screen) {
        this.filterSectionOnMapToolBar.setCenter(screen);
    }

    /**
     * Sets text field to selected marker on the map as well as adding a routing marker
     * at that point. Method called when user selects auto-fill button. Takes the
     * address, latitude, and longitude from the current location of the marker, sets
     * the associated text-feild to contain the given address. Calls the
     * javascriptconnecter to replace all current address markers on the map with
     * the updated one.
     *
     * @param field The text field being filled.
     */
    public void autoFillEventHandler(TextField field) {
        Position position = controller.getMapController().getLatLng();
        if (position == null) {
            AlertMessage.createMessage("Current Location has not been selected.",
                    "Please select a location on the map.");

        } else {
            field.setText(controller.getMapController().getAddress());
            int fieldIndex = arrayOfTextFields.indexOf(field);

            addressMarkerTitles.set(fieldIndex, controller.getMapController().getAddress());
            ArrayList<Double> current = new ArrayList<>();
            current.add(controller.getMapController().getLatLng().getLatitude());
            current.add(controller.getMapController().getLatLng().getLongitude());
            addressMarkerLatLng.set(fieldIndex, current);

            controller.getMapController().getJavaScriptConnector().call("removeAddressMarkers");

            int i = 0;
            for (String address : addressMarkerTitles) {
                if (address != null) {
                    controller.getMapController().getJavaScriptConnector().call(
                            "addRoutingMarker", addressMarkerTitles.get(i),
                            addressMarkerLatLng.get(i).get(0), addressMarkerLatLng.get(i).get(1));
                }
                i++;
            }
        }
    }

    /**
     * This function adds another text field for a stop,
     * and add another autofill button for that text field.
     * @param button When add stop button is clicked.
     */
    public void insertAddressFieldAndButton(Button button) {
        if (numAddresses == 5) {
            AlertMessage.createMessage("Maximum number of stops reached.",
                    "Unable to add more stops.");
        } else {
            numAddresses++;

            planTripGridPane.getChildren().remove(endLabel);
            planTripGridPane.getChildren().remove(findRouteButton);
            planTripGridPane.getChildren().remove(addStopButton);
            planTripGridPane.getChildren().remove(removeRouteButton);
            planTripGridPane.getChildren().remove(saveJourneyCheck);

            TextField addOneTextField = new TextField();
            addOneTextField.setFont(Font.font(13));
            addOneTextField.setVisible(true);

            arrayOfTextFields.add(addOneTextField);

            //Adds the lat and long of the corresponding text field to
            // null because it has not yet been autofilled
            ArrayList<Double> current = new ArrayList<>();
            current.add(null);
            current.add(null);
            addressMarkerLatLng.add(current);
            addressMarkerTitles.add(null);

            Button autoFillButton = new Button("Auto-Fill");
            autoFillButton.setFont(Font.font(15));
            GridPane.setHalignment(autoFillButton, HPos.RIGHT);
            autoFillButton.setVisible(true);
            autoFillButton.setOnAction(e -> autoFillEventHandler(addOneTextField));

            RowConstraints firstRow = new RowConstraints();
            firstRow.fillHeightProperty();
            firstRow.setVgrow(Priority.SOMETIMES);
            firstRow.setMinHeight(40);
            firstRow.setPrefHeight(40);
            firstRow.setMaxHeight(40);
            planTripGridPane.getRowConstraints().add(firstRow);

            RowConstraints secondRow = new RowConstraints();
            secondRow.fillHeightProperty();
            secondRow.setVgrow(Priority.SOMETIMES);
            secondRow.setMinHeight(40);
            secondRow.setPrefHeight(40);
            secondRow.setMaxHeight(40);
            planTripGridPane.getRowConstraints().add(secondRow);

            int row = GridPane.getRowIndex(button);
            planTripGridPane.add(addOneTextField, 0,row + 1);
            planTripGridPane.add(autoFillButton, 0, row);
            planTripGridPane.add(endLabel, 0, row);
            planTripGridPane.add(findRouteButton, 0,row + 3);
            planTripGridPane.add(addStopButton, 0,row + 2);
            planTripGridPane.add(saveJourneyCheck, 0, row + 2);
            planTripGridPane.add(removeRouteButton, 0, row + 3);
        }

    }

    /**
     * This function removes the route from the map,
     * also resets the amount of text fields back to a start and end,
     * also resets the texts fields back to empty,
     * also removes the markers from the list of markers.
     * @param actionEvent When remove route button is clicked.
     */
    public void removeRoute(ActionEvent actionEvent) {
        controller.getMapController().getJavaScriptConnector().call("removeRoute");
        planTripGridPane.getChildren().clear();

        while (numAddresses > 2) {
            int num = planTripGridPane.getRowConstraints().size();
            planTripGridPane.getRowConstraints().remove(num - 1);
            planTripGridPane.getRowConstraints().remove(num - 2);
            numAddresses--;
        }

        planTripGridPane.add(startLabel, 0, 0);
        planTripGridPane.add(startAutoFill, 0, 0);
        planTripGridPane.add(startLocation, 0, 1);
        planTripGridPane.add(endAutoFill, 0, 2);
        planTripGridPane.add(endLabel, 0, 2);
        planTripGridPane.add(endLocation, 0, 3);
        planTripGridPane.add(saveJourneyCheck, 0, 4);
        planTripGridPane.add(addStopButton, 0,4);
        planTripGridPane.add(findRouteButton, 0,5);
        planTripGridPane.add(removeRouteButton, 0, 5);

        while (arrayOfTextFields.size() > 2) {
            arrayOfTextFields.remove(2);
            addressMarkerTitles.remove(0);
        }
        addressMarkerTitles.set(0, null);
        addressMarkerTitles.set(1, null);


        for (TextField textField : arrayOfTextFields) {
            textField.setText("");
        }
    }
}
