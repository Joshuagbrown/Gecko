package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.models.User;
import seng202.team6.services.Validity;
import java.io.IOException;

public class MyDetailsController implements ScreenController {


    public TextField usernameField;
    public TextField homeAddressField;
    public TextField nameField;
    public Button confirmEditButton;
    public Button editDetailsButton;
    public Button cancelEditButton;
    private MainScreenController controller;
    private String name = null;
    private String address = null;
    private Validity valid;


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
    }

    /**
     * Allows you to make changes to your details.
     * @param actionEvent When Edit details button is clicked
     */
    public void editDetails(ActionEvent actionEvent) {
        confirmEditButton.setVisible(true);
        cancelEditButton.setVisible(true);
        nameField.setEditable(true);
        homeAddressField.setEditable(true);
    }

    /**
     * Changes the user details with the new details.
     * @param actionEvent When confirm button is clicked
     * @throws IOException if address is invalid
     * @throws InterruptedException
     */
    public void confirmEditButton(ActionEvent actionEvent) throws IOException, InterruptedException {
        User user = controller.getCurrentUser();
        if (Validity.checkName(nameField.getText())) {
            name = nameField.getText();
            user.setName(name);
        } else {
            nameField.setText("Invalid Name");
        }
        if (valid.checkAddress(homeAddressField.getText())) {
            address = homeAddressField.getText();
            user.setAddress(address);
        } else {
            homeAddressField.setText("Invalid Address");
        }
        if (address != null && name != null) {
            try {
                controller.getDataService().updateUser(user);
            } catch (Exception e) {
                AlertMessage.createMessage("testing", "failed to update user information");
            }
            nameField.setEditable(false);
            homeAddressField.setEditable(false);
            confirmEditButton.setVisible(false);
            cancelEditButton.setVisible(false);

        }
    }

    public void cancelEditButton(ActionEvent actionEvent) {
        User user = controller.getCurrentUser();
        nameField.setText(user.getName());
        homeAddressField.setText(user.getAddress());
        nameField.setEditable(false);
        homeAddressField.setEditable(false);
        confirmEditButton.setVisible(false);
        cancelEditButton.setVisible(false);
    }
}
