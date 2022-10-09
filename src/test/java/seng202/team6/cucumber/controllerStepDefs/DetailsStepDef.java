package seng202.team6.cucumber.controllerStepDefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
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
import seng202.team6.testfx.controllertests.TestFXBase;

import java.util.HexFormat;

import static org.junit.Assert.assertEquals;

public class DetailsStepDef extends TestFXBase {

    static MainScreenController mainScreenController;
    static DatabaseManager manager;
    static UserDao userDao = new UserDao();;

    static User user() {
        byte[] passwordHash = HexFormat.of().parseHex("C0E64B6AE3A5F1D293E28704712F3663");
        byte[] passwordSalt = HexFormat.of().parseHex("D03FEF3D6CE1AAEDF4255FEDE95DDEA8");

        return new User("user", passwordHash, passwordSalt,
                "54 Bellvue Avenue, Papanui 8052, New Zealand", "Name");
    }

    @Before
    @Override
    public void setUpClass() throws Exception {
        ApplicationTest.launch(MainApplication.class);
        setup();
    }

    public void setup() throws InstanceAlreadyExistsException, DatabaseException {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");

        userDao.add(user());
    }

    @Before
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

    @After
    public void breakDown() {
        manager.resetDB();
    }

    @Given("User is logged in")
    public void userIsLoggedIn() {
        UserLoginDetails userDetails = userDao.getLoginDetails(user().getUsername());
        mainScreenController.setCurrentUserId(userDetails.getUserId());
        mainScreenController.loginUser(userDao.getOne(userDetails.getUserId()));
    }

    @When("User clicks My Details button")
    public void userClicksMyDetailsButton() {
        mainScreenController.loginButtonEventHandler(null);
    }

    @Then("User can see the my details page")
    public void userCanSeeTheMyDetailsPage() {
        assertEquals(mainScreenController.getMyDetailsScreen(),mainScreenController.mainBorderPane.getCenter());
    }
}
