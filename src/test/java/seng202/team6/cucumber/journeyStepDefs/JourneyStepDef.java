package seng202.team6.cucumber.journeyStepDefs;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.PointQuery;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Journey;
import seng202.team6.models.User;
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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;


public class JourneyStepDef extends TestFXBase {
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

    @When("The user save the start point {string} and end point {string} and save the journey")
    public void theUserSaveTheStartPointAndEndPointAndSaveTheJourney(String start, String end) throws DatabaseException {

        //clickOn("#planTrip");
        clickOn("#startLocation");
        write(start);
        clickOn("#endLocation");
        write(end);
        clickOn("#saveJourneyCheck");
        clickOn("#findRouteButton");
    }

    @Then("the user have save journey with the start {string} and end point {string}")
    public void theUserHaveSaveJourneyWithTheStartAndEndPoint(String start, String end) {

        clickOn("#loginPageBtn");
        clickOn("#savedJourneyButton");
        clickOn("#journeyTable");
        clickOn((Node) lookup("#startPoint").nth(1).query());


        TableView<?> table = find("#journeyTable");
        Journey journey = (Journey) table.getItems().get(0);
        Assertions.assertEquals(journey.getStart(),start);
        Assertions.assertEquals(journey.getEnd(),end);

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


    @When("The user save the start point {string} and end point {string} and add {string} as a stop point and save the journey")
    public void theUserSaveTheStartPointAndEndPointAndAddAsAStopPointAndSaveTheJourney(String start, String end, String middle) {

        clickOn("#startLocation");
        write(start);
        clickOn("#addStopButton");

        clickOn("#endLocation");
        write(middle);
        clickOn("#addOneTextField3");
        write(end);

        clickOn("#saveJourneyCheck");
        clickOn("#findRouteButton");

    }

    @Then("the user have save journey with the start {string} and end point {string} and {string} as middle point")
    public void theUserHaveSaveJourneyWithTheStartAndEndPointAndAsMiddlePoint(String start, String end, String middle) {
        clickOn("#loginPageBtn");
        clickOn("#savedJourneyButton");
        clickOn("#journeyTable");

        TableView<?> table = find("#journeyTable");
        Journey journey = (Journey) table.getItems().get(0);
        Assertions.assertEquals(journey.getStart(),start);
        Assertions.assertEquals(journey.getEnd(),end);
        Assertions.assertEquals(journey.getAddresses().get(1),middle);

    }

    @And("User go to the save journey page in my detail")
    public void userGoToTheSaveJourneyPageInMyDetail() {
        clickOn("#loginPageBtn");
        clickOn("#savedJourneyButton");
        clickOn("#journeyTable");
        clickOn((Node) lookup("#startPoint").nth(1).query());
    }

    @And("User remove the routing from the plan trip page")
    public void userRemoveTheRoutingFromThePlanTripPage() {
        clickOn("#removeRouteButton");
        
    }

    @When("User choose that journey and show it on the map")
    public void userChooseThatJourneyAndShowItOnTheMap() {
        clickOn((Node) lookup("#startPoint").nth(1).query());
        clickOn("#showJourneyButton");
    }

    @Then("User can see the journey reloaded on the plan trip page")
    public void userCanSeeTheJourneyReloadedOnThePlanTripPage() {
        TextField textField = find("#startLocation");
        assertEquals(textField.getText(),"3 ilam road");
    }


    @When("User delete the first selected journey")
    public void userDeleteTheFirstSelectedJourney() {
        clickOn("#deleteJourneyButton");

    }




    @And("User click plan Trip")
    public void userClickPlanTrip() {
        clickOn("#planTrip");
    }
}
