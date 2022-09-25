package seng202.team6.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import seng202.team6.exceptions.DuplicateEntryException;
import seng202.team6.models.User;

public class SignUpController implements ScreenController {
    private MainScreenController controller;
    @FXML
    private TextField usernameSignUp;
    @FXML
    private PasswordField passwordSignUp;
    @FXML
    private TextField nameSignUp;
    @FXML
    private TextField addressSignUp;
    @FXML
    private Button signUpButton;
    @FXML
    private Button registerVehicleButtonSignUp;
    @FXML
    private Text invalidUsername;
    @FXML
    private Text invalidName;
    @FXML
    private Text invalidPassword;
    @FXML
    private Text invalidAddress;
    private String username = null;
    private String name = null;
    private String password = null;
    private String address = null;

    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * This function add a creates a new user with the details given.
     * @param actionEvent When the sign-up button is clicked.
     * @throws NoSuchAlgorithmException If the algorithm doesn't exist.
     * @throws InvalidKeySpecException If the key is invalid.
     */
    public void signUp(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException, DuplicateEntryException, IOException, InterruptedException {
        if (usernameSignUp.getText().matches("[a-zA-Z0-9]+")
                && !Objects.equals(usernameSignUp.getText(), "")) {
            username = usernameSignUp.getText();
            invalidUsername.setVisible(false);
        } else {
            usernameSignUp.setText("");
            invalidUsername.setVisible(true);
        }
        if (nameSignUp.getText().matches("[a-zA-Z\\s]+")) {
            name = nameSignUp.getText();
            invalidName.setVisible(false);
        } else {
            nameSignUp.setText("");
            invalidName.setVisible(true);
        }
        if (passwordSignUp.getText().length() >= 8) {
            password = passwordSignUp.getText();
            invalidPassword.setVisible(false);
        } else {
            passwordSignUp.setText("");
            invalidPassword.setVisible(true);
        }
        if (controller.getMapToolBarController().geoCode(addressSignUp.getText()) != null) {
            address = addressSignUp.getText();
            invalidAddress.setVisible(false);
        } else {
            addressSignUp.setText("");
            invalidAddress.setVisible(true);
        }
        if (username != null && name != null && password != null && address != null) {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            User user = new User(username, hash, salt, address, name);
            controller.getDataService().addUser(user);
        }
    }
}
