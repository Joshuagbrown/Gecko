package seng202.team6.cucumber.userStepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.UserDao;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.DataService;
import seng202.team6.testfx.controllertests.TestFXBase;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class UserStepDef extends TestFXBase {
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
    @Given("User logged in with {string} and {string}")
    public void userLoggedInWithAnd(String username, String password) {
        clickOn("#loginPageBtn");
        clickOn("#usernameLogin");
        write(username);
        clickOn("#passwordLogin");
        write(password);
        clickOn("#logInButton");
    }

    @When("User register vehicle with make {string}, year {string},model {string}, charger type {string}")
    public void userRegisterVehicleWithMakeYearModelChargerType(String make, String model, String year, String chargerType) throws DatabaseException {

        clickOn("#loginPageBtn");
        clickOn("#registerVehicleButton");
        clickOn("#inputVehicleMake");
        moveBy(10,20);
        clickOn();

        clickOn();clickOn();
        clickOn();
        clickOn();
        clickOn();
        clickOn();





        clickOn("#inputTextOfMake");
        write(make);
        clickOn("#inputTextOfYear");
        write(year);
        clickOn("#inputTextOfModel");
        write(model);
        clickOn("#inputChargerType");
        moveBy(10,20);
        clickOn("#inputTextOfChargerType");
        write(chargerType);

        clickOn("#submitVehicleButton");


    }




    @Then("User has the vehicle in the its acctount with make {string}, year {string},Model {string}, charger type {string}")
    public void userHasTheVehicleInTheItsAcctountWithMakeYearModelChargerType(String arg0, String arg1, String arg2, String arg3) {




    }
}
