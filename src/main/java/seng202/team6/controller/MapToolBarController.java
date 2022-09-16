package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seng202.team6.models.Position;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ScreenController {




    public BorderPane filterSectionOnMapToolBar;
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
    private TextField startLocation;

    @FXML
    private TextField endLocation;

    @FXML
    private Button newStationButton;

    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;

    }

    public void addNewStation(ActionEvent actionEvent) {
        String stationTitle = newStationTitle.getText();
        Double latitude = Double.parseDouble(newStationLatitude.getText());
        Double longitude = Double.parseDouble(newStationLongitude.getText());

        Position pos = new Position(latitude, longitude);


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

    private Position geoCode(String query) throws IOException, InterruptedException {
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
        Double lat = (Double) bestPosition.get("lat");
        Double lng = (Double) bestPosition.get("lng");
        System.out.println(lat);
        Position coords = new Position(lat, lng);
        return coords;
    }

    public void findRoute(ActionEvent actionEvent) {
        javaScriptConnector = controller.getMapController().getJavaScriptConnector();
        String firstLocation = startLocation.getText();
        String secondLocation = endLocation.getText();

        try {
            Position firstCoords = geoCode(firstLocation);
            Double firstLocationLat = firstCoords.getFirst();
            Double firstLocationLong = firstCoords.getSecond();
            Position secondCoords = geoCode(secondLocation);
            Double secondLocationLat = secondCoords.getFirst();
            Double secondLocationLong = secondCoords.getSecond();
//            javaScriptConnector.call("addMarker", firstLocation, firstLocationLat, firstLocationLong);
//            javaScriptConnector.call("addMarker", secondLocation, secondLocationLat, secondLocationLong);
            javaScriptConnector.call("addRoute", firstLocationLat, firstLocationLong, secondLocationLat, secondLocationLong);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setFilterSectionOnMapToolBar(Parent screen) {
        this.filterSectionOnMapToolBar.setCenter(screen);
    }

    public void showTable(ActionEvent actionEvent) {
    }

    public void eHStartAutoFill(ActionEvent actionEvent) {

    }

    public void eHEndAutoFill(ActionEvent actionEvent) {
    }
}
