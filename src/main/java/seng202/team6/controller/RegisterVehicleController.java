package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//TODO make controller
public class RegisterVehicleController implements ScreenController {

    public ChoiceBox inputChargerType;
    public ChoiceBox inputVehicleMake;
    public ChoiceBox inputVehicleModel;
    public ChoiceBox inputVehicleYear;
    public Button submitVehicleButton;
    public TextField inputTextOfMake;
    private MainScreenController controller;


    private String[] chargerTypeList  = {"other"};
    private String[] makeList  = {"Japan","other"};
    private String[] modelList  = {"other"};
    private String[] yearList  = {"other"};


    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;

        inputVehicleMake.getItems().setAll(makeList);
        inputVehicleMake.setOnAction(event -> {
            if (inputVehicleMake.getValue() == "other") {
                inputTextOfMake.setVisible(true);

            } else {
                inputTextOfMake.setVisible(false);
            }
        });
    }

//    public void checkChoiceBoxSeclection(){
//        if (inputChargerType.getValue() == "other");
//    }

    public void clearVehicleSelect(ActionEvent actionEvent) {
    }

    public void submitVehicle(ActionEvent actionEvent) {
    }


}
