package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DataToolBarController implements ScreenController {
    public CheckBox is24HourCheckBox;
    public CheckBox hasCarParkCostCheckBox;
    public CheckBox hasTouristAttractionCostCheckBox;
    public Slider distanceSliderOfFilter;
    public CheckBox hasChargingCostCheckBox;
    public Slider timeLimitInFilter;
    public TextField inputStationName;
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * This function creates a sql query from the filters selected.
     * @return a string that is the sql query.
     */
    public String createSqlQueryStringFromFilter() {
        String sql = "SELECT * FROM Stations WHERE ";
        if (inputStationName.getText() != null) {
            sql += "(name LIKE '%" + inputStationName.getText()
                    + "%' OR address LIKE '%"
                    + inputStationName.getText()
                    + "%') AND ";
        }

        if (distanceSliderOfFilter.getValue() != 0) {
            float[] latlng = controller.getMapController().getLatLng();
            if (latlng[0] == 0) {

                AlertMessage.createMessage("Current Location has not been selected.", "Please select a location on the map.");

            } else {
                float distance = (float) (distanceSliderOfFilter.getValue() / 110.574);
//            controller.setTextAreaInMainScreen(String.valueOf(distance));

                sql += "LAT < " + (latlng[0] + distance) + " AND lat > " + (latlng[0] - distance) + " AND long  < " + (latlng[1] + distance) + " AND long > " + (latlng[1] - distance) + " AND ";

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
        if (sql.equals("SELECT * FROM Stations WHERE ")) {
            sql = "SELECT * FROM Stations;";
        } else {
            int num = sql.lastIndexOf("AND");
            sql = sql.substring(0, num) + ";";

        }

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
    public void resetFilter(ActionEvent actionEvent){
        distanceSliderOfFilter.setValue(0);
        timeLimitInFilter.setValue(0);
        is24HourCheckBox.setSelected(false);
        hasChargingCostCheckBox.setSelected(false);
        hasCarParkCostCheckBox.setSelected(false);
        hasTouristAttractionCostCheckBox.setSelected(false);
        inputStationName.setText(null);
        filterStation(null);
        controller.setTextAreaInMainScreen(null);
    }


}
