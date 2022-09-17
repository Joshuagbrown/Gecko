package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
    public Text endLabel;

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

    private ArrayList<Button> addAddressButton = new ArrayList<Button> ();

    private ArrayList<TextField>  arrayOfTextFields = new ArrayList<TextField>();


    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
        addStationSection.setVisible(false);
        arrayOfTextFields.add(startLocation);
        arrayOfTextFields.add(endLocation);
        addAddressButton.add(addStopButton);
        addStopButton.setOnAction(event -> insertAddressFieldAndButton(addStopButton));
        startAutoFill.setOnAction(event -> eHAutoFill(startLocation));
        endAutoFill.setOnAction(event -> eHAutoFill(endLocation));
        System.out.println(planTripGridPane.getRowIndex(startAutoFill));
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
        Position coords = new Position(lat, lng);
        return coords;
    }

    public void findRoute(ActionEvent actionEvent) {
        javaScriptConnector = controller.getMapController().getJavaScriptConnector();
        int i = 1;
        for (TextField textField : arrayOfTextFields){
            if(i < arrayOfTextFields.size())
            {
                String firstLocation = textField.getText();
                String secondLocation = arrayOfTextFields.get(i).getText();


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
                i +=1;









            }

        }


    }




    public void findRouteEH() {
        {
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
    }

    public void setFilterSectionOnMapToolBar(Parent screen) {
        this.filterSectionOnMapToolBar.setCenter(screen);
    }

    public void showTable(ActionEvent actionEvent) {
    }

    public void eHAutoFill(TextField field) {
        field.setText(controller.getMapController().getAddress());

        controller.getMapController().getJavaScriptConnector().call("addRoutingMarker", controller.getMapController().getAddress(), controller.getMapController().getLatLng()[0],
                controller.getMapController().getLatLng()[1]);
    }

//
//
//    public void put_stone(Button button){
//        int row = GridPane.getRowIndex(button);
//        int column = GridPane.getColumnIndex(button);
//    }

    public void insertAddressFieldAndButton(Button button){

        int row = planTripGridPane.getRowIndex(button);
        planTripGridPane.getChildren().remove(endLabel);
        planTripGridPane.getChildren().remove(findRouteButton);
        planTripGridPane.getChildren().remove(addStopButton);



        TextField addOneTextField = new TextField();
        addOneTextField.setVisible(true);
        arrayOfTextFields.add(addOneTextField);


        //Button addOneButton = new Button("+");
        //addAddressButton.add((row+1) / 2 ,addOneButton);
        //addOneButton.setOnAction(event ->insertAddressFieldAndButton(addOneButton) );
        //addOneButton.setVisible(true);

        Button autoFillButton = new Button("Auto-Fill");
        GridPane.setHalignment(autoFillButton, HPos.RIGHT);
        autoFillButton.setVisible(true);
        //autoFillButton.
        autoFillButton.setOnAction(e -> eHAutoFill(addOneTextField));



        planTripGridPane.add(addOneTextField, 0 ,row+2);
//        planTripGridPane.add(addOneButton,1,row+2);
        planTripGridPane.add(autoFillButton, 0, row+1);
        planTripGridPane.add(endLabel, 0, row+1);
        planTripGridPane.add(findRouteButton, 0 ,row+3);
        planTripGridPane.add(addStopButton, 1 ,row+3);
//        Button goButton = new Button("GO!");
//        goButton.setOnAction(e -> findRoute(null));

    }

}
