package seng202.team6.controller;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Position;
import seng202.team6.repository.FilterBuilder;
import seng202.team6.services.AlertMessage;


/**
 * The controller class of the data toolbar fxml.
 * @author  Phyu Wai Lwin.
 */
public class DataToolBarController implements ScreenController {

    @FXML
    private RadioButton fromCurrentLoc;
    @FXML
    private RadioButton fromHome;
    @FXML
    private CheckBox is24HourCheckBox;
    @FXML
    private CheckBox hasCarParkCostCheckBox;
    @FXML
    private CheckBox hasTouristAttractionCostCheckBox;
    @FXML
    private Slider distanceSliderOfFilter;
    @FXML
    private CheckBox hasChargingCostCheckBox;
    @FXML
    private Slider timeLimitInFilter;
    @FXML
    private TextField inputStationName;
    private MainScreenController controller;


    /**
     * Initializes the data tool bar, specifically the filtering station panel.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        Image home = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/home_icon.png")));
        ImageView homeImageview = new ImageView(home);
        homeImageview.setFitHeight(24);
        homeImageview.setPreserveRatio(true);
        fromHome.setGraphic(homeImageview);

        Image redMarker = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/marker-icon-2x-red.png")));
        ImageView redMarkerImageview = new ImageView(redMarker);
        redMarkerImageview.setFitHeight(24);
        redMarkerImageview.setPreserveRatio(true);
        fromCurrentLoc.setGraphic(redMarkerImageview);

        if (controller.getCurrentUser() == null) {
            fromCurrentLoc.setSelected(true);
        } else {
            fromHome.setSelected(true);
        }

    }

    /**
     * This function calls the create sql function,
     * and with that string applies the filters to the map and data table.
     * @param actionEvent When filter station button is clicked.
     */
    public void filterStation(ActionEvent actionEvent) throws DatabaseException {
        FilterBuilder builder = new FilterBuilder();

        if (!inputStationName.getText().isBlank()) {
            builder.addSearchFilter(inputStationName.getText());
        }

        if (distanceSliderOfFilter.getValue() != 0) {
            Position position;
            if (fromHome.isSelected()) {
                position = controller.getMapController().getHomePosition();
            } else {
                position = controller.getMapController().getLatLng();
            }

            if (position == null && fromCurrentLoc.isSelected()) {
                AlertMessage.createMessage("Current Location has not been selected.",
                        "Please select a location on the map.");
            } else if (position == null && fromHome.isSelected()) {
                Alert alert = AlertMessage.notLoggedIn();
                ButtonType button = alert.getButtonTypes().get(0);
                ButtonType result = alert.showAndWait().orElse(button);

                if (button.equals(result)) {
                    controller.loginButtonEventHandler(null);
                }
            } else {
                builder.addDistanceFilter(position, distanceSliderOfFilter.getValue());
            }
        }

        if (timeLimitInFilter.getValue() != 0) {
            builder.addTimeLimitFilter(timeLimitInFilter.getValue());
        }

        if (is24HourCheckBox.isSelected()) {
            builder.addIs24HourFilter();
        }

        if (hasCarParkCostCheckBox.isSelected()) {
            builder.addCarParkCostFilter();
        }

        if (hasChargingCostCheckBox.isSelected()) {
            builder.addHasChargingCostFilter();
        }

        if (hasTouristAttractionCostCheckBox.isSelected()) {
            builder.addHasTouristAttractionFilter();
        }

        controller.setFilterBuilder(builder);
        controller.updateStationsFromDatabase();
    }

    /**
     * This function resets the values of the filters.
     * @param actionEvent When reset button is clicked.
     */
    public void resetFilter(ActionEvent actionEvent) throws DatabaseException {
        distanceSliderOfFilter.setValue(0);
        timeLimitInFilter.setValue(0);
        is24HourCheckBox.setSelected(false);
        hasChargingCostCheckBox.setSelected(false);
        hasCarParkCostCheckBox.setSelected(false);
        hasTouristAttractionCostCheckBox.setSelected(false);
        inputStationName.setText("");
        filterStation(null);
        controller.setTextAreaInMainScreen(null);
    }
}
