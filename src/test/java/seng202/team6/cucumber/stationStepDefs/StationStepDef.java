package seng202.team6.cucumber.stationStepDefs;


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
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.LoginController;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.Journey;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.UserDao;
import seng202.team6.services.DataService;
import seng202.team6.testfx.controllertests.TestFXBase;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;


public class StationStepDef extends TestFXBase {

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        DataService dataService = new DataService();
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = baseLoader.load();
        mainScreenController = baseLoader.getController();
        mainScreenController.init(null, dataService);

        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        userDao = new UserDao();
        userDao.add(user());
    }





    @After
    public void breakDownClass() {
        manager.resetDB();
    }

    @Override
    public void start(Stage stage) throws Exception {


    }

    @When("The user save the start point {string} and end point {string} and save the journey")
    public void theUserSaveTheStartPointAndEndPointAndSaveTheJourney(String start, String end) throws DatabaseException {

        List<String> address = Arrays.asList(start,end);
        mainScreenController.getDataService().addJourney(
                new Journey(address,String.valueOf(mainScreenController.getCurrentUserId())));
    }

    @Then("the user have save journey with the start {string} and end point {string}")
    public void theUserHaveSaveJourneyWithTheStartAndEndPoint(String start, String end) {
        Map<Integer, Journey> journeyMap = mainScreenController.getDataService().fetchJourneyData(
                mainScreenController.getCurrentUser().getUsername());
        Assertions.assertEquals(journeyMap.get(1).getAddresses().get(0),start);
        Assertions.assertEquals(journeyMap.get(1).getAddresses().get(1),end);
    }


    @Given("User logged in with {string} and {string}")
    public void userLoggedInWithAnd(String arg0, String arg1) {
        mainScreenController.loginUser(userDao.getOne(userDao.getLoginDetails(arg0).getUserId()));
        Assertions.assertEquals(mainScreenController.getCurrentUserId(),0);


        Assertions.assertNotEquals(mainScreenController.getCurrentUserId(),0);
    }
}
