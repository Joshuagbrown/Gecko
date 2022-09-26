package seng202.team6.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    public ToggleButton hoursButton;
    @FXML
    public ToggleButton touristButton;
    @FXML
    public Button viewChargersButton;
    @FXML
    public TextField lonField;
    @FXML
    public Stage stage;
    @FXML
    public TextField nameField;
    private int stationId;
    private MainScreenController controller;


    /**
     * Initializes the Station Controller class.
     * @param id The stationId of the station.
     */
    public void init(MainScreenController controller, int id) throws IOException {
        this.stationId = id;
        this.controller = controller;
        setFields();
        loadWindow();
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
        System.out.println(station.getName());
        nameField.setText(station.getName());
        System.out.println("gets here");
        Position pos = station.getCoordinates();
        //System.out.println("gets here");
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
     * Loads the edit station pop-up window.
     * @throws IOException Exception thrown
     */
    public void loadWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Station.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("My Window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

}
