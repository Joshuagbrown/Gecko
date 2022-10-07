package seng202.team6.cucumber.controllerStepDefs;


import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.LoginController;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.testfx.controllertests.TestFXBase;

import static org.testfx.api.FxAssert.verifyThat;


public class LoginStepDefs extends TestFXBase {

    MainScreenController mainScreenController;

    @Before
    @Override
    public void setUpClass() throws Exception {
        ApplicationTest.launch(MainApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        mainScreenController = loader1.load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent page = loader.load();
        initState(loader, stage,mainScreenController);
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }
    public void initState(FXMLLoader loader, Stage stage,MainScreenController mainScreenController) {
        LoginController loginController = loader.getController();
        loginController.init(stage,mainScreenController);
    }

    @Given("I am on the login screen")
    public void iAmOnTheLoginScreen() {
        // We know that only the login screen has a login button, so if we can see we can't've logged in
        verifyThat("#logInButton", Node::isVisible);
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        clickOn("#usernameLogin");
        write(username);
        clickOn("#passwordLogin");
        write(password);
        clickOn("#logInButton");
    }

    @Then("I am logged in success")
    public void iAmLoggedInSuccess() {
        // We know that once we log in the main screen has a button to log out, so if we can see this it must've logged us in correctly

        Assertions.assertNotNull(mainScreenController.getCurrentUser());
        //verifyThat("#logInButton", Node::isVisible);
    }



}
