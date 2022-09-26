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
    private Stage stage;
    @FXML
    public TextField nameField;
    public Button closePopUp;
    private int stationId;
    private MainScreenController controller;



    /**
     * Initializes the Station Controller class.
     * @param id The stationId of the station.
     */
    public void init(Stage stage, MainScreenController controller, int id) throws IOException {
        this.stage = stage;
        this.stationId = id;
        this.controller = controller;
        setFields();
    }


    /**
     * Finds and returns the station to display.
     * @return station the station with corresponding id
     */
    public Station findStation() {
        return controller.getDataService().getStation(stationId);
    }

    /**
     * Function which sets the fields of the edit-station pop up to be filled with the
     * information from the selected station.
     */
    public void setFields() {
        Station station = findStation();
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


}
