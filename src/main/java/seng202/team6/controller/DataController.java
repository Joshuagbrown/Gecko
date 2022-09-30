package seng202.team6.controller;

import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.models.Charger;
import seng202.team6.models.Station;


/**
 * The controller class for the data fxml.
 * @author Phyu Wai Lwin.
 */
public class DataController implements ScreenController {
    private static class MultiLineCell<S> extends TableCell<S, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Text text = new Text();
            setGraphic(text);
            text.wrappingWidthProperty().bind(widthProperty());
            text.textProperty().bind(itemProperty());
            text.setStyle("-fx-fill: -fx-text-background-color;");
            text.setLineSpacing(5);
        }
    }

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
    /**
     * the address column of the table.
     */
    @FXML
    public TableColumn<Station, String> address;
    /**
     * the number of car-park data column of the table.
     */
    @FXML
    public TableColumn<Station, Integer> noOfCarPark;

    /**
     * The list of charger types available at the station.
     */
    @FXML
    public TableColumn<Station, String>  chargers;


    /**
     * Initialize the window.
     * @param controller the main screen controller.
     * @param stage Top level container for this window.
     */
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        loadData();
    }

    /**
     * Loads the data into the table.
     */
    public void loadData() {
        table.getItems().clear();

        nameColumn.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());
        address.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());
        chargers.setCellFactory(stationStringTableColumn -> new MultiLineCell<>());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xcolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLatitude()));
        ycolumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCoordinates().getLongitude()));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        noOfCarPark.setCellValueFactory(new PropertyValueFactory<>("numberOfCarParks"));
        chargers.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getChargers()
                        .stream()
                        .map(value -> "• " + value.getPlugType())
                        .collect(Collectors.joining("\n"))));

        controller.getStations().addListener((MapChangeListener<Integer, Station>) change -> {
            if (change.wasAdded()) {
                table.getItems().add(change.getValueAdded());
            }
            if (change.wasRemoved()) {
                table.getItems().remove(change.getValueRemoved());
            }
        });

        table.getItems().addAll(controller.getStations().values());

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
