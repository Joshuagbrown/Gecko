package seng202.team6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.services.Validity;



/**
 * Controller for add station pop-up.
 * Author: Tara Lipscombe
 */
public class AddStationController {

    @FXML
    private Button deleteButton;
    @FXML
    private TextField latField;
    @FXML
    private TextField addressField;
    @FXML
    private CheckBox hoursButton;
    @FXML
    private CheckBox touristButton;
    @FXML
    private Button viewChargersButton;
    @FXML
    private TextField lonField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField operatorField;
    @FXML
    private TextField ownerField;
    @FXML
    private TextField timeLimitField;
    @FXML
    private TextField numParksField;
    @FXML
    private CheckBox parkCostButton;
    @FXML
    private CheckBox chargingCostButton;
    private Stage stage;
    @FXML
    private TextField nameField;

    private MainScreenController controller;

    private Validity valid;

    private List<String> currentErrors = new ArrayList<>();

    private String address;

    private Position pos;

    private Station station;


    /**
     * Initializes the Station Controller class.
     *
     * @param stage      the stage for the pop-up.
     * @param controller the mainscreen controller.
     */
    public void init(Stage stage, MainScreenController controller, String address)
            throws IOException, InterruptedException {

        this.stage = stage;
        this.address = address;
        this.controller = controller;
        valid = new Validity(controller);
        findLatLon();
        setFields();

    }


    /**
     * Finds the corresponding latitude and longitude for the given address.
     * and sets it to the position variable
     *
     * @throws IOException from geocoding
     * @throws InterruptedException from geocoding
     */
    private void findLatLon() throws IOException, InterruptedException {
        JSONObject positionField = controller.getMapToolBarController().geoCode(address);
        String lat = (String) positionField.get("lat");
        String lng = (String) positionField.get("lng");
        pos = new Position(Double.parseDouble(lat), Double.parseDouble(lng));

    }


    /**
     * Sets the fields in the add station pop up with the address, latitude and
     * longitude.
     */
    private void setFields() {

        addressField.setText(address);
        latField.setText(String.valueOf(pos.getLatitude()));
        lonField.setText(String.valueOf(pos.getLongitude()));
        addressField.setEditable(false);
        latField.setEditable(false);
        lonField.setEditable(false);

    }


    /**
     * Function to save changes made by user and update database.
     *
     * @param actionEvent when save button is clicked
     */
    public void savingChanges(ActionEvent actionEvent) throws IOException,
            InterruptedException, DatabaseException {

        Boolean valid = checkValues();
        if (!valid) {
            AlertMessage.createListMessageStation("Invalid Changes made.",
                    "Please fix the changes with the following errors.", currentErrors);
            currentErrors.clear();
        } else {
            String newName = nameField.getText();
            boolean is24Hours = hoursButton.isSelected();
            boolean tourist = touristButton.isSelected();
            String newOperator = operatorField.getText();
            String newOwner = ownerField.getText();
            int newTimeLimit = Integer.parseInt(timeLimitField.getText());
            int newCarParks = Integer.parseInt(numParksField.getText());
            boolean newCarParkCost = parkCostButton.isSelected();
            boolean newChargingCost = chargingCostButton.isSelected();

            ///CHECK CHARGERS
            List<Charger> chargers = new ArrayList<Charger>();

            Station newStation = new Station(pos, newName, -1, newOperator, newOwner,
                    address, newTimeLimit, is24Hours, chargers, newCarParks, newCarParkCost,
                    newChargingCost, tourist);

            station = newStation;
            controller.getDataService().getStationDao().add(newStation);
            controller.updateStationsFromDatabase();
            stage.close();
            controller.setTextAreaInMainScreen(newStation.toString());

        }


    }

    /**
     * Function to check that all new values are of correct type/format.
     */
    private Boolean checkValues() throws IOException, InterruptedException {

        Boolean returnable = true;

        String newName = nameField.getText();

        if (!valid.checkStationName(newName)) {
            nameField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
            currentErrors.add("Station name must be greater than a length of 0, and only contain "
                    + "characters within the" + " following set {a-z, A-Z, '+', '&', ',', ' '}");
        } else {
            nameField.setStyle("");
        }


        String newOperator = operatorField.getText();

        if (!valid.checkOp(newOperator)) {
            operatorField.setStyle("-fx-text-box-border-width: 10px; -fx-text-box-border: #b22222; "
                    + "-fx-focus-color: #b22222;");
            returnable = false;
            currentErrors.add("Operator name must be of length greater than 0 and only contain "
                    + "characters within the following set {a-z, A-Z, '(', ')', ' '}");
        } else {
            operatorField.setStyle("");
        }

        String newOwner = ownerField.getText();

        if (!valid.checkOp(newOwner)) {
            ownerField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
            currentErrors.add("Owner name must be of length greater than 0 and only contain "
                    + "characters within the following set {a-z, A-Z, '(', ')', ' '}");
        } else {
            ownerField.setStyle("");
        }

        String newTimeLimit = timeLimitField.getText();

        if (!valid.checkInts(newTimeLimit)) {
            timeLimitField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
            currentErrors.add("Time Limit must be a valid integer between 0 and 300");
        } else {
            timeLimitField.setStyle("");
        }

        String newCarParks = numParksField.getText();

        if (!valid.checkInts(newCarParks)) {
            numParksField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
            currentErrors.add("Number of CarParks must be a valid integer between 0 and 300");
        } else {
            numParksField.setStyle("");
        }

        return returnable;
    }


    /**
     * Function to initialize the view/edit charger pop-up.
     * @param actionEvent when the "View chargers" button is clicked
     */
    public void viewChargers(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chargers.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Current Chargers");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        ChargerController chargerController = loader.getController();
        chargerController.init(stage, controller, station);

    }




























}
