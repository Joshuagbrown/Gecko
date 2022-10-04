package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.models.Position;
import seng202.team6.repository.FilterBuilder;

/**
 * The controller class of the data toolbar fxml.
 * @author  Phyu Wai Lwin.
 */
public class DataToolBarController implements ScreenController {
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


    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * This function calls the create sql function,
     * and with that string applies the filters to the map and data table.
     * @param actionEvent When filter station button is clicked.
     */
    public void filterStation(ActionEvent actionEvent) {
        FilterBuilder builder = new FilterBuilder();

        if (!inputStationName.getText().isBlank()) {
            builder.addSearchFilter(inputStationName.getText());
        }

        if (distanceSliderOfFilter.getValue() != 0) {
            Position position = controller.getMapController().getLatLng();
            if (position == null) {
                AlertMessage.createMessage("Current Location has not been selected.",
                        "Please select a location on the map.");

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

        controller.updateStationsFromDatabase(builder);
    }

    /**
     * This function resets the values of the filters.
     * @param actionEvent When reset button is clicked.
     */
    public void resetFilter(ActionEvent actionEvent) {
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
