package seng202.team6.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.repository.UserDao;

public class LoginController implements ScreenController {
    @FXML
    public Button logInButton;
    public TextArea funFactBox;
    @FXML
    private TextField usernameLogin;
    @FXML
    private TextField passwordLogin;
    @FXML
    private Text errorText;
    private UserDao userDao = new UserDao();
    private MainScreenController controller;

    /**
     * Initialize the window.
     * @param controller the main screen controller.
     * @param stage Top level container for this window.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        errorText.setText("");
    }

    /**
     * Hashes the password.
     * @param password The password
     * @param salt The salt of the password
     * @return the hash.
     */
    private byte[] hashPassword(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Logs user in when button is clicked.
     * Checks the username and password fields are correct and in the
     * database and then either logs the user in or displays an error.
     * @param actionEvent event when log in button is clicked.
     */
    public void logIn(ActionEvent actionEvent) {
        String username = usernameLogin.getText();
        UserLoginDetails userDetails = userDao.getLoginDetails(username);
        if (userDetails != null) {
            byte[] hashedPassword = hashPassword(passwordLogin.getText(),
                    userDetails.getPasswordSalt());
            if (Arrays.equals(hashedPassword,userDetails.getPasswordHash())) {
                errorText.setText("");
                controller.setCurrentUserId(userDetails.getUserId());
                controller.loginUser(userDao.getOne(userDetails.getUserId()));
                passwordLogin.clear();
            } else {
                passwordLogin.clear();
                errorText.setText("You have entered an invalid username or password");
                usernameLogin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                passwordLogin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
        } else {
            passwordLogin.clear();
            errorText.setText("You have entered an invalid username or password");
            usernameLogin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            passwordLogin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
        }
    }

}
