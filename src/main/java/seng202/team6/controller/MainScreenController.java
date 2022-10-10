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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.controlsfx.dialog.ProgressDialog;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Journey;
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
    public Button stationButton;
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
    /**
     * parent class for the saved journeys screen to display the saved journeys page.
     */
    private Parent journeysScreen;
    private MapController mapController;
    private DataController dataController;
    private HelpController helpController;
    private MapToolBarController mapToolBarController;
    private LoginController loginController;
    private SignUpController signUpController;
    private LoginToolBarController loginToolBarController;
    private MyDetailsController myDetailsController;
    private MyDetailsToolBarController myDetailsToolBarController;
    private SaveJourneyController saveJourneyController;

    private ObservableMap<Integer, Station> stations = FXCollections.observableHashMap();
    private ObservableMap<Integer, Journey> journeys = FXCollections.observableHashMap();
    private User currentUser = null;
    private Parent registerVehicleScreen;
    private RegisterVehicleController registerVehicleController;

    private int currentUserId;


    /**
     * Initialize the window by loading necessary screen and
     * initialize the parent of different screen.
     *
     * @param stage       Top level container for this window.
     * @param dataService Service class to handle accessing and storing the necessary information.
     */
    void init(Stage stage, DataService dataService) {
        Pair<Parent, ScreenController> pair;
        LoadScreen screen = new LoadScreen();

        this.stage = stage;
        this.dataService = dataService;
        updateStationsFromDatabase();
        updateJourneysFromDatabase();

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

            //loadVehicleType();
            pair = screen.loadBigScreen(stage, "/fxml/SaveJourney.fxml", this);
            journeysScreen = pair.getKey();
            saveJourneyController = (SaveJourneyController) pair.getValue();

            // loadVehicleType();
            mapButtonEventHandler();

        } catch (IOException e) {
            throw new RuntimeException(e);
            //} catch (DatabaseException e) {
            //throw new RuntimeException(e);
            //} catch (CsvFileException e) {
            // throw new RuntimeException(e);
        }
        stage.sizeToScene();

    }

    /**
     * Function to get my detail controller of the app.
     *
     * @return my detail controller.
     */
    public MyDetailsController getMyDetailController() {
        return myDetailsController;
    }

    /**
     * Function to get register vehicle controller of the app.
     *
     * @return register vehicle controller.
     */
    public RegisterVehicleController getRegisterVehicleController() {
        return registerVehicleController;
    }

    /**
     * load the register vehicle screen on the main screen.
     */
    public void loadRegisterVehicleScreen() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterVehicle.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Current Station");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        registerVehicleController = loader.getController();
        registerVehicleController.init(stage, this);

    }

    /**
     * Function to get the current user id.
     *
     * @return current user id.
     */
    public int getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Fuction to set the current user id.
     *
     * @param currentUserId the user id to be set.
     */
    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    /**
     * Function to get the stations.
     *
     * @return An observable map of stations.
     */
    public ObservableMap<Integer, Station> getStations() {
        return stations;
    }

    /**
     * Function to get the journeys.
     * @return An observable map of journeys.
     */
    public ObservableMap<Integer, Journey> getJourneys() {
        return journeys;
    }

    /**
     * Function that returns the current user.
     *
     * @return currentUser the current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Function to set the current user.
     *
     * @param currentUser the current user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Function to update the stations.
     *
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
     * Function to update the journeys.
     */
    public void updateJourneysFromDatabase() {
        getJourneys().clear();
        if (currentUser != null) {
            Map<Integer, Journey> journeyMap = dataService.fetchJourneyData(
                    currentUser.getUsername());
            getJourneys().putAll(journeyMap);
        }
    }

    /**
     * Function to return the loginPage button.
     *
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
     * Changing the text of the button.
     */
    public void setLoginBtnTextBack() {
        this.loginPageBtn.setText("Login");
    }

    /**
     * Function to return the map controller.
     *
     * @return mapController.
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * Function to return the map toolbar controller.
     *
     * @return map toolbar controller.
     */
    public MapToolBarController getMapToolBarController() {
        return mapToolBarController;
    }

    /**
     * Function to return the stage.
     *
     * @return stage of main screen controller.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Function to return the data service.
     *
     * @return data service. of main screen controller.
     */
    public DataService getDataService() {
        return dataService;
    }

    /**
     * Function to return the help controller.
     *
     * @return help controller.
     */
    public HelpController getHelpController() {
        return this.helpController;
    }

    /**
     * Function to return the data controller.
     *
     * @return data controller.
     */
    public DataController getDataController() {
        return dataController;
    }

    /**
     * Function to return the login controller.
     *
     * @return loginController the login controller.
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * Function to return the 'My Details' controller.
     *
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
        changeToAddButton();
    }

    /**
     * This loads the map view and toolbars.
     */
    public void mapButtonEventHandler() {
        mapButtonEventHandler(null);
    }

    /**
     * Loads the saved journeys page on the 'my details' screen.
     */
    public void loadJourneysButtonEventHandler() {
        updateJourneysFromDatabase();
        textAreaInMainScreen.setText("");
        mainBorderPane.setCenter(journeysScreen);
        toolBarPane.setCenter(myDetailsToolBarScreen);
        mainBorderPane.setRight(null);
    }

    /**
     * Pick random fun fact and loads it into the text area.
     *
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
     *
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
        changeToAddButton();
    }

    /**
     * Function to call when logging in a user.
     *
     * @param user The user to login
     */
    public void loginUser(User user) {
        setCurrentUser(user);
        loadMyDetailsViewAndToolBars();
        getMyDetailsController().loadUserData();
        mapButtonEventHandler();
        setLoginBtnText();
        changeToAddButton();
    }

    /**
     * The action handler that linked to the map button on main screen.
     *
     * @param actionEvent Top level container for this window.
     */
    public void loadDataViewAndToolBars(ActionEvent actionEvent) {

        mainBorderPane.setCenter(dataScreen);
        toolBarPane.setCenter(dataToolBarScreen);
        changeToAddButton();
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
     *
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
                protected List<String> call() {
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
                AlertMessage.createListMessageStation("Warning",
                        "Some lines were skipped",
                        task.getValue());
            }
            mapController.getJavaScriptConnector().call("cleanUpMarkerLayer");
            updateStationsFromDatabase();
        }
    }

    /**
     * Function to get my detail toolbar controller.
     *
     * @return my detail toolbar controller.
     */
    public MyDetailsToolBarController getMyDetailsToolBarController() {
        return myDetailsToolBarController;
    }

    /**
     * Action to load the csv file of green vehicle into the database.
     *
     * @throws DatabaseException the error from database.
     * @throws CsvFileException  the error from the csv file loading.
     */
    public void loadVehicleType() throws DatabaseException, CsvFileException {
        File csvFile = new File("src/main/resources/csv/green_vehicles.csv");
        dataService.loadVehicleDataFromCsv(csvFile);
    }

    /**
     * Function to generate add station pop-up.
     *
     * @param actionEvent when 'Add Station' button is clicked.
     */
    public void addStation(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (getCurrentUserId() == 0 || getCurrentUserId() == -1) {
            AlertMessage.createMessage("Unable to access this feature",
                    "Please Log in or Sign up.");
        } else {
            getMapController().loadAddStationWindow(null);
        }
    }

    /**
     * Function to generate edit station pop-up.
     *
     * @param actionEvent when 'Edit Station' button is clicked.
     */
    public void editStation(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (getCurrentUserId() == 0 || getCurrentUserId() == -1) {
            AlertMessage.createMessage("Unable to access this feature",
                    "Please Log in or Sign up.");
        } else {
            Parent current = (Parent) mainBorderPane.getCenter();
            int stationID;
            if (current == dataScreen) {
                stationID = getDataController().getCurrentlySelected().getStationId();
            } else {
                stationID = getMapController().getCurrentlySelected().getStationId();
            }
            getMapController().loadEditStationWindow(stationID);
        }
    }

    /**
     * Change the add station button to the edit station button.
     */
    public void changeToEditButton() {
        stationButton.setText("Edit Station");
        stationButton.setOnAction(e -> {
            try {
                editStation(e);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    /**
     * Change the edit station button to the add station button.
     */
    public void changeToAddButton() {
        stationButton.setText("Add Station");
        stationButton.setOnAction(e -> {
            try {
                addStation(e);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}


