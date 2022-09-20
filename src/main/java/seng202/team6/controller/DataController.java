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
    private TableView<Station> table;

    @FXML
    private TableColumn<Station, String> nameColumn;

    @FXML
    private TableColumn<Station, Double> xcolumn;

    @FXML
    private TableColumn<Station, Double> ycolumn;

    private MainScreenController controller;

    public TableColumn<Station, String> address;
    public TableColumn<Station, Integer> noOfCarPark;


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
     * @param sql A sql query from the filters.
     */
    public void loadData(String sql) {
        table.getItems().clear();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xcolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLatitude()));
        ycolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLongitude()));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        noOfCarPark.setCellValueFactory(new PropertyValueFactory<>("numberOfCarParks"));

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
