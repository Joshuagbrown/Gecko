package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DataToolBarController implements ScreenController {
    public CheckBox Is24HourCheckBox;
    public CheckBox hasCarParkCostCheckBox;
    public CheckBox hasTouristAttractionCostCheckBox;
    public Slider distanceSliderOfFilter;
    public CheckBox hasChargingCostCheckBox;
    public Slider timeLimitInFilter;
    public Button resetFilter;
    public TextField inputStationName;
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }


    public String createSqlQueryStringFromFilter() {
        String sql = "SELECT * FROM Stations WHERE ";
        if (inputStationName.getText()!=null) {
            sql += "(name LIKE '%"+inputStationName.getText()+
                    "%' OR address LIKE '%"
                    +inputStationName.getText()+ "%') AND ";

        }

        if (distanceSliderOfFilter.getValue()!= 0) {


        } else {

        }
        if (timeLimitInFilter.getValue() != 0) {
            sql += "timeLimit >= "+timeLimitInFilter.getValue()+ " OR timeLimit ==0 "+ "AND ";
        }
        if(Is24HourCheckBox.isSelected()) {
            sql += "is24Hours = 1 AND ";
        }
        if(hasCarParkCostCheckBox.isSelected()) {
            sql += "carparkCost = 1 AND ";
        }
        if (hasTouristAttractionCostCheckBox.isSelected()) {
            sql += "hasTouristAttraction = 1 AND ";

        }
        if (hasChargingCostCheckBox.isSelected()) {
            sql += "chargingCost = 1 AND ";
        }
        if(sql == "SELECT * FROM Stations WHERE ") {
            sql = "SELECT * FROM Stations;";
        } else {
            int num = sql.lastIndexOf("AND");
            sql = sql.substring(0,num ) + ";";

        }


        return sql;
    }

    public void filterStation(ActionEvent actionEvent) {
        String sql = createSqlQueryStringFromFilter();
        controller.getDataController().loadData( sql);
        controller.getMapController().addStationsToMap(sql);
    }

    public void resetFilter(ActionEvent actionEvent) {
        distanceSliderOfFilter.setValue(0);
        timeLimitInFilter.setValue(0);
        Is24HourCheckBox.setSelected(false);
        hasChargingCostCheckBox.setSelected(false);
        hasCarParkCostCheckBox.setSelected(false);
        hasTouristAttractionCostCheckBox.setSelected(false);
        inputStationName.setText(null);
        filterStation(null);
        controller.setTextAreaInMainScreen(null);
    }
}
