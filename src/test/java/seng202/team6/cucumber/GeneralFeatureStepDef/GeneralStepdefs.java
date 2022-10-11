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

        verifyThat("#dataPageBtn", Node::isVisible);
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
        verifyThat("#loginHelpButton", Node::isVisible);

    }

}
