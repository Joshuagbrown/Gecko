package seng202.team6.controller;

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

import java.util.List;

/**
 * The controller class for the data fxml.
 * @author Phyu Wai Lwin.
 */
public class DataController implements ScreenController {
    //A Logger object is used to log messages for  system
    private static final Logger log = LogManager.getLogger();

    @FXML
    private TableView<Station> table;

    @FXML
    private TableColumn<Station, String> nameColumn;

    @FXML
    private TableColumn<Station, Double> xcolumn;

    @FXML
    private TableColumn<Station, Double> ycolumn;

    private MainScreenController controller;

    @FXML
    public TableColumn<Object, Object> objectId;
    @FXML
    public TableColumn<Object, Object> operator;
    @FXML
    public TableColumn<Object, Object> timeLimit;
    @FXML
    public TableColumn<Object, Object> address;
    @FXML
    public TableColumn<Object, Object> owner;
    @FXML
    public TableColumn<Object, Object> noOfCarPark;
    @FXML
    public TableColumn<Object, Object> carParkCost;
    @FXML
    public TableColumn<Object, Object> chargingCost;
    @FXML
    public TableColumn<Object, Object> tourstAttraction;

    /**
     * Initialize the window.
     * @param controller the main screen controller
     * @param stage Top level container for this window
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        loadData(null);
    }

    /**
     * Loads the data into the table
     * @param sql A sql query from the filters.
     */
    public void loadData(String sql) {
        table.getItems().clear();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xcolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLatitude()));
        ycolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLongitude()));
        objectId.setCellValueFactory(new PropertyValueFactory<>("objectId"));
        operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        timeLimit.setCellValueFactory(new PropertyValueFactory<>("timeLimit"));
        noOfCarPark.setCellValueFactory(new PropertyValueFactory<>("numberOfCarParks"));
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
