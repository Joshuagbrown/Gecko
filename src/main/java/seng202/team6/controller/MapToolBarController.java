package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import seng202.team6.models.Position;
import seng202.team6.models.Station;

import java.io.IOException;

/**
 * Controller for the map toolbar.
 */
public class MapToolBarController implements ToolBarController {
    private Stage stage;
    private MainScreenController controller;

    private JSObject javaScriptConnector;

    @FXML
    private TextField newStationTitle;

    @FXML
    private TextField newStationLatitude;

    @FXML
    private TextField newStationLongitude;

    @FXML
    private TextField startLocation;

    @FXML
    private TextField endLocation;

    @FXML
    private Button newStationButton;

    private String[] possibleList;


    /**
     * Initializes the controller.
     * @param stage Primary Stage of the application
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;

    }

    public void addNewStation(ActionEvent actionEvent) {
//        String stationTitle = newStationTitle.getText();
//        Double latitude = Double.parseDouble(newStationLatitude.getText());
//        Double longitude = Double.parseDouble(newStationLongitude.getText());
//
//        Position pos = new Position(latitude, longitude);
//        Station newStation = new Station(pos, stationTitle);

//        controller.addNewStation(station);

    }

    public void findRoute(ActionEvent actionEvent) {
        String firstLocation = startLocation.getText();
        String secondLocation = endLocation.getText();
        javaScriptConnector.call("addRoute", firstLocation, secondLocation);
    }

    public void showTable(ActionEvent actionEvent) throws IOException {
        LoadScreen screen = new LoadScreen();
        controller.getToolBarPane().setBottom(screen.LoadToolBar(stage,"/fxml/TableInToolBar.fxml", controller.getToolBarPane(),controller));
    }
}
