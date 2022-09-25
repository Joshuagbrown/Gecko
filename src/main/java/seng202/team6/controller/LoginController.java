package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.repository.UserDao;
import seng202.team6.models.UserLoginDetails;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class LoginController implements ScreenController {

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
            byte[] hashedPassword = hashPassword(passwordLogin.getText(), userDetails.getPasswordSalt());
            if (hashedPassword == userDetails.getPasswordHash()) {
                System.out.println("Success");
            } else {
                passwordLogin.clear();
                errorText.setText("You have entered an invalid username or password");
            }
        } else {
            passwordLogin.clear();
            errorText.setText("You have entered an invalid username or password");
        }
    }

}
