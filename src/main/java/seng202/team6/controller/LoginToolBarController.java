package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginToolBarController implements ScreenController {

    @FXML
    public Button signUpToolBarButton;
    @FXML
    public Button logInToolBarButton;
    private MainScreenController controller;

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
