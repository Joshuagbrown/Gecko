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
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Journey;
import seng202.team6.models.Position;
import seng202.team6.services.AlertMessage;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ScreenController {
    private Logger log = LogManager.getLogger();
    /**
     * Border pane that hold the filter section on map toolbar.
     */
    @FXML
    private BorderPane filterSectionOnMapToolBar;
    /**
     * The grid pane that hold the find routes texts.
     */
    @FXML
    private GridPane planTripGridPane;
    /**
     * Button that click to load the text box.
     */
    @FXML
    private Button addStopButton;
    /**
     * Button that click to autofill the start location on text.
     */
    @FXML
    private Button startAutoFill;
    /**
     * Button that click to autofill the end location on text.
     */
    @FXML
    private Button endAutoFill;
    /**
     * Button that click to find the route.
     */
    @FXML
    private Button findRouteButton;
    /**
     * the text for the start location.
     */
    @FXML
    private Text startLabel;
    /**
     * the text for the end location.
     */
    @FXML
    private Text endLabel;
    /**
     * Button is clicked to remove the route from the map.
     */
    @FXML
    private CheckBox saveJourneyCheck;
    @FXML
    private Button removeRouteButton;
    @FXML
    private Button removeLastStopButton;
    private MainScreenController controller;
    @FXML
    private TextField startLocation;
    @FXML
    private TextField endLocation;
    private ArrayList<Button> addAddressButton = new ArrayList<>();
    private ArrayList<TextField> arrayOfTextFields = new ArrayList<>();
    private ArrayList<String> addressMarkerTitles = new ArrayList<>();
    private ArrayList<ArrayList<Double>> addressMarkerLatLng = new ArrayList<>();
    private ArrayList<String> addresses = new ArrayList<>();
    private ArrayList<Button> autoFillButtons = new ArrayList<>();
    private int numAddresses = 2;


    @FXML
    public TextField addOneTextField;
    /**
     * Initializes the controller.
     *
     * @param stage      Primary Stage of the application.
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

        autoFillButtons.add(startAutoFill);
        autoFillButtons.add(endAutoFill);

    }

    /**
     * Function that takes a "potential" address string, uses geocoding (provided by
     * API Here.com)
     * to identify its corresponding position (represented by a latitude and a
     * longitude) and
     * return this position, will return null if provided string does not correspond
     * to
     * an existing address.
     *
     * @param query the query to geocode
     */
    public JSONObject geoCode(String query) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = null;
        encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String apiKey = "NdSNzsRJvYIyENlbzYj4XzOfYj0iK2Tv0lh0hLxky0w";

        String requestUri = "https://geocode.search.hereapi.com/v1/geocode" + "?apiKey=" + apiKey + "&q="
                + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();
        try {
            HttpResponse<String> geocodingResponse =
                httpClient.send(geocodingRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = geocodingResponse.body();
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
                JSONObject address = (JSONObject) bestResult.get("address");
                String country = (String) address.get("countryName");
                if (!Objects.equals(country, "New Zealand")) {
                    return null;
                }
                return (JSONObject) bestResult.get("position");
            }
        } catch (InterruptedException | IOException e) {
            AlertMessage.createMessage("An error occurred",
                                       "There was an error connecting to the geocoding API."
                                       + "See the log for more details.");
            log.error("Error connecting to geocoding API", e);
        }
        return null;
    }

    /**
     * when the 'GO!' button is clicked,
     * calls the 'goRoute' function.
     *
     * @param actionEvent When the 'GO!' button is clicked.
     */
    public void findRoute(ActionEvent actionEvent) {
        goRoute();
    }

    /**
     * This function goes through the text fields of locations,
     * calls geocode to get the longitude and latitude,
     * calls add route function with the list of longitude and latitudes.
     */
    public void goRoute() {
        ArrayList<JSONObject> posArray = new ArrayList<>();
        addresses.clear();
        for (TextField textField : arrayOfTextFields) {
            if (!Objects.equals(textField.getText(), "")) {
                addresses.add(textField.getText());
                JSONObject location = geoCode(textField.getText());
                posArray.add(location);
            }
        }
        boolean validAddresses = true;
        if (posArray.contains(null)) {
            validAddresses = false;
        }
        if (posArray.size() >= 2 && validAddresses) {
            if (saveJourneyCheck.isSelected()) {
                if (controller.getCurrentUser() != null) {
                    try {
                        Journey journey = new Journey(addresses,
                                controller.getCurrentUser().getUsername());
                        controller.getDataService().addJourney(journey);
                    } catch (DatabaseException e) {
                        AlertMessage.createMessage("Invalid journey", "");
                    }
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
            AlertMessage.createMessage("Incorrect number of Addresses.",
                    "Please input at least two valid destinations.");
        }
    }

    /**
     * This function clears the route, the makes the route,
     * from the route given from the journey.
     *
     * @param stopAmount   the amount of stops needing to be added
     * @param allAddresses all the addresses in the route
     */
    public void findRouteFromJourney(int stopAmount, List<String> allAddresses) {
        deleteRoute();
        int counter = 0;
        for (int i = 0; i < stopAmount; i++) {
            insertAddressFieldAndButton(addStopButton);
        }
        for (TextField textField : arrayOfTextFields) {
            textField.setText(allAddresses.get(counter));
            counter++;
        }
        goRoute();
    }

    /**
     * Display filter screen on the map toolbar.
     *
     * @param screen the screen that want to display.
     */
    public void setFilterSectionOnMapToolBar(Parent screen) {
        this.filterSectionOnMapToolBar.setCenter(screen);
    }

    /**
     * Sets text field to selected marker on the map as well as adding a routing
     * marker
     * at that point. Method called when user selects auto-fill button. Takes the
     * address, latitude, and longitude from the current location of the marker,
     * sets
     * the associated text-field to contain the given address. Calls the
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
     *
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
            planTripGridPane.getChildren().remove(removeLastStopButton);

            addOneTextField = new TextField();
            addOneTextField.setFont(Font.font(13));
            addOneTextField.setVisible(true);
            addOneTextField.setId("addOneTextField" + String.valueOf(numAddresses));

            arrayOfTextFields.add(addOneTextField);

            // Adds the lat and long of the corresponding text field to
            // null because it has not yet been autofilled
            ArrayList<Double> current = new ArrayList<>();
            current.add(null);
            current.add(null);
            addressMarkerLatLng.add(current);
            addressMarkerTitles.add(null);

            Button autoFillButton = new Button("Auto-fill from");
            autoFillButton.setContentDisplay(ContentDisplay.RIGHT);
            Image image = new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream("/images/marker-icon-2x-red.png")));
            ImageView imageview = new ImageView(image);
            imageview.setFitHeight(addStopButton.getHeight());
            imageview.setPreserveRatio(true);
            autoFillButton.setGraphic(imageview);
            autoFillButton.setFont(Font.font(15));
            GridPane.setHalignment(autoFillButton, HPos.RIGHT);
            autoFillButton.setVisible(true);
            autoFillButton.setOnAction(e -> autoFillEventHandler(addOneTextField));
            autoFillButtons.add(autoFillButton);

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
            planTripGridPane.add(addOneTextField, 0, row + 1);
            planTripGridPane.add(autoFillButton, 0, row);
            planTripGridPane.add(endLabel, 0, row);
            planTripGridPane.add(findRouteButton, 0,row + 4);
            planTripGridPane.add(addStopButton, 0,row + 2);
            planTripGridPane.add(saveJourneyCheck, 0, row + 3);
            planTripGridPane.add(removeRouteButton, 0, row + 4);
            planTripGridPane.add(removeLastStopButton, 0, row + 2);
        }
    }

    /**
     * Function called after the 'Remove Last Stop' button is selected.
     * Removes the last stop in the route.
     * @param actionEvent when the 'Remove Last Stop' button is selected
     */
    public void removeLastStop(ActionEvent actionEvent) {
        //to be done
        ArrayList<String> currentAddresses = new ArrayList<>();
        for (TextField text : arrayOfTextFields) {
            currentAddresses.add(text.getText());
        }
        deleteRoute();

        int max = currentAddresses.size() - 1;
        int index = 0;

        while (index < max) {
            if (index < 2) {
                arrayOfTextFields.get(index).setText(currentAddresses.get(index));
            } else {
                Button lastButton = addAddressButton.get(addAddressButton.size() - 1);
                insertAddressFieldAndButton(lastButton);
                arrayOfTextFields.get(arrayOfTextFields.size() - 1).setText(currentAddresses
                        .get(index));
            }
            index++;
        }
    }

    /**
     * This function removes the route from the map,
     * also resets the amount of text fields back to a start and end,
     * also resets the texts fields back to empty,
     * also removes the markers from the list of markers.
     */
    public void deleteRoute() {
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
        planTripGridPane.add(addStopButton, 0,4);
        planTripGridPane.add(removeLastStopButton, 0, 4);
        planTripGridPane.add(saveJourneyCheck, 0, 5);
        planTripGridPane.add(findRouteButton, 0,6);
        planTripGridPane.add(removeRouteButton, 0, 6);

        while (arrayOfTextFields.size() > 2) {
            arrayOfTextFields.remove(2);
            addressMarkerTitles.remove(0);
        }
        addressMarkerTitles.set(0, null);
        addressMarkerTitles.set(1, null);

        for (TextField textField : arrayOfTextFields) {
            textField.setText("");
        }
        saveJourneyCheck.setSelected(false);

        while (autoFillButtons.size() > 2) {
            int size = autoFillButtons.size();
            autoFillButtons.remove(size - 1);
        }
    }

    /**
     * This function removes the route from the map,
     * by calling the 'delete route' function.
     *
     * @param actionEvent When remove route button is clicked.
     */
    public void removeRoute(ActionEvent actionEvent) {
        deleteRoute();
    }


    /**
     * Sets all auto-fill buttons to disabled, so user is unable to autofill until address it
     * fetched.
     */
    public void setAutoFillButtonsOff() {
        for (Button autofill : autoFillButtons) {
            autofill.setDisable(true);
        }
    }

    /**
     * Sets all auto-fill buttons to enabled, so user is able to autofill.
     */
    public void setAutoFillButtonsOn() {
        for (Button autofill : autoFillButtons) {
            autofill.setDisable(false);
        }
    }



}
