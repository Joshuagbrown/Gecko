package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MyDetailsToolBarController implements ScreenController{

    private MainScreenController controller;

    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    public void loadRegisterVehicle(ActionEvent actionEvent) { }

    public void loadSavedJourneys(ActionEvent actionEvent) { }

    public void loadGeneral(ActionEvent actionEvent) { }


}
