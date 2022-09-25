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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.json.simple.JSONObject;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.DuplicateEntryException;
import seng202.team6.models.User;
import seng202.team6.services.Validity;

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
    private Validity valid;

    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        valid = new Validity(controller);
    }

    /**
     * This function add a creates a new user with the details given.
     * @param actionEvent When the sign-up button is clicked.
     * @throws NoSuchAlgorithmException If the algorithm doesn't exist.
     * @throws InvalidKeySpecException If the key is invalid.
     */
    public void signUp(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InterruptedException {
        if (Validity.checkUserName(usernameSignUp.getText())) {
            username = usernameSignUp.getText();
            invalidUsername.setVisible(false);
        } else {
            usernameSignUp.setText("");
            invalidUsername.setVisible(true);
        }
        if (Validity.checkName(nameSignUp.getText())) {
            name = nameSignUp.getText();
            invalidName.setVisible(false);
        } else {
            nameSignUp.setText("");
            invalidName.setVisible(true);
        }
        if (Validity.checkPassword(passwordSignUp.getText())) {
            password = passwordSignUp.getText();
            invalidPassword.setVisible(false);
        } else {
            passwordSignUp.setText("");
            invalidPassword.setVisible(true);
        }
        if (valid.checkAddress(addressSignUp.getText())) {
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
            try {
                controller.getDataService().addUser(user);
                controller.loginUser(user);
            } catch (DatabaseException e) {
                AlertMessage.createMessage("Invalid username", "Username already in use.\nPick a new username");
            }

        }
    }
}
