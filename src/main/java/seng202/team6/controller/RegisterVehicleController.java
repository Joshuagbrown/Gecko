package seng202.team6.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.AlertMessage;
import seng202.team6.services.Validity;


public class RegisterVehicleController implements ScreenController {
    private final Logger log = LogManager.getLogger();
    @FXML
    private Button confirmButton;
    @FXML

    private ComboBox<String> inputChargerType;
    @FXML
    private ComboBox<String> inputVehicleMake;
    @FXML
    private ComboBox<String> inputVehicleModel;
    @FXML
    private ComboBox<String> inputVehicleYear;

    @FXML
    private TextField inputTextOfMake;
    @FXML
    private TextField inputTextOfYear;
    @FXML
    private TextField inputTextOfModel;
    @FXML
    private TextField inputTextOfChargerType;
    private MainScreenController controller;
    private Vehicle editVehicle;
    private List<Vehicle> vehicles;
    private VehicleDao vehicleDao = new VehicleDao();
    private final String otherString = "other";
    private Stage stage;


    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
        vehicles = controller.getVehicles();

        loadPlugType();
        loadVehicleDataAndActionHandler();
    }

    private List<String> getMakes() {
        return vehicles.stream()
            .map(Vehicle::getMake)
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getYears(String make) {
        return vehicles.stream()
            .filter(v -> v.getMake().equals(make))
            .map(Vehicle::getYear)
            .map(String::valueOf)
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getModels(String make, int year) {
        return vehicles.stream()
            .filter(v -> v.getMake().equals(make))
            .filter(v -> v.getYear() == year)
            .map(Vehicle::getModel)
            .distinct()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<String> getPlugTypes() {
        return vehicleDao.getPlugType();
    }

    /**
     * load the vehicle page with the vehicle that want to edit.
     * @param vehicle the vehicle that want to edit the data.
     */
    public void loadEditVehicle(Vehicle vehicle) {

        inputVehicleMake.valueProperty().set(vehicle.getMake());
        inputVehicleYear.valueProperty().set(String.valueOf(vehicle.getYear()));

        inputVehicleModel.valueProperty().set(vehicle.getModel());
        inputChargerType.valueProperty().set(vehicle.getPlugType());

    }


    private void loadVehicleDataAndActionHandler() {
        loadMake();
        inputVehicleMake.setOnAction(event -> {
            if (Objects.equals(inputVehicleMake.getValue(), otherString)) {

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
                loadYear(inputVehicleMake.getValue());

            }
        });

        inputVehicleYear.setOnAction(event -> {
            if (Objects.equals(inputVehicleYear.getValue(), otherString)) {

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

                int value = -1;
                if (inputVehicleYear.getValue() != null) {
                    value = Integer.parseInt(inputVehicleYear.getValue());
                }
                loadModel(inputVehicleMake.getValue(), value);
            }
        });

        inputVehicleModel.setOnAction(event -> {

            if (Objects.equals(inputVehicleModel.getValue(), otherString)) {
                inputTextOfModel.setVisible(true);

            } else {
                inputTextOfModel.setVisible(false);
            }
        });
        inputChargerType.setOnAction(event -> {

            if (Objects.equals(inputChargerType.getValue(), otherString)) {
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
        List<String> makeList = getMakes();
        makeList.add("other");
        inputVehicleMake.getItems().setAll(makeList);
    }

    /**
     * load the year of vehicle into the choice box with related make.
     * @param make the vehicle make that determine the vehicle year.
     */
    public void loadYear(String make) {
        List<String> yearList = getYears(make);
        yearList.add("other");
        inputVehicleYear.getItems().setAll(yearList);
    }

    /**
     * load the vehicle model into the choice box with related year and make.
     * @param make the vehicle make that determine the vehicle model.
     * @param year the vehicle year that determine the vehicle model.
     */
    public void loadModel(String make, int year) {
        List<String> modelList = getModels(make, year);
        modelList.add("other");
        inputVehicleModel.getItems().setAll(modelList);
    }

    /**
     * load the possible plug type into the choice box.
     */
    public void loadPlugType() {
        List<String> plugType = getPlugTypes();
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
     */
    public void submitVehicle(ActionEvent actionEvent) {
        if (inputChecking() != null) {
            try {
                vehicleDao.add(inputChecking());
            } catch (DatabaseException e) {
                AlertMessage.createMessage("Error", "An error occurred adding a vehicle to the "
                                                    + "database. Please see the log for"
                                                    + " more details.");
                log.error("Error adding vehicle to database", e);
            }
            controller.getMyDetailsController().loadUserVehicle();
            clearVehicleSelect(null);
            stage.close();
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

        if (inputVehicleMake.getValue() != null && !Objects.equals(inputVehicleMake.getValue(),
                otherString)) {
            make = inputVehicleMake.getValue();
        } else {
            if (Validity.checkName(inputTextOfMake.getText())) {
                make = inputTextOfMake.getText();
            } else {
                error += "Please input a valid make \n";
            }
        }
        if (inputVehicleYear.getValue() != null && !Objects.equals(inputVehicleYear.getValue(),
                otherString)) {
            year = Integer.parseInt(inputVehicleYear.getValue());
        } else {
            if (Validity.checkVehicleYear(inputTextOfYear.getText())) {
                year = Integer.parseInt(inputTextOfYear.getText());
            } else {
                error += "Please input a valid year \n";
            }
        }
        if (inputVehicleModel.getValue() != null && !Objects.equals(inputVehicleModel.getValue(),
                otherString)) {
            model = inputVehicleModel.getValue();
        } else {
            if (inputTextOfModel.getText() != "") {
                model = inputTextOfModel.getText();
            } else {
                error += "Please input a valid model of vehicle\n";
            }
        }

        if (inputChargerType.getValue() != null && !Objects.equals(inputChargerType.getValue(),
                otherString)) {
            plugType = inputChargerType.getValue();
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
     */
    public void updateEditVehicle(ActionEvent actionEvent) {
        if (inputChecking() != null) {

            Vehicle vehicle = inputChecking();
            editVehicle.setMake(vehicle.getMake());
            editVehicle.setModel(vehicle.getModel());
            editVehicle.setYear(vehicle.getYear());
            editVehicle.setPlugType(vehicle.getPlugType());
            vehicleDao.update(editVehicle);
            controller.getMyDetailsController().loadUserVehicle();

            editVehicle = null;
            clearVehicleSelect(null);
            stage.close();


        }
    }


    /**
     * Function to set the vehicle to edit.
     * @param editVehicle the vehicle to edit
     */
    public void setEditVehicle(Vehicle editVehicle) {
        this.editVehicle = editVehicle;
    }


    /**
     * Sets confirm button to handle adding a new vehicle.
     */
    public void swapToAddVehicle() {
        confirmButton.setOnAction(e -> submitVehicle(e));
        confirmButton.setText("Add New Vehicle");
    }


    /**
     * Sets confirm button to handle editing a vehicle.
     */
    public void swapToEditVehicle() {
        confirmButton.setOnAction(e -> updateEditVehicle(e));
        confirmButton.setText("Confirm Edit");
    }
}
