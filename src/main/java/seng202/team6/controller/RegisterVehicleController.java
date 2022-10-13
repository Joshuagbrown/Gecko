package seng202.team6.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.Validity;


public class RegisterVehicleController implements ScreenController {

    public ComboBox inputChargerType;
    public ComboBox inputVehicleMake;
    public ComboBox inputVehicleModel;
    public ComboBox inputVehicleYear;

    public ComboBox[] comboBoxeList = {inputVehicleMake,inputVehicleModel,inputVehicleYear};
    public Button submitVehicleButton;
    public TextField inputTextOfMake;
    public TextField inputTextOfYear;
    public TextField inputTextOfModel;
    public TextField inputTextOfChargerType;
    public Button btnConfirmEdit;
    private MainScreenController controller;


    private List<String> chargerTypeList;
    private List<String> makeList;
    private List<String> modelList;
    private List<String> yearList;
    private VehicleDao vehicleDao = new VehicleDao();

    private Validity validity = new Validity(controller);

    private Vehicle editVehicle;


    @Override
    public void init(Stage stage, MainScreenController controller) {


        this.controller = controller;
        loadPlugType();
        loadVehicleDataAndActionHandler();


    }

    /**
     * load the vehicle page with the vehicle that want to edit.
     * @param vehicle the vehicle that want to edit the data.
     */
    public void loadEditVehicle(Vehicle vehicle) {

        inputVehicleMake.valueProperty().set(vehicle.getMake());
        inputVehicleYear.valueProperty().set(Integer.toString(vehicle.getYear()));

        inputVehicleModel.valueProperty().set(vehicle.getModel());
        inputChargerType.valueProperty().set(vehicle.getPlugType());

    }


    private void loadVehicleDataAndActionHandler() {
        loadMake();
        inputVehicleMake.setOnAction(event -> {
            if (inputVehicleMake.getValue() == "other") {

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
            if (inputVehicleYear.getValue() == "other") {

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

                loadModel((String) inputVehicleMake.getValue(),
                        (String) inputVehicleYear.getValue());
            }
        });

        inputVehicleModel.setOnAction(event -> {

            if (inputVehicleModel.getValue() == "other") {
                inputTextOfModel.setVisible(true);

            } else {
                inputTextOfModel.setVisible(false);
            }
        });
        inputChargerType.setOnAction(event -> {

            if (inputChargerType.getValue() == "other") {
                inputTextOfChargerType.setVisible(true);

            } else {
                inputTextOfChargerType.setVisible(false);
            }
        });

    }

    /**
     * load the vehicle make into the choice box.
     */
    public void loadMake() {
        makeList = vehicleDao.getMakes();
        makeList.add("other");
        inputVehicleMake.getItems().setAll(makeList);
    }

    /**
     * load the year of vehicle into the choice box with related make.
     * @param make the vehicle make that determine the vehicle year.
     */
    public void loadYear(String make) {
        yearList = vehicleDao.getYear(make);
        yearList.add("other");
        inputVehicleYear.getItems().setAll(yearList);
    }

    /**
     * load the vehicle model into the choice box with related year and make.
     * @param make the vehicle make that determine the vehicle model.
     * @param year the vehicle year that determine the vehicle model.
     */
    public void loadModel(String make, String year) {
        modelList = vehicleDao.getModel(make,year);
        modelList.add("other");
        inputVehicleModel.getItems().setAll(modelList);
    }

    /**
     * load the possible plug type into the choice box.
     */
    public void loadPlugType() {

        List<String> plugType = vehicleDao.getPlugType();
        plugType.add("other");
        inputChargerType.getItems().setAll(plugType);

    }


    /**
     * Clear the selected value of all the choice box.
     * @param actionEvent the click action.
     */
    public void clearVehicleSelect(ActionEvent actionEvent) {

        inputVehicleMake.valueProperty().set(null);
        inputChargerType.valueProperty().set(null);
        resetInputMake();
        loadVehicleDataAndActionHandler();



    }

    /**
     * The event handler of the add a new vehicle button.
     * @param actionEvent event handler.
     * @throws DatabaseException the database error.
     */
    public void submitVehicle(ActionEvent actionEvent) throws DatabaseException {
        if (inputChecking() != null) {
            vehicleDao.add(inputChecking());
            // TODO: ???
            controller.getMyDetailController().loadUserVehicle();
            clearVehicleSelect(null);

        }
    }

    /**
     * Check the input validation if valid return a vehicle if not return null.
     * @return the vehicle that has all the essential data.
     */
    public Vehicle inputChecking() {
        String error = "";

        String make = null;
        int year = -1;
        String model = null;
        String plugType = null;

        if (inputVehicleMake.getValue() != null && inputVehicleMake.getValue() != "other") {
            make = (String) inputVehicleMake.getValue();
        } else {
            if (Validity.checkName(inputTextOfMake.getText())) {
                make = inputTextOfMake.getText();
            } else {
                error += "Please input a valid make \n";
            }
        }
        if (inputVehicleYear.getValue() != null && inputVehicleYear.getValue() != "other") {
            year = Integer.parseInt((String) inputVehicleYear.getValue());
        } else {
            if (Validity.checkVehicleYear(inputTextOfYear.getText())) {
                year = Integer.parseInt(inputTextOfYear.getText());
            } else {
                error += "Please input a valid year \n";
                //AlertMessage.createMessage("Invalid vehicle data", "Please input a numeric year");
            }
        }
        if (inputVehicleModel.getValue() != null && inputVehicleModel.getValue() != "other") {
            model = (String) inputVehicleModel.getValue();
        } else {
            if (Validity.checkUserName(inputTextOfModel.getText())) {
                model = inputTextOfModel.getText();
            } else {
                error += "Please input a valid model of vehicle\n";
            }
        }

        if (inputChargerType.getValue() != null && inputChargerType.getValue() != "other") {
            plugType = (String) inputChargerType.getValue();
        } else {
            if (Validity.checkPlugType(inputTextOfChargerType.getText())) {
                plugType = inputTextOfChargerType.getText();
            } else {
                error += "Please input a valid plug type of vehicle\n";
            }
        }


        if (make != null && year != -1 && model != null && plugType != null) {

            return new Vehicle(make, model, plugType, year, controller.getCurrentUserId());

        } else {
            AlertMessage.createMessage("Invalid vehicle data", error);
            return null;
        }

    }

    /**
     * If user input other of make that need to disable the choice of other choice box.
     */
    public void resetInputMake() {
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


    /**
     * Update the vehicle with the new data.
     * @param actionEvent event handler of click.
     * @throws DatabaseException the database error that might occur.
     */
    public void updateEditVehicle(ActionEvent actionEvent) throws DatabaseException {
        if (inputChecking() != null) {

            Vehicle vehicle = inputChecking();
            editVehicle.setMake(vehicle.getMake());
            editVehicle.setModel(vehicle.getModel());
            editVehicle.setYear(vehicle.getYear());
            editVehicle.setPlugType(vehicle.getPlugType());
            vehicleDao.update(editVehicle);
            controller.getMyDetailController().loadUserVehicle();

            editVehicle = null;
            btnConfirmEdit.setVisible(false);
            btnConfirmEdit.setDisable(true);
            submitVehicleButton.setDisable(false);
            clearVehicleSelect(null);


        }
    }

    public void setEditVehicle(Vehicle editVehicle) {
        this.editVehicle = editVehicle;
    }
}
