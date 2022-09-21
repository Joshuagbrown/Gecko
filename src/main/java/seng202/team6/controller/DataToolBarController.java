package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
     * This function creates a sql query from the filters selected.
     * @return a string that is the sql query.
     */
    public String createSqlQueryStringFromFilter() {
        String sql = "SELECT * FROM Stations "
                + "JOIN chargers c ON stations.stationId = c.stationId WHERE ";
        if (inputStationName.getText().length() != 0) {
            sql += "(name LIKE '%" + inputStationName.getText()
                    + "%' OR address LIKE '%"
                    + inputStationName.getText()
                    + "%' OR operator LIKE '%"
                    + inputStationName.getText()
                    + "%') AND ";
        }
        if (distanceSliderOfFilter.getValue() != 0) {
            double[] latlng = controller.getMapController().getLatLng();
            if (latlng[0] == 0) {
                AlertMessage.createMessage("Current Location has not been selected.",
                        "Please select a location on the map.");
            } else {
                float distance = (float) (distanceSliderOfFilter.getValue() / 110.574);
                sql += "LAT < " + (latlng[0] + distance) + " AND lat > " + (latlng[0] - distance)
                        + " AND long  < " + (latlng[1] + distance) + " AND long > "
                        + (latlng[1] - distance) + " AND ";
            }
        }
        if (timeLimitInFilter.getValue() != 0) {
            sql += "(timeLimit >= " + timeLimitInFilter.getValue() + " OR timeLimit ==0) " + "AND ";
        }
        if (is24HourCheckBox.isSelected()) {
            sql += "is24Hours = 1 AND ";
        }
        if (hasCarParkCostCheckBox.isSelected()) {
            sql += "carparkCost = 0 AND ";
        }
        if (hasTouristAttractionCostCheckBox.isSelected()) {
            sql += "hasTouristAttraction = 1 AND ";

        }
        if (hasChargingCostCheckBox.isSelected()) {
            sql += "chargingCost = 0 AND ";
        }
        if (sql.equals("SELECT * FROM Stations "
                + "JOIN chargers c ON stations.stationId = c.stationId WHERE ")) {
            sql = "SELECT * FROM Stations JOIN chargers c ON stations.stationId = c.stationId";
        } else {
            int num = sql.lastIndexOf("AND");
            sql = sql.substring(0, num);
        }
        sql += "; ORDER BY stations.stationId";
        return sql;
    }

    /**
     * This function calls the create sql function,
     * and with that string applies the filters to the map and data table.
     * @param actionEvent When filter station button is clicked.
     */
    public void filterStation(ActionEvent actionEvent) {
        String sql = createSqlQueryStringFromFilter();
        controller.getDataController().loadData(sql);
        controller.getMapController().addStationsToMap(sql);
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
