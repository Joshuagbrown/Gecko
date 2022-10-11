package seng202.team6.cucumber.controllerStepDefs;


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


public class LoginStepDefs extends TestFXBase {

    MainScreenController mainScreenController;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        DataService dataService = new DataService();
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = baseLoader.load();
        mainScreenController = baseLoader.getController();
        mainScreenController.init(stage, dataService);
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

        verifyThat("#logOutButton", Node::isVisible);
        //Assertions.assertNotNull(mainScreenController.getCurrentUser());
        //verifyThat("#logInButton", Node::isVisible);
    }

    @Then("I am not logged in")
    public void iAmNotLoggedIn() {
        verifyThat("#errorText", Node::isVisible);
    }




    @When("User clicks My Details button")
    public void userClicksMyDetailsButton() {
        clickOn("#loginPageBtn");

    }

    @Then("User can see the my details page")
    public void userCanSeeTheMyDetailsPage() {
        verifyThat("#editDetailsButton", Node::isVisible);
    }


    @When("User clicks log out button")
    public void userClicksLogOutButton() {
        clickOn("#logOutButton");
    }

    @Given("User logged in with with username {string} and password {string} and in my detail page")
    public void userLoggedInWithWithUsernameAndPasswordAndInMyDetailPage(String username, String password) {
        userClicksMyDetailsButton();
        iAmOnTheLoginScreen();
        iLoginWithUsernameAndPassword(username, password);
        userClicksMyDetailsButton();

    }

    @When("User click edit button")
    public void userClickEditButton() {
        clickOn("#editDetailsButton");
    }

    @And("User click confirm edit button")
    public void userClickConfirmEditButton() {
        clickOn("#confirmEditButton");

    }

    @And("User input {string} on name space and input {string} on the address space")
    public void userInputOnNameSpaceAndInputOnTheAddressSpace(String name, String address) {
        doubleClickOn("#nameField");
        write(name);
        doubleClickOn("#homeAddressField");
        write(address);
    }

    @Then("User Name have changed into {string} and address has changed into {string}")
    public void userNameHaveChangedIntoAndAddressHasChangedInto(String name, String address) {


        TextField nameTextField = (TextField) find("#usernameField");
        Assertions.assertEquals(name,nameTextField.getText());

        TextField homeTextField = (TextField) find("#homeAddressField");
        Assertions.assertEquals(address,homeTextField.getText());


    }
}
