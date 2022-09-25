package seng202.team6.controller;

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

import java.io.IOException;

public class StationController {

    @FXML
    public TextField yField;
    @FXML
    public TextField addressField;
    @FXML
    public ToggleButton hoursButton;
    @FXML
    public ToggleButton touristButton;
    @FXML
    public Button viewChargersButton;
    @FXML
    public TextField xField;
    @FXML
    public Stage stage;
    @FXML
    public TextField nameField;
    private int stationId;

    public StationController(int id) {
        stationId = id;
    }


    public void setFields(Station station) {
        nameField.setText(station.getName());
        Position pos = station.getCoordinates();
        xField.setText(String.valueOf(pos.getLatitude()));
        yField.setText(String.valueOf(pos.getLongitude()));
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
