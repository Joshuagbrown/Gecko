package seng202.team6.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.Validity;

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
    public TextField inputTextOfChargerType;
    public Button btnConfirmEdit;
    private MainScreenController controller;


    private List<String> chargerTypeList ;
    private List<String> makeList ;
    private List<String> modelList;
    private List<String> yearList ;
    private VehicleDao vehicleDao = new VehicleDao();

    private Validity validity = new Validity(controller);


    @Override
    public void init(Stage stage, MainScreenController controller) {


        this.controller = controller;
        loadPlugType();
        loadVehicleDataAndActionHandler();


    }

    public void loadEditVehicle(Vehicle vehicle){

        inputVehicleMake.valueProperty().set(vehicle.getMake());
        inputVehicleYear.valueProperty().set(Integer.toString(vehicle.getYear()));

        inputVehicleModel.valueProperty().set(vehicle.getModel());
        inputChargerType.valueProperty().set(vehicle.getPlugType());

    }


    private void loadVehicleDataAndActionHandler() {
        loadMake();
        inputVehicleMake.setOnAction(event -> {
            if (inputVehicleMake.getValue()=="other") {

                inputVehicleYear.getItems().clear();
                inputVehicleModel.getItems().clear();

                inputVehicleYear.valueProperty().set(null);
                inputVehicleModel.valueProperty().set(null);


                inputVehicleYear.setDisable(true);
                inputVehicleModel.setDisable(true);

                inputTextOfMake.setVisible(true);
                inputTextOfYear.setVisible(true);
                inputTextOfModel.setVisible(true);


            } else {

                resetInputMake();
                loadYear((String) inputVehicleMake.getValue());

            }
        });

        inputVehicleYear.setOnAction(event -> {
            if (inputVehicleYear.getValue()=="other") {

                inputVehicleModel.getItems().clear();

                inputVehicleModel.valueProperty().set(null);

                inputVehicleModel.setDisable(true);

                inputTextOfYear.setVisible(true);
                inputTextOfModel.setVisible(true);

            } else {
                inputVehicleModel.getItems().clear();

                inputVehicleModel.valueProperty().set(null);

                inputVehicleModel.setDisable(false);

                inputTextOfYear.setVisible(false);
                inputTextOfModel.setVisible(false);

                loadModel((String) inputVehicleMake.getValue(), (String) inputVehicleYear.getValue());
            }
        });

        inputVehicleModel.setOnAction(event -> {

            if (inputVehicleModel.getValue()=="other") {
                inputTextOfModel.setVisible(true);

            } else {
                inputTextOfModel.setVisible(false);
            }
        });
        inputChargerType.setOnAction(event -> {

            if (inputChargerType.getValue()=="other") {
                inputTextOfChargerType.setVisible(true);

            } else {
                inputTextOfChargerType.setVisible(false);
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
    public void loadPlugType(){

        List<String> plugType = vehicleDao.getPlugType();
        plugType.add("other");
        inputChargerType.getItems().setAll(plugType);

    }


    public void clearVehicleSelect(ActionEvent actionEvent) {

        inputVehicleMake.valueProperty().set(null);
        inputChargerType.valueProperty().set(null);
        resetInputMake();
        loadVehicleDataAndActionHandler();



    }

    public void submitVehicle(ActionEvent actionEvent) throws DatabaseException {
        if (inputChecking() != null) {
            vehicleDao.add(inputChecking());
            controller.getMyDetailController().loadUserVehicle();
            clearVehicleSelect(null);

        }
    }

    public Vehicle inputChecking(){
        String error = null;

        String make = null;
        int year = -1;
        String model = null;
        String plugType = null;

        if (inputVehicleMake.getValue()!= null && inputVehicleMake.getValue()!= "other") {
            make = (String) inputVehicleMake.getValue();
        } else {
            if (Validity.checkName(inputTextOfMake.getText())) {
                make = inputTextOfMake.getText();
            } else {
                error += "Please input a valid make \n" ;
                //AlertMessage.createMessage("Invalid vehicle data", "Please input a valid make");
            }
        }
        if (inputVehicleYear.getValue()!= null && inputVehicleYear.getValue()!= "other") {
            year = Integer.parseInt((String)inputVehicleYear.getValue());
        } else {
            if (Validity.checkValue(inputTextOfYear.getText())) {
                year = Integer.parseInt(inputTextOfYear.getText());
            } else {
                error += "Please input a numeric year\n";
                //AlertMessage.createMessage("Invalid vehicle data", "Please input a numeric year");
            }
        }
        if (inputVehicleModel.getValue()!= null && inputVehicleYear.getValue()!= "other") {
            model = (String) inputVehicleModel.getValue();
        } else {
            if (Validity.checkUserName(inputTextOfModel.getText())) {
                model = inputTextOfYear.getText();
            } else {
                error += "Please input a valid model of vehicle\n";
                //AlertMessage.createMessage("Invalid vehicle data", "Please input a valid model of vehicle");
            }
        }

        if (inputChargerType.getValue()!= null && inputChargerType.getValue()!= "other") {
            plugType = (String) inputChargerType.getValue();
        } else {
            if (Validity.checkPlugType(inputTextOfChargerType.getText())) {
                plugType = inputTextOfChargerType.getText();
            } else {
                error += "Please input a valid plug type of vehicle\n";
                //AlertMessage.createMessage("Invalid vehicle data", "Please input a valid plug type of vehicle");
            }
        }


        if(make != null && year != -1 && model != null && plugType!= null) {

            return new Vehicle(make,model,plugType,year,controller.getCurrentUserId());

        } else {
            AlertMessage.createMessage("Invalid vehicle data", error);
            return null;
        }
    }

    public void resetInputMake(){
        inputVehicleYear.getItems().clear();
        inputVehicleModel.getItems().clear();

        inputVehicleYear.valueProperty().set(null);
        inputVehicleModel.valueProperty().set(null);

        inputVehicleYear.setDisable(false);
        inputVehicleModel.setDisable(false);

        inputTextOfMake.setVisible(false);
        inputTextOfYear.setVisible(false);
        inputTextOfModel.setVisible(false);
    }


    public void updateEditVehicle(ActionEvent actionEvent) throws DatabaseException {
        if (inputChecking() != null) {
            vehicleDao.update(inputChecking());
            controller.getMyDetailController().loadUserVehicle();

        }
        clearVehicleSelect(null);
    }
}
