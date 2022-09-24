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


    public void StationController(int id) {



    }



    public void load() throws IOException {

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
