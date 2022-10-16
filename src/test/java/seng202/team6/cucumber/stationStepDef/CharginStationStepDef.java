package seng202.team6.cucumber.stationStepDef;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.PointQuery;
import seng202.team6.controller.LoginController;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.exceptions.InstanceAlreadyExistsException;
import seng202.team6.models.*;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.StationDao;
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
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class CharginStationStepDef extends TestFXBase{


    static DatabaseManager manager;
    static UserDao userDao;

    static StationDao stationDao;



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

  public void createStationForTest() throws DatabaseException {
        stationDao = new StationDao();
        List<Charger> chargerList1 = Arrays.asList(
                new Charger("Type 2 Socketed", "Opermainative", 22));
        stationDao.add(new Station(new Position(-43.73745,170.100913), "YHA MT COOK",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "4 Kitchener Dr, Mount Cook National Park 7999, New Zealand" ,
                0, true, chargerList1,1,false,
                true,false));

        List<Charger> chargerList2 = Arrays.asList(
                new Charger("Type 2 Socketed", "Operative", 44));
        stationDao.add(new Station(new Position(-43.59049,172.630201), "CHRISTCHURCH ADVENTURE PARK",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "Worsleys Rd, Cashmere, Christchurch 8022, New Zealand" ,
                0, false, chargerList2,4,false,
                false,true));


        List<Charger> chargerList3 = Arrays.asList(
                new Charger("Type 2 Socketed", "Operative", 44));
        stationDao.add(new Station(new Position(-44.303657,171.225107), "TIMARU AIRPORT",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "186 Falvey Road, Levels 7975, New Zealand" ,
                0, false, chargerList3,4,false,
                false,false));


        List<Charger> chargerList4 = Arrays.asList(
                new Charger("Type 2 Socketed", "Operative", 7));
        stationDao.add(new Station(new Position(-40.721068,175.639788), "PUKAHA NATIONAL WILDLIFE CENTRE",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "85379 State Highway 2, Mount Bruce 5881" ,
                0, true, chargerList4,2,false,
                false,true));


        List<Charger> chargerList5 = Arrays.asList(
                new Charger("Type 2 Socketed", "Operative", 44));
        stationDao.add(new Station(new Position(-43.557139,172.680089), "THE TANNERY",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "3 Garlands Road, Woolston, Christchurch 8023" ,
                240, true, chargerList5,45,false,
                false,false));

    }

    @Before
    @Override
    public void setUpClass() throws Exception {
        DatabaseManager.removeInstance();
        manager = DatabaseManager.initialiseInstanceWithUrl("jdbc:sqlite:database-test.db");
        userDao = new UserDao();
        userDao.add(user());
        createStationForTest();
        ApplicationTest.launch(MainApplication.class);
    }


    @After
    public void breakDownClass() {
        manager.resetDB();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }


    @Given("User is on the data page")
    public void userIsOnTheDataPage() {
        clickOn("#dataButton");
        verifyThat("#dataToolBarScreen", Node::isVisible );
    }


    @When("User click the second station on the station table which name is")
    public void userClickTheSecondStationOnTheStationTableWhichNameIs() {

        clickOn("#table");



    }

    @Then("the user can see the information in the text area of the main screen")
    public void theUserCanSeeTheInformationInTheTextAreaOfTheMainScreen() {

        TableView<?> table = find("#table");
        table.getItems().get(2);

//        javafx.scene.control.TextArea textArea = (javafx.scene.control.TextArea) find("#textAreaInMainScreen");
        Assertions.assertEquals(table.getItems().get(1).toString(),
                "Station Name : CHRISTCHURCH ADVENTURE PARK\n" +
                        "Coordinate : -43.59049,172.630201\n" +
                        "Operator : MERIDIAN ENERGY LIMITED\n" +
                        "Owner : MERIDIAN ENERGY LIMITED\n" +
                        "Address : Worsleys Rd, Cashmere, Christchurch 8022, New Zealand\n" +
                        "Time Limit : 0\n" +
                        "Is 24 Hour : false\n" +
                        "Chargers : \n" +
                        "44 kW Type 2 Socketed, Status: Operative\n" +
                        "Number Of CarPark : 4\n" +
                        "CarPark Cost : false\n" +
                        "Charging Cost : false\n" +
                        "Has Tourist Attraction : true");
    }

    @When("User filter the station by the station name {string} and choose {int} hour operative and no charging cost and has tourist attriction")
    public void userFilterTheStationByTheStationNameAndChooseHourOperativeAndNoChargingCost(String name, int arg1) {
        clickOn("#inputStationName");
        write(name);
        clickOn("#is24HourCheckBox");
        clickOn("#hasChargingCostCheckBox");
        clickOn("#hasTouristAttractionCostCheckBox");
        clickOn("#filterButton");



    }

    @Then("User has no vehicle on the table as no vehicle meet the situation.")
    public void userHasNoVehicleOnTheTableAsNoVehicleMeetTheSituation() {
        TableView<?> table = find("#table");
        Assertions.assertEquals("Station Name : PUKAHA NATIONAL WILDLIFE CENTRE\n" +
                "Coordinate : -40.721068,175.639788\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 85379 State Highway 2, Mount Bruce 5881\n" +
                "Time Limit : 0\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "7 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 2\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : false\n" +
                "Has Tourist Attraction : true",table.getItems().get(0).toString());
    }


}
