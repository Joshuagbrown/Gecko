package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class DataToolBarController implements ScreenController {
    public CheckBox Is24HourCheckBox;
    public CheckBox hasCarParkCostCheckBox;
    public CheckBox hasTouristAttractionCostCheckBox;
    public Slider distanceSliderOfFilter;
    public CheckBox hasChargingCostCheckBox;
    public Slider timeLimitInFilter;
    private Stage stage;
    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        this.controller = controller;
    }


    public String createSqlQueryStringFromFilter() {
        String sql = "SELECT * FROM Stations WHERE ";

        if (distanceSliderOfFilter.getValue()!= 0) {


        } else {

        }
        if (timeLimitInFilter.getValue() != 0) {
            sql += "timeLimit >= "+timeLimitInFilter.getValue()+"AND ";
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
        if(sql == "SELECT * FROM Station WHERE ") {
            sql = "SELECT * FROM Station";
        } else {
            int num = sql.lastIndexOf("AND");
            sql = sql.substring(0,num ) + ";";

        }


        return sql;
    }

    public void filterStation(ActionEvent actionEvent) {
        String sql = createSqlQueryStringFromFilter();
        controller.getDataController().loadData( sql);
    }
}
