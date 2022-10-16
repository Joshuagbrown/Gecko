package seng202.team6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.services.AlertMessage;
import seng202.team6.services.Validity;


/**
 * Controller for edit station pop-up.
 * Author: Tara Lipscomnbe
 */
public class EditStationController implements StationController {

    @FXML
    private Text stationTitle;
    @FXML
    public BorderPane editStationBorderPane;
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
    private int stationId;
    private MainScreenController controller;
    private Station station;
    private Validity valid;
    private List<String> currentErrors = new ArrayList<>();

    private Scene stationScene;
    private Scene chargerScene;

    private String originalAddress;



    /**
     * Initializes the Station Controller class.
     * @param stage the stage for the pop-up.
     * @param controller the mainscreen controller.
     * @param id The stationId of the station.
     */
    public void init(Stage stage, Scene scene, MainScreenController controller, Integer id) {

        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            boolean saved = checkChanges();
            if (!saved) {
                e.consume();
            } else {
                if (controller.checksSelectedStationButton()) {
                    controller.setSelected(controller.getPrevSelected());
                }
            }
        });
        this.stationScene = scene;
        this.stationId = id;
        this.controller = controller;
        valid = new Validity(controller);
        findStation();
        setFields();
        stationTitle.setText("Edit Station Information");

    }



    /**
     * Finds and sets the station to display.
     */
    public void findStation() {
        station = controller.getStations().get(stationId);
        originalAddress = station.getAddress();
    }


    /**
     * Function which sets the fields of the edit-station pop up to be filled with the
     * information from the selected station.
     */
    public void setFields() {
        nameField.setText(station.getName());
        addressField.setText(station.getAddress());
        operatorField.setText(station.getOperator());
        ownerField.setText(station.getOwner());

        if (station.is24Hours()) {
            hoursButton.setSelected(true);
        } else {
            hoursButton.setSelected(false);
        }

        if (station.isHasTouristAttraction()) {
            touristButton.setSelected(true);
        } else {
            touristButton.setSelected(false);
        }

        timeLimitField.setText(String.valueOf(station.getTimeLimit()));
        numParksField.setText(String.valueOf(station.getNumberOfCarParks()));

        if (station.isCarparkCost()) {
            parkCostButton.setSelected(true);
        } else {
            parkCostButton.setSelected(false);
        }

        if (station.isChargingCost()) {
            chargingCostButton.setSelected(true);
        } else {
            chargingCostButton.setSelected(false);
        }
    }


    /**
     * Function to save changes made by user and update database.
     * @param actionEvent when save button is clicked
     */
    public void savingChanges(ActionEvent actionEvent) throws IOException, InterruptedException {

        boolean isValid = checkValues();
        if (!isValid) {
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

            station.setName(newName);
            station.setIs24Hours(is24Hours);
            station.setHasTouristAttraction(tourist);
            station.setOperator(newOperator);
            station.setOwner(newOwner);
            station.setTimeLimit(newTimeLimit);
            station.setNumberOfCarParks(newCarParks);
            station.setCarParkCost(newCarParkCost);
            station.setChargingCost(newChargingCost);

            String newAddress = addressField.getText();
            if (!Objects.equals(newAddress, originalAddress)) {
                station.setAddress(newAddress);
                Position newPos = getPos(newAddress);
                station.setPosition(newPos);
            }

            controller.getDataService().getStationDao().update(station);
            try {
                controller.updateStationsFromDatabase();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            stage.close();
            controller.setSelected(controller.getPrevSelected());
            controller.setTextAreaInMainScreen(station.toString());
        }

    }


    /**
     * Function to get the corresponding lat and long of a given address
     * and creates and returns a position object.
     * @param newAddress the address string
     * @return the position object
     */
    private Position getPos(String newAddress) throws IOException, InterruptedException {
        JSONObject positionField = controller.getMapToolBarController().geoCode(newAddress);
        double lat = (double) positionField.get("lat");
        double lng = (double) positionField.get("lng");
        return new Position(lat, lng);
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
            currentErrors.add("Station name must only contain "
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
            currentErrors.add("Operator name must only contain "
                    + "characters within the following set {a-z, A-Z, '(', ')', ' '}");
        } else {
            operatorField.setStyle("");
        }

        String newOwner = ownerField.getText();

        if (!valid.checkOp(newOwner)) {
            ownerField.setStyle(invalidStyle);
            returnable = false;
            currentErrors.add("Owner name must only contain "
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
        boolean saved = checkChanges();
        if (saved) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Chargers.fxml"));
            Parent root = loader.load();
            chargerScene = new Scene(root);
            stage.setScene(chargerScene);
            stage.setTitle("Current Chargers");
            stage.setResizable(false);
            stage.show();
            ChargerController chargerController = loader.getController();
            chargerController.init(stage, stationScene, controller, station, "update");
        }
    }


    /**
     * Checks to see if any unsaved changes exist within the pop-up.
     * @return boolean returns true if any unsaved changes exist.
     */
    private boolean checkChanges() {
        boolean hasUnsavedChanges = false;

        if (nameField.getText().length() > 0 && !nameField.getText().equals(
                station.getName())) {
            hasUnsavedChanges = true;
        }
        if (addressField.getText().length() > 0 && !addressField.getText()
                        .equals(station.getAddress())) {
            hasUnsavedChanges = true;
        }
        if (operatorField.getText().length() > 0 && !operatorField.getText()
                .equals(station.getOperator())) {
            hasUnsavedChanges = true;
        }
        if (ownerField.getText().length() > 0 && !ownerField.getText().equals(
                station.getOwner())) {
            hasUnsavedChanges = true;
        }
        if (hoursButton.isSelected() != station.is24Hours()) {
            hasUnsavedChanges = true;
        }
        if (touristButton.isSelected() != station.isHasTouristAttraction()) {
            hasUnsavedChanges = true;
        }
        if (timeLimitField.getText().length() > 0 && Integer.parseInt(timeLimitField
                .getText()) != station.getTimeLimit()) {
            hasUnsavedChanges = true;
        }
        if (numParksField.getText().length() > 0 && Integer.parseInt(numParksField
                .getText()) != station.getNumberOfCarParks()) {
            hasUnsavedChanges = true;
        }
        if (parkCostButton.isSelected() != station.isCarparkCost()) {
            hasUnsavedChanges = true;
        }
        if (chargingCostButton.isSelected() != station.isChargingCost()) {
            hasUnsavedChanges = true;
        }

        if (hasUnsavedChanges) {
            Alert alert = AlertMessage.unsavedChanges();
            ButtonType cancel = alert.getButtonTypes().get(0);
            ButtonType result = alert.showAndWait().orElse(cancel);

            if (cancel.equals(result)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Function used to delete the currently selected Station from the database and map.
     * @param actionEvent when the user selects the "Delete Station" button
     */
    public void deleteSelectedStation(ActionEvent actionEvent) {

        Alert alert = AlertMessage.deleteStation();
        ButtonType delete = alert.getButtonTypes().get(1);
        ButtonType result = alert.showAndWait().orElse(delete);

        if (delete.equals(result)) {
            controller.getDataService().getStationDao().delete(station.getStationId());
            try {
                controller.updateStationsFromDatabase();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            stage.close();
            controller.setSelected(controller.getPrevSelected());
            controller.setTextAreaInMainScreen("");
            controller.getDataController().currentlySelected = null;
        }

    }


}
