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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.AlertMessage;
import seng202.team6.services.Validity;

public class MyDetailsController implements ScreenController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField homeAddressField;
    @FXML
    private TextField nameField;
    @FXML
    private Button confirmEditButton;
    @FXML
    private Button editDetailsButton;
    @FXML
    private Button cancelEditButton;
    @FXML
    private TableView<Vehicle> userVehicleTable;
    @FXML
    private TableColumn<Vehicle, String> make;
    @FXML
    private TableColumn<Vehicle, String> plugType;
    @FXML
    private TableColumn<Vehicle, String> model;
    @FXML
    private TableColumn<Vehicle, Integer> year;
    @FXML
    private Button btnAddVehicle;
    @FXML
    private Button btnEditVehicle;
    @FXML
    private Button btnDeleteVehicle;
    @FXML
    private Text invalidNameMyDetails;
    @FXML
    private Text invalidAddressMyDetails;

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
        confirmEditButton.setVisible(false);
        cancelEditButton.setVisible(false);
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
    public void confirmEditButtonEvent(ActionEvent actionEvent)
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
            controller.getMapController().setHomeAddress(address);
            homeAddressField.setStyle(null);
        } else {
            homeAddressField.setText(user.getAddress());
            invalidAddressMyDetails.setVisible(true);
            homeAddressField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
        if (address != null && name != null) {

            try {
                controller.getDataService().updateUser(user);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
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
    public void cancelEditButtonEvent() {
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
        controller.getRegisterVehicleController().swapToAddVehicle();
    }

    /**
     * Edit vehicle page is show when user click the edit vehicle button.
     * @param actionEvent the click action of edit button.
     */
    public void editVehicleEventHandler(ActionEvent actionEvent) {

        if (userVehicleTable.getSelectionModel().getSelectedItem() != null) {
            controller.setTextAreaInMainScreen(null);
            controller.getMyDetailsToolBarController().loadRegisterVehicle(null);
            controller.getRegisterVehicleController().swapToEditVehicle();
            controller.getRegisterVehicleController().loadEditVehicle(
                    userVehicleTable.getSelectionModel().getSelectedItem());
            controller.getRegisterVehicleController().setEditVehicle(
                    userVehicleTable.getSelectionModel().getSelectedItem());

        } else {
            AlertMessage.createMessage("No vehicle is currently selected.",
                    "Please select a vehicle in the table to edit it.");
        }

    }

    /**
     * User delete the vehicle from the table list.
     * @param actionEvent  the click action of delete button.
     */
    public void deleteVehicleEventHandler(ActionEvent actionEvent) {
        VehicleDao currentVehicleDao = new VehicleDao();
        if (userVehicleTable.getSelectionModel().getSelectedItem() != null) {
            try {
                currentVehicleDao.delete(userVehicleTable
                        .getSelectionModel().getSelectedItem().getVehicleId());
                controller.getRegisterVehicleController().setEditVehicle(
                        userVehicleTable.getSelectionModel().getSelectedItem());
                loadUserVehicle();
            } catch (DatabaseException e) {
                AlertMessage.createMessage("Error", "Could not find vehicle to delete");
            }


        } else {
            AlertMessage.createMessage("No vehicle is currently selected.",
                    "Please select a vehicle in the table to edit it.");
        }

    }
    /**
     * This sets the selected vehicle to what vehicle has been clicked on.
     *
     * @param mouseEvent when the user click the selected vehicle
     */

    public void clickItem(MouseEvent mouseEvent) {
        Vehicle selected = userVehicleTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            controller.setTextAreaInMainScreen(selected.toString());
        } else {
            controller.setTextAreaInMainScreen(null);
        }
    }
}
