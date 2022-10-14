package seng202.team6.cucumber.userStepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
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





    @Then("User has the vehicle in the its acctount with make {string}  , year {string} , model {string} input charger type {string}")
    public void userHasTheVehicleInTheItsAcctountWithMakeYearModelInputChargerType(String make, String year, String model, String type) {

        clickOn((Node) lookup("#year").nth(1).query());

        TextArea textArea = find("#textAreaInMainScreen");

        Assertions.assertEquals(String.format("Make : %s\nYear : %s\nModel : %s\nCharger Type : %s",make,year,model,type), textArea.getText());
    }


    @When("User register vehicle by choose make ARCIMOTO  , year {string} , model EVERGREEN input charger type {string}")
    public void userRegisterVehicleByChooseMakeARCIMOTOYearModelEVERGREENInputChargerType(String arg0, String chargerType) {
        clickOn("#loginPageBtn");
        clickOn("#registerVehicleButton");
        clickOn("#inputVehicleMake");
        moveBy(10,20);
        clickOn();

        clickOn("#inputVehicleYear");
        moveBy(10,20);
        clickOn();
        clickOn("#inputVehicleModel");
        moveBy(10,20);
        clickOn();
        clickOn("#inputChargerType");
        moveBy(10,20);
        clickOn();
        clickOn("#inputTextOfChargerType");
        write(chargerType);

        clickOn("#submitVehicleButton");
        clickOn("#quitButton");
    }

    @When("User change the register vehicle year to {string} , model {string}")
    public void userChangeTheRegisterVehicleYearToModel(String year, String model) {
        clickOn((Node) lookup("#make").nth(1).query());
        clickOn("#btnEditVehicle");

        clickOn("#inputVehicleYear");
        moveBy(10,20);
        moveBy(10,20);
        clickOn();
        clickOn("#inputTextOfYear");
        write(year);
        clickOn("#inputTextOfModel");
        write(model);


        clickOn("#btnConfirmEdit");
        clickOn("#quitButton");

        clickOn();
        clickOn();



        clickOn((Node) lookup("#year").nth(0).query());
        moveBy(10,20);
        clickOn();


    }

    @When("user selected the vehicle and delete it")
    public void userSelectedTheVehicleAndDeleteIt() {
        clickOn((Node) lookup("#make").nth(1).query());
        clickOn("#btnDeleteVehicle");

    }


    @Then("user has no vehicle in table.")
    public void userHasNoVehicleInTable() {
        clickOn((Node) lookup("#year").nth(0).query());
        moveBy(10,20);
        clickOn();
        TextArea textArea = find("#textAreaInMainScreen");

        Assertions.assertNull( textArea.getText());

    }

    @When("User go to the data page and select the first station and edit the station info by")
    public void userGoToTheDataPageAndSelectTheFirstStationAndEditTheStationInfoBy() {
        clickOn("#stationButton");
        clickOn("#nameField");
        clickOn("#addressField");
        clickOn("#timeLimitField");
        clickOn("#numParksField");
        clickOn("#viewChargersButton");
        clickOn("#wattageText");
        clickOn("#saveChanges");//save charger
        clickOn("#saveButton");

    }
}
