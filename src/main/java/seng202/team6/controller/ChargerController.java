package seng202.team6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import seng202.team6.models.Charger;
import seng202.team6.models.Station;


public class ChargerController {
    @FXML
    public RadioButton acButton;
    @FXML
    public ToggleGroup currentType;
    @FXML
    public RadioButton dcButton;
    @FXML
    public ToggleGroup operative;
    @FXML
    public ComboBox chargerDropDown;
    @FXML
    public Button addButton;
    @FXML
    public RadioButton opButton;
    @FXML
    public RadioButton notOpButton;
    @FXML
    public TextField wattageText;
    @FXML
    public ComboBox plugTypeDropDown;
    private Stage stage;
    private Station station;
    private MainScreenController controller;

    /**
     * Initializes the Charger Controller class.
     * @param stage the stage for the pop-up.
     * @param controller the mainscreen controller.
     * @param station the current station.
     */
    public void init(Stage stage, MainScreenController controller, Station station) {
        this.stage = stage;
        this.station = station;
        this.controller = controller;
        setChargerAndPlugDropDown();
    }

    /**
     * Function to set all chargers in the charger drop down.
     */
    public void setChargerAndPlugDropDown() {

        ObservableList<Charger> options = FXCollections.observableArrayList();
        //for (Charger charger : station.getChargers()) {
        //    options.add(charger);
        //}
        options.addAll(station.getChargers());
        chargerDropDown.setItems(options);

        // Need to get all different types of chargers

    }

    /**
     * Action event handler so charger information is displayed when
     * the corresponding charger is selected.
     * @param actionEvent user selects a charger from the
     *     charger drop down.
     */
    public void chargerSelected(ActionEvent actionEvent) {

        // ADD code for ac/dc once Philip adds this to database
        Charger current = (Charger) chargerDropDown.getValue();
        String op = current.getOperative();
        if (op.equals("Operative")) {
            opButton.setSelected(true);
            //notOpButton.setSelected(false);
            //Do we need above?
        } else {
            notOpButton.setSelected(true);
        }

        int watts = current.getWattage();
        wattageText.setText(String.valueOf(watts));



    }




}
