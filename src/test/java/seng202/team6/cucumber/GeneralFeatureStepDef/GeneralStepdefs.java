package seng202.team6.cucumber.GeneralFeatureStepDef;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.LoginController;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.UserDao;
import seng202.team6.services.DataService;
import seng202.team6.testfx.controllertests.TestFXBase;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.HexFormat;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;


public class GeneralStepdefs extends TestFXBase {


    static DatabaseManager manager;
    static UserDao userDao;


    User user() throws NoSuchAlgorithmException, InvalidKeySpecException {


        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String password = "123456789";
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        User user = new User("admin", hash, salt, "54 Bellvue Avenue, Papanui 8052, New Zealand", "Name");

        return user;

    }

    @Before
    @Override
    public void setUpClass() throws Exception {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        userDao = new UserDao();
        userDao.add(user());
        ApplicationTest.launch(MainApplication.class);
    }





    @After
    public void breakDownClass() {
        manager.resetDB();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Given("User launch the the app")
    public void userLaunchTheTheApp() throws Exception {

        verifyThat("#dataButton", Node::isVisible);
    }

    @When("User click the help button")
    public void userClickTheHelpButton() {
        clickOn("#helpButton");
    }

    @Then("User can see the help page and related button")
    public void userCanSeeTheHelpPageAndRelatedButton() {
        verifyThat("#helpPage", Node::isVisible);
        verifyThat("#generalHelpButton", Node::isVisible);
        verifyThat("#mapHelpButton", Node::isVisible);
        verifyThat("#dataHelpButton", Node::isVisible);

    }

    @Given("User click the signup button in log in page")
    public void userClickTheSignupButtonInLogInPage() {
        clickOn("#loginPageBtn");
        clickOn("#signUpToolBarButton");
    }

    @When("User input {string} in user name and input {string} as password")
    public void userInputInUserNameAndInput(String arg0, String arg1) {
        clickOn("#usernameSignUp");
        write(arg0);
        clickOn("#passwordSignUp");
        write(arg1);
        clickOn("#confirmPassword");
        write(arg1);

    }

    @And("input address as {string} and signed up")
    public void inputAddressAsAndSignedUp(String arg0) {
        clickOn("#addressSignUp");
        write(arg0);
        clickOn("#signUpButton");

    }

    @And("input name as {string}")
    public void inputNameAs(String arg0) {

        clickOn("#nameSignUp");
        write(arg0);

    }



    @Then("the user is signed up with name {string} and address {string}")
    public void theUserIsSignedUpWithNameAndAddress(String arg0, String arg1) {
        clickOn("#loginPageBtn");
        TextField nameTextField = (TextField) find("#nameField");
        Assertions.assertEquals(arg0,nameTextField.getText());

        TextField homeTextField = (TextField) find("#homeAddressField");
        Assertions.assertEquals(arg1,homeTextField.getText());
    }
}
