package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Controller class for the login toolbar.
 */
public class LoginToolBarController implements ScreenController {

    @FXML
    public Button signUpToolBarButton;
    @FXML
    public Button logInToolBarButton;
    private MainScreenController controller;


    /**
     * Funciton to initialize the login toolbar screen.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * Load the signup page.
     */
    public void loadSignUp() {
        controller.loadSignUpViewAndToolBars();
        signUpToolBarButton.setStyle("-fx-border-color: #FFFFFF");
        logInToolBarButton.setStyle("");
    }

    /**
     * Load the login page.
     */
    public void loadLogin() {
        controller.loginButtonEventHandler(null);
        signUpToolBarButton.setStyle("");
        logInToolBarButton.setStyle("-fx-border-color: #FFFFFF");
    }


}
