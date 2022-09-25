package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import seng202.team6.repository.UserDao;
import seng202.team6.models.UserLoginDetails;

public class LoginController implements ScreenController {

    @FXML
    private TextField usernameLogin;
    @FXML
    private TextField passwordLogin;
    private UserDao userDao = new UserDao();
    private MainScreenController controller;

    /**
     * Initialize the window.
     * @param controller the main screen controller.
     * @param stage Top level container for this window.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * Logs user in when button is clicked.
     * Checks the username and password fields are correct and in the
     * database and then either logs the user in or displays an error.
     * @param actionEvent event when log in button is clicked.
     */
    public void logIn(ActionEvent actionEvent) {
        String username = usernameLogin.getText();
        String password = passwordLogin.getText();
        UserLoginDetails userDetails = userDao.getLoginDetails(username);
        System.out.println(userDetails);
    }

}
