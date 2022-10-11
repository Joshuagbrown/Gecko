package seng202.team6.cucumber.userStepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.Vehicle;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.UserDao;
import seng202.team6.repository.VehicleDao;
import seng202.team6.services.DataService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class UserStepDef {
    MainScreenController mainScreenController;
    static DatabaseManager manager;
    static UserDao userDao;

    VehicleDao vehicleDao = new VehicleDao();


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
    public void setUpClass() throws Exception {

        DataService dataService = new DataService();
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
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



    @When("User register vehicle with make {string}, year {string},Model {string}, charger type {string}")
    public void userRegisterVehicleWithMakeYearModelChargerType(String arg0, String arg1, String arg2, String arg3) throws DatabaseException {

        VehicleDao vehicleDao = new VehicleDao();
        vehicleDao.add(new Vehicle(arg0,arg2,arg3,Integer.parseInt(arg1), mainScreenController.getCurrentUserId()));
    }


    @Given("User logged in with {string} and passward for user feature")
    public void userLoggedInWithAndPasswardForUserFeature(String arg0) {
        mainScreenController.loginUser(userDao.getOne(userDao.getLoginDetails(arg0).getUserId()));
        Assertions.assertEquals(mainScreenController.getCurrentUserId(),0);
        Assertions.assertNotEquals(mainScreenController.getCurrentUserId(),0);
    }

    @Then("User has the vehicle in the its acctount with make {string}, year {string},Model {string}, charger type {string}")
    public void userHasTheVehicleInTheItsAcctountWithMakeYearModelChargerType(String arg0, String arg1, String arg2, String arg3) {

        Vehicle vehicle = vehicleDao.getUserVehicle(mainScreenController.getCurrentUserId()).get(0);
        Assertions.assertEquals(arg0,vehicle.getMake());
        Assertions.assertEquals(Integer.parseInt(arg1),vehicle.getYear());
        Assertions.assertEquals(arg2,vehicle.getModel());
        Assertions.assertEquals(arg3,vehicle.getPlugType());

    }
}
