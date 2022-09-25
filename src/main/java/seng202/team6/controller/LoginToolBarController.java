package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LoginToolBarController implements ScreenController {
    private MainScreenController controller;

    public void loadSignUp(ActionEvent actionEvent) {
        controller.loadSignUpViewAndToolBars();
    }

    public void loadLogin(ActionEvent actionEvent) {
        controller.loadLoginViewAndToolBars(null);
    }

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }
}
