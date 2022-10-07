package seng202.team6.testfx.controllertests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.LoginController;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.services.DataService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;

public class LoginTestFX extends TestFXBase {
    MainScreenController mainScreenController;


    public LoginTestFX() throws IOException {

    }

    @Override
    public void setUpClass() throws Exception {
        ApplicationTest.launch(MainApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        DataService dataService = new DataService();
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = baseLoader.load();
        MainScreenController baseController = baseLoader.getController();
        baseController.init(stage, dataService);
        Parent page = loader.load();
        initState(loader, stage);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }

    public void initState(FXMLLoader loader, Stage stage) throws IOException {

        LoginController loginController = loader.getController();
        loginController.init(stage,mainScreenController);
    }

    @Test
    public void testLoginSuccess() {
        clickOn("#usernameLogin");
        write("pwl");
        clickOn("#passwordLogin");
        write("123456789");
        clickOn("#logInButton");
        // We know that once we log in the main screen has a button to log out, so if we can see this it must've logged us in correctly
        Assertions.assertNotNull(mainScreenController.getCurrentUser());
    }




    @AfterAll
    public static void killAllWindows(){
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
