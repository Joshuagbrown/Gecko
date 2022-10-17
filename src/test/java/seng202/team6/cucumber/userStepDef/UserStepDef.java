package seng202.team6.cucumber.userStepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import seng202.team6.controller.MainApplication;
import seng202.team6.controller.MainScreenController;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.*;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.StationDao;
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
import java.sql.Time;
import java.util.Arrays;
import java.util.List;


public class UserStepDef extends TestFXBase {
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
                new Charger("Type 2 Socketed", "Operative", 22));
        stationDao.add(new Station(new Position(-43.73745,170.100913), "YHA MT COOK",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "4 Kitchener Dr, Mount Cook National Park 7999, New Zealand" ,
                0, true, chargerList1,1,false,
                true,false));

        List<Charger> chargerList2 = Arrays.asList(
                new Charger("Type 2 CCS", "Operative", 44));
        stationDao.add(new Station(new Position(-43.59049,172.630201), "CHRISTCHURCH ADVENTURE PARK",
                "MERIDIAN ENERGY LIMITED","MERIDIAN ENERGY LIMITED",
                "Worsleys Rd, Cashmere, Christchurch 8022, New Zealand" ,
                0, false, chargerList2,4,false,
                false,true));


        List<Charger> chargerList3 = Arrays.asList(
                new Charger("CHAdeMO", "Operative", 44));
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

    @Given("User logged in with {string} and {string}")
    public void userLoggedInWithAnd(String username, String password) {
        clickOn("#loginPageBtn");
        clickOn("#usernameLogin");
        write(username);
        clickOn("#passwordLogin");
        write(password);
        clickOn("#logInButton");
    }





    @Then("User has the vehicle in the its acctount with make {string}  , year {string} , model {string}, charger type {string}")
    public void userHasTheVehicleInTheItsAcctountWithMakeYearModelInputChargerType(String make, String year, String model, String type) {

        clickOn((Node) lookup("#year").nth(1).query());

        TextArea textArea = find("#textAreaInMainScreen");

        Assertions.assertEquals(String.format("Make : %s\nYear : %s\nModel : %s\nCharger Type : %s",make,year,model,type), textArea.getText());
    }


    @When("User register vehicle by choose make ARCIMOTO  , year {string} , model EVERGREEN choose charger type {string}")
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


        clickOn("#confirmButton");

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


        clickOn("#confirmButton");
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

    @And("User go to the data page")
    public void userGoToTheDataPage() {

        clickOn("#dataButton");
    }

    @And("select the first station")
    public void selectTheFirstStation() {

        moveBy(10,20);
        moveBy(10,20);
        moveBy(10,20);

        clickOn();
        
    }

    @And("click the view charger")
    public void clickTheViewCharger() {
        clickOn("#viewChargersButton");
    }

    @And("plug type to Type 2 Socketed")
    public void plugTypeToType2Scocketed() {
        clickOn("#plugTypeDropDown");
        press(KeyCode.DOWN);
        press(KeyCode.ENTER);
    }

    @When("User edit the charger wattage to {string}")
    public void userEditTheChargerWattageTo(String arg0) {
        clickOn("#wattageText");
        push(KeyCode.CONTROL,KeyCode.A);
        write(arg0);
    }

    @And("User click the save button in station")
    public void userClickTheSaveButtonInStation() {
        clickOn("#saveButton");
    }

    @Then("User have the new charger type changed in that station")
    public void userHaveTheNewChargerTypeChangedInThatStation() {
        TextArea textArea = find("#textAreaInMainScreen");
        Assertions.assertEquals("Station Name : YHA MT COOK\n" +
                "Coordinate : -43.73745,170.100913\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 4 Kitchener Dr, Mount Cook National Park 7999, New Zealand\n" +
                "Time Limit : 0\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "22 kW Type 2 Socketed, Status: Operative;\n" +
                "10 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 1\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : true\n" +
                "Has Tourist Attraction : false", textArea.getText());
    }

    @And("User click the return Button")
    public void userClickTheReturnButton() {
        clickOn("#returnButton");
    }

    @And("User click the add charger button")
    public void userClickTheAddChargerButton() {
        clickOn("#addButton");
    }

    @And("click edit button")
    public void clickEditButton() {
        clickOn("#stationButton");
        
    }

    @And("click save change")
    public void clickSaveChange() {
        clickOn("#saveChanges");
    }

    @When("User selected the first charger and delete it")
    public void userSelectedTheFirstChargerAndDeleteIt() {
        //clickOn("#chargerDropDown");
        clickOn("#deleteButton");
        
    }

    @Then("User will only have the previous added charger info in that station")
    public void userWillOnlyHaveThePreviousAddedChargerInfoInThatStation() {
        TextArea textArea = find("#textAreaInMainScreen");
        Assertions.assertEquals("Station Name : YHA MT COOK\n" +
                "Coordinate : -43.73745,170.100913\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 4 Kitchener Dr, Mount Cook National Park 7999, New Zealand\n" +
                "Time Limit : 0\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "10 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 1\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : true\n" +
                "Has Tourist Attraction : false", textArea.getText());
        
    }

    @When("user delete the charging station")
    public void userDeleteTheChargingStation() {
        clickOn("#deleteButton");
        press(KeyCode.TAB);
        release(KeyCode.TAB);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
    }

    @Then("the first charging station has been deleted and replace by other one.")
    public void theFirstChargingStationHasBeenDeletedAndReplaceByOtherOne() {

        TextArea textArea = find("#textAreaInMainScreen");
        Assertions.assertNotEquals("Station Name : YHA MT COOK\n" +
                "Coordinate : -43.73745,170.100913\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 4 Kitchener Dr, Mount Cook National Park 7999, New Zealand\n" +
                "Time Limit : 0\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "22 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 1\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : true\n" +
                "Has Tourist Attraction : false", textArea.getText());


        
    }

    @And("the station info by edit the name to {string}")
    public void theStationInfoByEditTheNameTo(String arg0) {
        clickOn("#nameField");
        push(KeyCode.CONTROL,KeyCode.A);
        write(arg0);
        
    }

    @And("edit address to {string}")
    public void editAddressTo(String arg0) {
        clickOn("#addressField");
        push(KeyCode.CONTROL,KeyCode.A);
        write(arg0);
        
    }

    @And("number of carpark to {string}")
    public void numberOfCarparkTo(String arg0) {
        clickOn("#numParksField");
        push(KeyCode.CONTROL,KeyCode.A);
        write(arg0);
        
    }

    @And("time limit to {string}")
    public void timeLimitTo(String arg0) {
        clickOn("#timeLimitField");
        push(KeyCode.CONTROL,KeyCode.A);
        write(arg0);
    }

    @And("save the changes")
    public void saveTheChanges() {
        clickOn("#saveButton");
    }

    @Then("User have the charging station with those information")
    public void userHaveTheChargingStationWithThoseInformation() {

        TextArea textArea = find("#textAreaInMainScreen");
        Assertions.assertEquals("Station Name : SOMETHING\n" +
                "Coordinate : -43.53092,172.57957\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 3 ilam road\n" +
                "Time Limit : 240\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "22 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 3\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : true\n" +
                "Has Tourist Attraction : false", textArea.getText());

    }

    @Then("the user have the charger type changed in that station")
    public void theUserHaveTheChargerTypeChangedInThatStation() {
        TextArea textArea = find("#textAreaInMainScreen");
        Assertions.assertEquals("Station Name : YHA MT COOK\n" +
                "Coordinate : -43.73745,170.100913\n" +
                "Operator : MERIDIAN ENERGY LIMITED\n" +
                "Owner : MERIDIAN ENERGY LIMITED\n" +
                "Address : 4 Kitchener Dr, Mount Cook National Park 7999, New Zealand\n" +
                "Time Limit : 0\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "10 kW Type 2 Socketed, Status: Operative\n" +
                "Number Of CarPark : 1\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : true\n" +
                "Has Tourist Attraction : false", textArea.getText());
    }
    @When("User filter the station by the station name {string} and choose {int} hour operative and no charging cost and has tourist attraction")
    public void userFilterTheStationByTheStationNameAndChooseHourOperativeAndNoChargingCost(String name, int arg1) {
        clickOn("#inputStationName");
        write(name);
        clickOn("#is24HourCheckBox");
        clickOn("#hasChargingCostCheckBox");
        clickOn("#hasTouristAttractionCostCheckBox");
        clickOn("#filterButton");
    }

    @And("input the operator as {string}")
    public void inputTheOperatorAs(String arg0) {
        clickOn("#operatorField");
        write(arg0);
    }

    @And("input the owner as {string}")
    public void inputTheOwnerAs(String arg0) {
        clickOn("#ownerField");
        write(arg0);
    }

    @And("choose {int} hour operative")
    public void chooseHourOperative(int arg0) {
        clickOn("#hoursButton");
    }

    @And("choose no charging cost")
    public void chooseNoChargingCost() {
    }

    @And("choose has tourist attraction")
    public void chooseHasTouristAttraction() {
        clickOn("#touristButton");
    }

    @And("click continue button")
    public void clickContinueButton() {
        clickOn("#saveButton");
    }

    @And("click the save station")
    public void clickTheSaveStation() {
        clickOn("#saveStationButton");
    }

    @Then("User have the charging station with related info")
    public void userHaveTheChargingStationWithRelatedInfo() {

        TableView<?> table = find("#table");
        Assertions.assertEquals("Station Name : SOMETHING\n" +
                "Coordinate : -43.53092,172.57957\n" +
                "Operator : Some one\n" +
                "Owner : Some one\n" +
                "Address : 3 ilam road\n" +
                "Time Limit : 240\n" +
                "Is 24 Hour : true\n" +
                "Chargers : \n" +
                "10 kW Type 2 CCS, Status: Operative\n" +
                "Number Of CarPark : 5\n" +
                "CarPark Cost : false\n" +
                "Charging Cost : false\n" +
                "Has Tourist Attraction : true",table.getItems().get(0).toString());
    }

    @And("plug type to Type 2 CCS")
    public void plugTypeToTypeCCS() {
        clickOn("#plugTypeDropDown");
        press(KeyCode.DOWN);
        press(KeyCode.ENTER);
    }
}
