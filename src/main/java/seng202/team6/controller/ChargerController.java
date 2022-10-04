package seng202.team6.controller;

import java.util.List;
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
import seng202.team6.services.Validity;


public class ChargerController {
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
    public Button deleteButton;
    private Stage stage;
    private Station station;
    private MainScreenController controller;
    private List<String> types;
    private Charger currentlySelectedCharger;
    private Validity valid;

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
        valid = new Validity(controller);
        setChargerAndPlugDropDown();
    }

    /**
     * Function to set all chargers in the charger drop down.
     */
    public void setChargerAndPlugDropDown() {

        types = controller.getDataService().getStationDao().getChargerTypes();
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.addAll(types);
        plugTypeDropDown.setItems(typeOptions);

        ObservableList<Charger> options = FXCollections.observableArrayList();
        options.addAll(station.getChargers());
        chargerDropDown.setItems(options);
        if (options.size() > 0) {
            chargerDropDown.getSelectionModel().select(0);
        }


    }

    /**
     * Action event handler so charger information is displayed when
     * the corresponding charger is selected.
     * @param actionEvent user selects a charger from the
     *     charger drop down.
     */
    public void chargerSelected(ActionEvent actionEvent) {

        Charger current = (Charger) chargerDropDown.getValue();
        currentlySelectedCharger = current;
        if (current != null) {
            String op = current.getOperative();
            if (op.equals("Operative")) {
                opButton.setSelected(true);
            } else {
                notOpButton.setSelected(true);
            }

            int watts = current.getWattage();
            wattageText.setText(String.valueOf(watts));

            String type = current.getPlugType();
            int index = types.indexOf(type);
            plugTypeDropDown.getSelectionModel().select(index);
        }

    }

    /**
     * Function used to update charger details in the database.
     * @param actionEvent when the user selects "Save Changes"
     */
    public void saveChargerChanges(ActionEvent actionEvent) {

        Boolean valid = checkValues();
        if (!valid) {
            AlertMessage.createMessage("Invalid Changes made.",
                    "Please fix the changes with errors.");
        } else {
            List<Charger> chargers = station.getChargers();
            int i = 0;
            boolean found = false;
            while (i < chargers.size() && !found) {
                if (chargers.get(i) == currentlySelectedCharger) {
                    found = true;
                } else {
                    i++;
                }
            }
            int wattage = Integer.parseInt(wattageText.getText());
            String plugType = (String) plugTypeDropDown.getValue();
            String op;
            if (opButton.isSelected()) {
                op = "Operative";
            } else {
                op = "Not Operative";
            }
            Charger updating;
            if (i == chargers.size()) {
                updating = new Charger(plugType, op, wattage);
                station.addCharger(updating);
            } else {
                updating = chargers.get(i);
                updating.setWattage(wattage);
                updating.setOperative(op);
                updating.setPlugType(plugType);
            }

            controller.getDataService().getStationDao().update(station);
            setChargerAndPlugDropDown();
            chargerDropDown.getSelectionModel().select(i);
            controller.updateStationsFromDatabase();
            controller.setTextAreaInMainScreen(station.toString());
        }

    }

    /**
     * Function to check the new provided details for the charger.
     * @return true if all valid, entries, else false
     */
    private Boolean checkValues() {

        Boolean returnable = true;

        String wattage = wattageText.getText();

        if (!valid.checkWattage(wattage)) {
            wattageText.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
        } else {
            wattageText.setStyle("");
        }

        if (plugTypeDropDown.getValue() == null) {
            plugTypeDropDown.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
        } else {
            plugTypeDropDown.setStyle("");
        }

        return returnable;

    }

    /**
     * Function to add a new charger to the database under the
     * currently selected charger.
     * @param actionEvent when the add a new charger button is selected.
     */
    public void addCharger(ActionEvent actionEvent) {
        chargerDropDown.getSelectionModel().clearSelection();
        wattageText.setText("");
        opButton.setSelected(true);
        plugTypeDropDown.getSelectionModel().clearSelection();

    }


    /**
     * Function to delete the currently selected charger.
     * @param actionEvent when the user selects delete charger
     */
    public void deleteCharger(ActionEvent actionEvent) {
        List<Charger> chargers = station.getChargers();
        int index = 0;
        boolean found = false;
        while (index < chargers.size() && !found) {
            if (chargers.get(index) == currentlySelectedCharger) {
                found = true;
            } else {
                index++;
            }
        }

        if (index == chargers.size()) {
            AlertMessage.createMessage("Unable to delete the currently selected charger.",
                    "Please save your changes first.");
        } else {
            chargers.remove(index);

            controller.getDataService().getStationDao().update(station);
            setChargerAndPlugDropDown();
            wattageText.setText("");
            opButton.setSelected(true);
            plugTypeDropDown.getSelectionModel().clearSelection();
            chargerDropDown.getSelectionModel().clearSelection();
            controller.updateStationsFromDatabase();
            controller.setTextAreaInMainScreen(station.toString());
        }

    }

}
