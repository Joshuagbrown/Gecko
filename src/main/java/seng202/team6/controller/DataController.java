package seng202.team6.controller;

import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Station;


public class DataController implements ScreenController {

    private static final Logger log = LogManager.getLogger();

    @FXML
    public TableView<Station> table;

    @FXML
    public TableColumn<Station, String> nameColumn;

    @FXML
    public TableColumn<Station, Double> xcolumn;

    @FXML
    public TableColumn<Station, Double> ycolumn;

    public MainScreenController controller;

    public TableColumn objectId;
    public TableColumn operator;
    public TableColumn is24Hour;
    public TableColumn timeLimit;
    public TableColumn address;
    public TableColumn owner;
    public TableColumn noOfCarPark;
    public TableColumn carParkCost;
    public TableColumn chargingCost;
    public TableColumn tourstAttraction;

    /**
     * Initialize the window.
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        loadData(null);
    }

    /**
     * Loads the data into the table
     * @param sql
     */
    public void loadData(String sql) {
        table.getItems().clear();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xcolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getFirst()));
        ycolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getSecond()));
        objectId.setCellValueFactory(new PropertyValueFactory<>("objectId"));
        operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        timeLimit.setCellValueFactory(new PropertyValueFactory<>("timeLimit"));
        noOfCarPark.setCellValueFactory(new PropertyValueFactory<>("numberOfCarparks"));
        carParkCost.setCellValueFactory(new PropertyValueFactory<>("carparkCost"));
        tourstAttraction.setCellValueFactory(new PropertyValueFactory<>("hasTouristAttraction"));

        try {
            List<Station> stations = controller.getDataService().fetchAllData(sql);
            table.getItems().addAll(stations);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * This sets the selected station to what station has been clicked on.
     * @param mouseEvent Station is clicked.
     */
    public void clickItem(MouseEvent mouseEvent) {
        Station selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            controller.setTextAreaInMainScreen(selected.toString());
        }
    }
}
