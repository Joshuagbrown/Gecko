package seng202.team6.controller;

import java.io.IOException;
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
import seng202.team6.models.Position;
import seng202.team6.models.Station;
import seng202.team6.repository.StationDao;

public class StationController {

    @FXML
    public TextField latField;
    @FXML
    public TextField addressField;
    @FXML
    public CheckBox hoursButton;
    @FXML
    public CheckBox touristButton;
    @FXML
    public Button viewChargersButton;
    @FXML
    public TextField lonField;
    @FXML
    public Button saveButton;
    private Stage stage;
    @FXML
    public TextField nameField;
    private int stationId;
    private MainScreenController controller;
    private Station station;



    /**
     * Initializes the Station Controller class.
     * @param stage the stage for the pop-up.
     * @param controller the mainscreen controller.
     * @param id The stationId of the station.
     */
    public void init(Stage stage, MainScreenController controller, int id) throws IOException {
        this.stage = stage;
        this.stationId = id;
        this.controller = controller;
        findStation();
        setFields();
    }


    /**
     * Finds and sets the station to display.
     */
    public void findStation() {
        this.station = controller.getDataService().getStation(stationId);
    }


    /**
     * Function which sets the fields of the edit-station pop up to be filled with the
     * information from the selected station.
     */
    public void setFields() {
        nameField.setText(station.getName());
        Position pos = station.getCoordinates();
        latField.setText(String.valueOf(pos.getLatitude()));
        lonField.setText(String.valueOf(pos.getLongitude()));
        addressField.setText(station.getAddress());
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

    }

    /**
     * Function to save changes made by user and update database.
     * @param actionEvent when save button is clicked
     */
    public void savingChanges(ActionEvent actionEvent) {

        String newName = nameField.getText();
        String newAddress = addressField.getText();
        Double newLat = Double.parseDouble(latField.getText());
        Double newLong = Double.parseDouble(lonField.getText());
        Position newPos = new Position(newLat, newLong);
        boolean is24Hours = hoursButton.isSelected();
        boolean tourist = touristButton.isSelected();

        station.setName(newName);
        station.setAddress(newAddress);
        station.setPosition(newPos);
        station.setIs24Hours(is24Hours);
        station.setHasTouristAttraction(tourist);

        controller.getDataService().getStationDao().update(station);
        controller.updateStationsFromDatabase(null);
        stage.close();
        controller.setTextAreaInMainScreen(station.toString());

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
