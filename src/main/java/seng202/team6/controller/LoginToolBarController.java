package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginToolBarController implements ScreenController {
    private MainScreenController controller;

    /**
     * Load the signup page.
     */
    public void loadSignUp(ActionEvent actionEvent) {
        controller.loadSignUpViewAndToolBars();
    }

    /**
     * Load the login page.
     */
    public void loadLogin(ActionEvent actionEvent) {
        controller.loginButtonEventHandler(null);
    }

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }
}
