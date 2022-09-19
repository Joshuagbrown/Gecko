package seng202.team6.controller;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ScreenController {


    public BorderPane filterSectionOnMapToolBar;
    public TitledPane addStationSection;
    public GridPane planTripGridPane;
    public Button addStopButton;
    public Button startAutoFill;
    public Button endAutoFill;
    public Button findRouteButton;

    public Text startLabel;
    public Text endLabel;
    public Button removeRouteButton;

    @FXML
    private Button[] addButton = new Button[15];

    private Stage stage;
    private MainScreenController controller;

    private JSObject javaScriptConnector;

    @FXML
    private WebView webView;
    private WebEngine webEngine;

    @FXML
    private TextField newStationTitle;

    @FXML
    private TextField newStationLatitude;

    @FXML
    private TextField newStationLongitude;

    @FXML
    public TextField startLocation;


    @FXML
    public TextField endLocation;

    @FXML
    private Button newStationButton;

    private ArrayList<Button> addAddressButton = new ArrayList<Button>();

    private ArrayList<TextField>  arrayOfTextFields = new ArrayList<TextField>();
    private ArrayList<String> addressMarkerTitles = new ArrayList<String>();
    private ArrayList<ArrayList<Float>> addressMarkerLatLng = new ArrayList<ArrayList<Float>>();

    private int numAddresses;


    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;

        arrayOfTextFields.add(startLocation);
        addressMarkerTitles.add(null);
        ArrayList<Float> start = new ArrayList<Float>();
        start.add(null);
        start.add(null);
        addressMarkerLatLng.add(start);

        arrayOfTextFields.add(endLocation);
        addressMarkerTitles.add(null);
        ArrayList<Float> end = new ArrayList<Float>();
        end.add(null);
        end.add(null);
        addressMarkerLatLng.add(end);

        addAddressButton.add(addStopButton);
        addStopButton.setOnAction(event -> insertAddressFieldAndButton(addStopButton));
        startAutoFill.setOnAction(event -> eHAutoFill(startLocation));
        endAutoFill.setOnAction(event -> eHAutoFill(endLocation));

    }


    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's encodeURIComponent function.
     * @param s The String to be encoded
     * @return the encoded String
     */
    public static String encodeURI(String s) {
        String result;
        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } // This exception should never occur.
        catch (Exception e) {
            result = s;
        }

        return result;
    }

    private JSONObject geoCode(String query) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = null;
        encodedQuery = URLEncoder.encode(query,"UTF-8");
        String apiKey = "NdSNzsRJvYIyENlbzYj4XzOfYj0iK2Tv0lh0hLxky0w";

        String requestUri = "https://geocode.search.hereapi.com/v1/geocode" + "?apiKey=" + apiKey + "&q=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest, HttpResponse.BodyHandlers.ofString());
        String jsonString = (String) geocodingResponse.body();
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = null;
        try {
            jsonResponse = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray items = (JSONArray) jsonResponse.get("items");
        JSONObject bestResult = (JSONObject) items.get(0);
        JSONObject bestPosition = (JSONObject) bestResult.get("position");
        return bestPosition;
    }

    /**
     * This function goes through the text fields of locations,
     * calls geocode to get the longitude and latitude,
     * calls add route function with the list of longitude and latitudes.
     * @param actionEvent When find route button is clicked
     */
    public void findRoute(ActionEvent actionEvent) {
        javaScriptConnector = controller.getMapController().getJavaScriptConnector();
        ArrayList<JSONObject>  posArray = new ArrayList<JSONObject>();
        for (TextField textField : arrayOfTextFields) {
            try {
                if (textField.getText() !="") {
                    JSONObject location = geoCode(textField.getText());
                    posArray.add(location);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (posArray.size()>=2) {
            String json = new Gson().toJson(posArray);
            controller.getMapController().getJavaScriptConnector().call("addRoute", json);
        } else {
            AlertMessage.createMessage("Incorrect number of addresses.", "Please input at least two destinations.");
        }
    }

    public void setFilterSectionOnMapToolBar(Parent screen) {
        this.filterSectionOnMapToolBar.setCenter(screen);
    }

    public void showTable(ActionEvent actionEvent) {
    }

    /**
     * ??
     *
     * @param field
     */
    public void eHAutoFill(TextField field) {

        field.setText(controller.getMapController().getAddress());
        int fieldIndex = arrayOfTextFields.indexOf(field);

        addressMarkerTitles.set(fieldIndex, controller.getMapController().getAddress());
        ArrayList<Float> current = new ArrayList<Float>();
        current.add(controller.getMapController().getLatLng()[0]);
        current.add(controller.getMapController().getLatLng()[1]);
        addressMarkerLatLng.set(fieldIndex, current);

        controller.getMapController().getJavaScriptConnector().call("removeAddressMarkers");

        int i = 0;
        for (String address : addressMarkerTitles) {
            if (address != null) {
                controller.getMapController().getJavaScriptConnector().call("addRoutingMarker", addressMarkerTitles.get(i),
                        addressMarkerLatLng.get(i).get(0), addressMarkerLatLng.get(i).get(1));
            }
            i++;
        }

    }

    /**
     * This function adds another text field for a stop,
     * and add another autofill button for that text field.
     * @param button When add stop button is clicked.
     */
    public void insertAddressFieldAndButton(Button button) {
        planTripGridPane.getChildren().remove(endLabel);
        planTripGridPane.getChildren().remove(findRouteButton);
        planTripGridPane.getChildren().remove(addStopButton);
        planTripGridPane.getChildren().remove(removeRouteButton);

        TextField addOneTextField = new TextField();
        addOneTextField.setFont(Font.font(13));
        addOneTextField.setVisible(true);

        arrayOfTextFields.add(addOneTextField);

        //Adds the lat and long of the corresponding text field to null because it has not yet been autofilled
        ArrayList<Float> current = new ArrayList<Float>();
        current.add(null);
        current.add(null);
        addressMarkerLatLng.add(current);
        addressMarkerTitles.add(null);

        Button autoFillButton = new Button("Auto-Fill");
        autoFillButton.setFont(Font.font(15));
        GridPane.setHalignment(autoFillButton, HPos.RIGHT);
        autoFillButton.setVisible(true);
        //autoFillButton.
        autoFillButton.setOnAction(e -> eHAutoFill(addOneTextField));

        int row = planTripGridPane.getRowIndex(button);
        planTripGridPane.add(addOneTextField, 0,row + 2);
        planTripGridPane.add(autoFillButton, 0, row + 1);
        planTripGridPane.add(endLabel, 0, row + 1);
        planTripGridPane.add(findRouteButton, 0,row + 3);
        planTripGridPane.add(addStopButton, 0,row + 3);
        planTripGridPane.add(removeRouteButton, 0, row + 3);

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
        planTripGridPane.getChildren().removeAll(planTripGridPane.getChildren().stream().toList());

        planTripGridPane.add(startLabel, 0, 0);
        planTripGridPane.add(startAutoFill, 0, 0);
        planTripGridPane.add(startLocation, 0, 1);
        planTripGridPane.add(endAutoFill, 0, 2);
        planTripGridPane.add(endLabel, 0, 2);
        planTripGridPane.add(endLocation, 0, 3);
        planTripGridPane.add(findRouteButton, 0,4);
        planTripGridPane.add(addStopButton, 0,4);
        planTripGridPane.add(removeRouteButton, 0, 4);

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
