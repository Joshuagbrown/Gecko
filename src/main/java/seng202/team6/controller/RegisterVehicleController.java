package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.repository.VehicleDao;

import java.util.List;

//TODO make controller
public class RegisterVehicleController implements ScreenController {

    public ChoiceBox inputChargerType;
    public ChoiceBox inputVehicleMake;
    public ChoiceBox inputVehicleModel;
    public ChoiceBox inputVehicleYear;

    public ChoiceBox[] choiceBoxeList = {inputVehicleMake,inputVehicleModel,inputVehicleYear};
    public Button submitVehicleButton;
    public TextField inputTextOfMake;
    public TextField inputTextOfYear;
    public TextField inputTextOfModel;
    private MainScreenController controller;


    private List<String> chargerTypeList ;
    private List<String> makeList ;
    private List<String> modelList;
    private List<String> yearList ;
    private VehicleDao vehicleDao = new VehicleDao();


    @Override
    public void init(Stage stage, MainScreenController controller) {


        this.controller = controller;

        loadMake();
        inputVehicleMake.setOnAction(event -> {
            if (inputVehicleMake.getValue() == "other") {
                inputTextOfMake.setVisible(true);
                inputTextOfYear.setVisible(true);
                inputTextOfModel.setVisible(true);



            } else {
                inputTextOfMake.setVisible(false);
                System.out.println((String) inputVehicleMake.getValue());
                loadYear((String) inputVehicleMake.getValue());



            }
        });
        inputVehicleYear.setOnAction(event -> {
            if (inputVehicleYear.getValue().equals("other")) {
                inputTextOfYear.setVisible(true);
                inputTextOfModel.setVisible(true);

            } else {
                inputTextOfYear.setVisible(false);
                loadModel((String) inputVehicleMake.getValue(), (String) inputVehicleYear.getValue());



            }
        });

        inputVehicleModel.setOnAction(event -> {
            if (inputVehicleYear.getValue().equals("other")) {
                inputTextOfModel.setVisible(true);

            } else {
                inputTextOfModel.setVisible(false);

            }
        });

    }

    public void loadMake(){
        makeList = vehicleDao.getMakes();
        makeList.add("other");
        inputVehicleMake.getItems().setAll(makeList);
    }

    public void loadYear(String make){
        yearList = vehicleDao.getYear(make);
        yearList.add("other");
        inputVehicleYear.getItems().setAll(yearList);
    }

    public void loadModel (String make, String year) {
        modelList = vehicleDao.getModel(make,year);
        modelList.add("other");
        inputVehicleModel.getItems().setAll(modelList);
    }


    public void clearVehicleSelect(ActionEvent actionEvent) {
    }

    public void submitVehicle(ActionEvent actionEvent) {
    }


}
