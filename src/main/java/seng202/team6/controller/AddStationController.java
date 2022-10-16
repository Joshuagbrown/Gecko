package seng202.team6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.services.AlertMessage;
import seng202.team6.services.Validity;



/**
 * Controller for add station pop-up.
 * Author: Tara Lipscombe
 */
public class AddStationController implements StationController {
    private Logger log = LogManager.getLogger();
    @FXML
    private Text stationTitle;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField addressField;
    @FXML
    private CheckBox hoursButton;
    @FXML
    private CheckBox touristButton;
    @FXML
    private Button viewChargersButton;

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

    @FXML
    private Text chargerText;
    private MainScreenController controller;

    private Validity valid;

    private List<String> currentErrors = new ArrayList<>();

    private String address;

    private Position pos;

    private Station station;

    private Scene stationScene;
    private Scene chargerScene;
    private List<Charger> chargers = new ArrayList<>();
    private ChargerController chargerController = new ChargerController();
    private Boolean hasUnsavedChanges;


    /**
     * Initializes the Station Controller class.
     *
     * @param stage      the stage for the pop-up.
     * @param controller the mainscreen controller.
     */
    public void init(Stage stage, Scene scene, MainScreenController controller, String address) {
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            AlertMessage.createMessage("No station was created.", "To save a station you "
                    + "must fill out all fields, then select 'Continue', add at least one charger, "
                    + "finally, select 'Save Station'.");
            controller.setSelected(controller.getPrevSelected());
        });
        this.stationScene = scene;
        this.address = address;
        this.controller = controller;
        valid = new Validity(controller);
        if (address != null) {
            findLatLon();
            setFields();
        }
        stationTitle.setText("Add New Station Information");
        deleteButton.setVisible(false);
        chargerText.setVisible(false);
        viewChargersButton.setVisible(false);
        saveButton.setText("Continue");
        saveButton.setTranslateY(-18);
        gridPane.getRowConstraints().remove(5);
        hasUnsavedChanges = false;

    }




    /**
     * Finds the corresponding latitude and longitude for the given address.
     * and sets it to the position variable
     *
     * @throws IOException from geocoding
     * @throws InterruptedException from geocoding
     */
    private void findLatLon() {
        JSONObject positionField = controller.getMapToolBarController().geoCode(address);
        double lat = (double) positionField.get("lat");
        double lng = (double) positionField.get("lng");
        pos = new Position(lat, lng);
    }


    /**
     * Sets the fields in the add station pop up with the address, latitude and
     * longitude.
     */
    public void setFields() {
        if (address != null) {
            addressField.setText(address);
            addressField.setEditable(false);
        }

    }


    /**
     * Function to save changes made by user and update database.
     *
     * @param actionEvent when save button is clicked
     */
    public void savingChanges(ActionEvent actionEvent) throws IOException,
            InterruptedException {

        Boolean valid = checkValues();
        if (!valid) {
            AlertMessage.createListMessageStation("Invalid Changes made.",
                    "Please fix the changes with the following errors.", currentErrors);
            currentErrors.clear();
        } else {
            address = addressField.getText();
            findLatLon();
            String newName = nameField.getText();
            boolean is24Hours = hoursButton.isSelected();
            boolean tourist = touristButton.isSelected();
            String newOperator = operatorField.getText();
            String newOwner = ownerField.getText();
            int newTimeLimit = Integer.parseInt(timeLimitField.getText());
            int newCarParks = Integer.parseInt(numParksField.getText());
            boolean newCarParkCost = parkCostButton.isSelected();
            boolean newChargingCost = chargingCostButton.isSelected();
            chargers = chargerController.getCurrentChargers();


            Station newStation = new Station(pos, newName, newOperator, newOwner,
                    address, newTimeLimit, is24Hours, chargers, newCarParks, newCarParkCost,
                    newChargingCost, tourist);
            station = newStation;
            viewChargers(null);

        }


    }

    /**
     * Function to check that all new values are of correct type/format.
     */
    public Boolean checkValues() throws IOException, InterruptedException {

        Boolean returnable = true;
        String invalidStyle = "-fx-text-box-border: #B22222; -fx-focus-color: #B22222;";

        String newName = nameField.getText();

        if (!valid.checkStationName(newName)) {
            nameField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Station name must be greater than a length of 0, and only contain "
                    + "characters within the" + " following set {a-z, A-Z, '+', '&', ',', ' '}");
        } else {
            nameField.setStyle("");
        }

        String newAddress = addressField.getText();

        if (!valid.checkAddress(newAddress)) {
            addressField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Address must represent an existing address");
        } else {
            addressField.setStyle("");
        }


        String newOperator = operatorField.getText();

        if (!valid.checkOp(newOperator)) {
            operatorField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Operator name must be of length greater than 0 and only contain "
                    + "characters within the following set {a-z, A-Z, '(', ')', ' '}");
        } else {
            operatorField.setStyle("");
        }

        String newOwner = ownerField.getText();

        if (!valid.checkOp(newOwner)) {
            ownerField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Owner name must be of length greater than 0 and only contain "
                    + "characters within the following set {a-z, A-Z, '(', ')', ' '}");
        } else {
            ownerField.setStyle("");
        }

        String newTimeLimit = timeLimitField.getText();

        if (!valid.checkInts(newTimeLimit)) {
            timeLimitField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Time Limit must be a valid integer between 0 and 300");
        } else {
            timeLimitField.setStyle("");
        }

        String newCarParks = numParksField.getText();

        if (!valid.checkInts(newCarParks)) {
            numParksField.setStyle(invalidStyle);
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
        if (station == null) {
            Alert alert = AlertMessage.noAccess();
            ButtonType button = alert.getButtonTypes().get(0);
            ButtonType result = alert.showAndWait().orElse(button);

            if (button.equals(result)) {
                controller.loginButtonEventHandler(null);
            }
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chargers.fxml"));
            Parent root = loader.load();
            chargerScene = new Scene(root);
            stage.setScene(chargerScene);
            stage.setTitle("Current Chargers");
            stage.setResizable(false);
            stage.show();
            ChargerController chargerController = loader.getController();
            chargerController.init(stage, stationScene, controller, station, "new");
        }

    }




    /**
     * Adds any newly saved chargers on the charger pop-up to the charger list
     * So they are saved with the station in the database.
     * @param newCharger the charger to be added
     */
    public void addCharger(Charger newCharger) {
        chargers.add(newCharger);
    }

    /**
     * Function used to delete the currently selected Station from the database and map.
     * @param actionEvent when the user selects the "Delete Station" button
     */
    public void deleteSelectedStation(ActionEvent actionEvent) {

        controller.getDataService().getStationDao().delete(station.getStationId());
        try {
            controller.updateStationsFromDatabase();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        stage.close();
        controller.setTextAreaInMainScreen("");

    }



}
