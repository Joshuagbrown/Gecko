package seng202.team6.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.dialog.ProgressDialog;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Station;
import seng202.team6.models.User;
import seng202.team6.repository.FilterBuilder;
import seng202.team6.services.DataService;

/**
 * Controller of the main screen fxml.
 * @author  Phyu Wai Lwin.
 */
public class MainScreenController {

    /**
     * The toolbar pane of the main screen to display related toolbar screen.
     */
    @FXML
    public BorderPane toolBarPane;
    /**
     * The toolbar pane of the border screen to display related screen.
     */
    @FXML
    public BorderPane mainBorderPane;
    /**
     * the text area in the main screen t display the information.
     */
    @FXML
    public TextArea textAreaInMainScreen;
    /**
     * The text to for the project.
     */
    @FXML
    public Text geckoTitle;
    public Button loginPageBtn;
    private Stage stage;
    private DataService dataService;
    /**
     * parent class for map screen to display map.
     */
    private Parent mapScreen;
    /**
     * parent class for data screen to display data.
     */
    private Parent dataScreen;
    /**
     * parent class for help screen to display help page.
     */
    private Parent helpScreen;
    /**
     * parent class for map toolbar screen to display map toolbar.
     */
    private Parent mapToolBarScreen;
    /**
     * parent class for data toolbar screen to display data toolbar.
     */
    private Parent dataToolBarScreen;
    /**
     * parent class for help toolbar screen to display help toolbar.
     */
    private Parent helpToolBarScreen;
    /**
     * parent class for login screen to display login page.
     */
    private Parent loginScreen;
    /**
     * parent class for sign up screen to display sign up page.
     */
    private Parent signUpScreen;
    /**
     * parent class for login toolbar screen to display login toolbar.
     */
    private Parent loginToolBarScreen;
    private Parent myDetailsScreen;
    /**
     * parent class for the details toolbar screen to display the details toolbar.
     */
    private Parent myDetailsToolBarScreen;
    private MapController mapController;
    private DataController dataController;
    private HelpController helpController;
    private MapToolBarController mapToolBarController;
    private LoginController loginController;
    private SignUpController signUpController;
    private LoginToolBarController loginToolBarController;
    private MyDetailsController myDetailsController;
    private MyDetailsToolBarController myDetailsToolBarController;

    private ObservableMap<Integer, Station> stations = FXCollections.observableHashMap();
    private User currentUser = null;
    private Parent registerVehicleScreen;
    private RegisterVehicleController registerVehicleController;

    private int currentUserId;



    /**
     * Initialize the window by loading necessary screen and
     * initialize the parent of different screen.
     *
     * @param stage Top level container for this window.
     * @param dataService Service class to handle accessing and storing the necessary information.
     *
     */
    void init(Stage stage, DataService dataService) {
        Pair<Parent, ScreenController> pair;
        LoadScreen screen = new LoadScreen();

        this.stage = stage;
        this.dataService = dataService;
        updateStationsFromDatabase();

        try {
            pair = screen.loadBigScreen(stage, "/fxml/Help.fxml", this);
            helpScreen = pair.getKey();
            helpController = (HelpController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/Map.fxml", this);
            mapScreen = pair.getKey();
            mapController = (MapController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/Data.fxml", this);
            dataScreen = pair.getKey();
            dataController = (DataController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/MapToolBar.fxml", this);
            mapToolBarScreen = pair.getKey();
            mapToolBarController = (MapToolBarController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/DataToolBar.fxml", this);
            dataToolBarScreen = pair.getKey();
            mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);

            pair = screen.loadBigScreen(stage, "/fxml/HelpToolBar.fxml", this);
            helpToolBarScreen = pair.getKey();

            pair = screen.loadBigScreen(stage, "/fxml/LogIn.fxml", this);
            loginScreen = pair.getKey();
            loginController = (LoginController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/SignUp.fxml", this);
            signUpScreen = pair.getKey();
            signUpController = (SignUpController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/LoginToolBar.fxml", this);
            loginToolBarScreen = pair.getKey();
            loginToolBarController = (LoginToolBarController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/MyDetails.fxml", this);
            myDetailsScreen = pair.getKey();
            myDetailsController = (MyDetailsController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/MyDetailsToolBar.fxml", this);
            myDetailsToolBarScreen = pair.getKey();
            myDetailsToolBarController = (MyDetailsToolBarController) pair.getValue();

            pair = screen.loadBigScreen(stage, "/fxml/RegisterVehicle.fxml", this);
            registerVehicleScreen = pair.getKey();
            registerVehicleController = (RegisterVehicleController) pair.getValue();

//            loadMapViewAndToolBars();
            loadVehicleType();
            mapButtonEventHandler();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } catch (CsvFileException e) {
            throw new RuntimeException(e);
        }
        stage.sizeToScene();

    }
    public MyDetailsController getMyDetailController() {
        return myDetailsController;
    }

    public RegisterVehicleController getRegisterVehicleController() {
        return registerVehicleController;
    }

    public void loadRegisterVehicleScreen(){
        mainBorderPane.setCenter(registerVehicleScreen);
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    /**
     * Function to get the stations.
     * @return An observable map of stations.
     */
    public ObservableMap<Integer, Station> getStations() {
        return stations;
    }

    /**
     * Function that returns the current user.
     * @return currentUser the current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Function to set the current user.
     * @param currentUser the current user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Function to update the stations.
     * @param builder The filter builder to use
     */
    public void updateStationsFromDatabase(FilterBuilder builder) {
        Map<Integer, Station> stationMap = dataService.fetchData(builder);
        getStations().clear();
        getStations().putAll(stationMap);
    }

    /**
     * Function to update the stations.
     */
    public void updateStationsFromDatabase() {
        Map<Integer, Station> stationMap = dataService.fetchData();
        getStations().clear();
        getStations().putAll(stationMap);
    }

    /**
     * Function to return the loginPage button.
     * @return the login page button.
     */
    public Button getLoginPageBtn() {
        return loginPageBtn;
    }

    /**
     * Changing the text of the button.
     */
    public void setLoginBtnText() {
        this.loginPageBtn.setText("My Details");
    }

    /**
     * Function to return the map controller.
     * @return mapController.
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Function to return the map toolbar controller.
     * @return map toolbar controller.
     */
    public MapToolBarController getMapToolBarController() {
        return mapToolBarController;
    }

    /**
     * Function to return the stage.
     * @return stage of main screen controller.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Function to return the data service.
     * @return data service. of main screen controller.
     */
    public DataService getDataService() {
        return dataService;
    }

    /**
     * Function to return the help controller.
     * @return help controller.
     */
    public HelpController getHelpController() {
        return this.helpController;
    }

    /**
     * Function to return the data controller.
     * @return data controller.
     */
    public DataController getDataController() {
        return dataController;
    }

    /**
     * Function to return the login controller.
     * @return loginController the login controller.
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Function to return the 'My Details' controller.
     * @return myDetailsController the 'my details' controller.
     */
    public MyDetailsController getMyDetailsController() {
        return myDetailsController;
    }

    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window.
     */
    public void mapButtonEventHandler(ActionEvent actionEvent) {

        textAreaInMainScreen.setText("");
        mainBorderPane.setCenter(mapScreen);
        toolBarPane.setCenter(mapToolBarScreen);
        mainBorderPane.setRight(null);
        mapToolBarController.setFilterSectionOnMapToolBar(dataToolBarScreen);
    }

    /**
     * This loads the map view and toolbars.
     */
    public void mapButtonEventHandler() {
        mapButtonEventHandler(null);
    }

    /**
     * Pick random fun fact and loads it into the text area.
     * @param file the Fun fact text file
     */
    public void loadGeckoFact(InputStream file) {
        loginController.funFactBox.clear();

        List<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file));

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int min = 0;
        int max = 9;
        int randomLine = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String funFact = lines.get(randomLine);
        loginController.funFactBox.appendText(funFact);
        loginController.funFactBox.setFont(new Font("Courier", 16));
    }

    /**
     * The action handler that linked to the Login button on main screen.
     * @param actionEvent when Login button is clicked
     */
    public void loginButtonEventHandler(ActionEvent actionEvent) {
        if (currentUser == null) {
            mainBorderPane.setCenter(loginScreen);
            toolBarPane.setCenter(loginToolBarScreen);
            mainBorderPane.setRight(null);
            loadGeckoFact(getClass().getResourceAsStream("/TextFiles/FunFacts.txt"));
        } else {
            loadMyDetailsViewAndToolBars();
        }

    }

    /**
     * The action handler that linked to the sign-up button on toolbar screen.
     */
    public void loadSignUpViewAndToolBars() {
        mainBorderPane.setCenter(signUpScreen);
    }

    /**
     * The action handler that linked to the 'My Details' button on main screen.
     */
    public void loadMyDetailsViewAndToolBars() {
        mainBorderPane.setCenter(myDetailsScreen);
        toolBarPane.setCenter(myDetailsToolBarScreen);
    }

    /**
     * Function to call when logging in a user.
     * @param user The user to login
     */
    public void loginUser(User user) {
        setCurrentUser(user);
        loadMyDetailsViewAndToolBars();
        getMyDetailsController().loadUserData();
        mapButtonEventHandler();
        setLoginBtnText();
    }

    /**
     * The action handler that linked to the map button on main screen.
     * @param actionEvent Top level container for this window.
     */
    public void loadDataViewAndToolBars(ActionEvent actionEvent) {

        mainBorderPane.setCenter(dataScreen);
        toolBarPane.setCenter(dataToolBarScreen);
    }

    /**
     * The action handler that linked to the help button on toolbar.
     *
     * @param actionEvent Top level container for this window
     */
    public void loadHelpScreenAndToolBar(ActionEvent actionEvent) {
        mainBorderPane.setCenter(helpScreen);
        toolBarPane.setCenter(helpToolBarScreen);
    }

    /**
     * Display string information on the text area.
     * @param info the string information.
     */
    public void setTextAreaInMainScreen(String info) {
        textAreaInMainScreen.setText(info);
    }

    /**
     * This function imports the data from a selected file.
     */
    public void importData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Data from CSV file");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Task<List<String>> task = new Task<>() {
                @Override
                protected List<String> call()  {
                    ObjectProperty<Pair<Integer, Integer>> value = new SimpleObjectProperty<>();
                    value.addListener((observable, oldValue, newValue) -> {
                        updateProgress(newValue.getKey(), newValue.getValue());
                        updateMessage(newValue.getKey() + " / " + newValue.getValue());
                    });
                    try {
                        return dataService.loadDataFromCsv(selectedFile, value)
                                .stream().map(e ->
                                        String.format(
                                                "Line %d: %s",
                                                e.getLine(),
                                                e.getCause().getMessage()))
                                .toList();
                    } catch (CsvFileException | DatabaseException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            ProgressDialog dialog = new ProgressDialog(task);
            dialog.setContentText("Loading data from CSV file...");
            dialog.setTitle("Loading data");
            new Thread(task).start();
            dialog.showAndWait();
            if (task.getState() == Worker.State.FAILED) {
                AlertMessage.createMessage("An error occurred when importing data",
                        task.getException().getCause().getMessage());
            } else if (task.getState() == Worker.State.SUCCEEDED) {
                AlertMessage.createListMessage("Warning",
                        "Some lines were skipped",
                        task.getValue());
            }
            mapController.getJavaScriptConnector().call("cleanUpMarkerLayer");
            updateStationsFromDatabase();
        }
    }

    public MyDetailsToolBarController getMyDetailsToolBarController() {
        return myDetailsToolBarController;
    }

    public void loadVehicleType() throws DatabaseException, CsvFileException {
        File csvFile = new File("src/main/resources/csv/green_vehicles.csv");
        dataService.loadVehicleDataFromCsv(csvFile);
    }
}