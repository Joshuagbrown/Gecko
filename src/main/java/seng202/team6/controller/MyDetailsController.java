package seng202.team6.controller;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.Validity;

public class MyDetailsController implements ScreenController {


    public TextField usernameField;
    public TextField homeAddressField;
    public TextField nameField;
    public Button confirmEditButton;
    public Button editDetailsButton;
    public Button cancelEditButton;
    @FXML
    private Text invalidNameMyDetails;
    @FXML
    private Text invalidAddressMyDetails;

    public TableView<Vehicle> userVehicleTable;
    public TableColumn<Vehicle, String> make;
    public TableColumn<Vehicle, String> plugType;
    public TableColumn<Vehicle, String> model;
    public TableColumn<Vehicle, Integer> year;
    public Button btnAddVehicle;
    public Button btnEditVehicle;
    public Button btnDeleteVehicle;
    private MainScreenController controller;
    private String name = null;
    private String address = null;
    private Validity valid;

    private VehicleDao vehicleDao = new VehicleDao();


    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        valid = new Validity(controller);
    }

    /**
     * loads the users data into the fields.
     */
    public void loadUserData() {
        usernameField.setText(controller.getCurrentUser().getUsername());
        nameField.setText(controller.getCurrentUser().getName());
        homeAddressField.setText(controller.getCurrentUser().getAddress());
        loadUserVehicle();
    }

    /**
     * load the user vehicles with user id.
     */
    public void loadUserVehicle() {

        userVehicleTable.getItems().clear();
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        plugType.setCellValueFactory(new PropertyValueFactory<>("plugType"));

        List<Vehicle> vehicles = vehicleDao.getUserVehicle(controller.getCurrentUserId());

        userVehicleTable.getItems().addAll(vehicles);

    }

    /**
     * Allows you to make changes to your details.
     * @param actionEvent When Edit details button is clicked
     */
    public void editDetails(ActionEvent actionEvent) {
        editDetailsButton.setVisible(false);
        confirmEditButton.setVisible(true);
        cancelEditButton.setVisible(true);
        nameField.setEditable(true);
        homeAddressField.setEditable(true);
    }

    /**
     * Changes the user details with the new details.
     * @param actionEvent When confirm button is clicked
     * @throws IOException if address is invalid
     * @throws InterruptedException thrown by check address
     */
    public void confirmEditButton(ActionEvent actionEvent)
            throws IOException, InterruptedException {
        User user = controller.getCurrentUser();
        if (Validity.checkName(nameField.getText())) {
            invalidNameMyDetails.setVisible(false);
            name = nameField.getText();
            user.setName(name);
            nameField.setStyle(null);
        } else {
            nameField.setText(user.getName());
            nameField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            invalidNameMyDetails.setVisible(true);
        }
        if (valid.checkAddress(homeAddressField.getText())) {
            invalidAddressMyDetails.setVisible(false);
            address = homeAddressField.getText();
            user.setAddress(address);
            homeAddressField.setStyle(null);
        } else {
            homeAddressField.setText(user.getAddress());
            invalidAddressMyDetails.setVisible(true);
            homeAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (address != null && name != null) {
            try {
                controller.getDataService().updateUser(user);
            } catch (Exception e) {
                AlertMessage.createMessage("testing", "failed to update user information");
            }
            resetDefault();
        }
    }

    /**
     * Resets the textfields to not editable,
     * the buttons to just show edit,
     * and the error messages to not visible.
     */
    private void resetDefault() {
        nameField.setEditable(false);
        homeAddressField.setEditable(false);
        editDetailsButton.setVisible(true);
        confirmEditButton.setVisible(false);
        cancelEditButton.setVisible(false);
        invalidNameMyDetails.setVisible(false);
        invalidAddressMyDetails.setVisible(false);
        nameField.setStyle(null);
        homeAddressField.setStyle(null);
        name = null;
        address = null;
    }

    /**
     * Cancel editing my details.
     */
    public void cancelEditButton(ActionEvent actionEvent) {
        User user = controller.getCurrentUser();
        nameField.setText(user.getName());
        homeAddressField.setText(user.getAddress());
        resetDefault();
    }

    /**
     * Register vehicle page is show when user click the add vehicle button.
     * @param actionEvent the click action of add button.
     */
    public void addVehicleEventHandler(ActionEvent actionEvent) {


        controller.getMyDetailsToolBarController().loadRegisterVehicle(null);
        controller.getRegisterVehicleController().btnConfirmEdit.setDisable(true);
        controller.getRegisterVehicleController().submitVehicleButton.setDisable(false);
    }

    /**
     * Edit vehicle page is show when user click the edit vehicle button.
     * @param actionEvent the click action of add button.
     */
    public void editVehicleEventHandler(ActionEvent actionEvent) {


        if (userVehicleTable.getSelectionModel().getSelectedItem() != null) {
            controller.getMyDetailsToolBarController().loadRegisterVehicle(null);
            controller.getRegisterVehicleController().loadEditVehicle(
                    userVehicleTable.getSelectionModel().getSelectedItem());
            controller.getRegisterVehicleController().setEditVehicle(
                    userVehicleTable.getSelectionModel().getSelectedItem());
            controller.getRegisterVehicleController().submitVehicleButton.setDisable(true);
            controller.getRegisterVehicleController().btnConfirmEdit.setDisable(false);

        } else {
            AlertMessage.createMessage("Please selest a vehicle to edit",
                    "Please click the vehicle table to select one vehicle.");
        }

    }

    /**
     * User delete the vehicle from the table list.
     * @param actionEvent  the click action of delete button.
     */
    public void deleteVehicleEventHandler(ActionEvent actionEvent) {
        VehicleDao vehicleDao = new VehicleDao();
        if (userVehicleTable.getSelectionModel().getSelectedItem() != null) {
            try {
                vehicleDao.delete(userVehicleTable
                        .getSelectionModel().getSelectedItem().getVehicleId());
                controller.getRegisterVehicleController().setEditVehicle(
                        userVehicleTable.getSelectionModel().getSelectedItem());
                loadUserVehicle();
            } catch (DatabaseException e) {
                AlertMessage.createMessage("Error", "Couldn't find vehicle to delete");
            }


        } else {
            AlertMessage.createMessage("Please select a vehicle to delete",
                    "Please click the vehicle table to select one vehicle.");
        }

    }
}
