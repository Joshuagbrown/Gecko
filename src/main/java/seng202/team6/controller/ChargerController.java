package seng202.team6.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.Charger;
import seng202.team6.models.Station;
import seng202.team6.services.AlertMessage;
import seng202.team6.services.Validity;


/**
 * Controller class for the charger pop-up screens.
 * Author: Tara Lipscombe
 */
public class ChargerController {
    @FXML
    public ComboBox<Charger> chargerDropDown = new ComboBox<>();
    @FXML
    public Button addButton;
    @FXML
    public CheckBox opButton;
    @FXML
    public TextField wattageText;
    @FXML
    public ComboBox<String> plugTypeDropDown = new ComboBox<>();
    @FXML
    public Button deleteButton;
    @FXML
    public Text chargerTitle;
    @FXML
    public Button saveStationButton;
    @FXML
    public Button saveChanges;
    private Stage stage;
    private Station station;
    private MainScreenController controller;
    private List<String> types;
    private int currentlySelectedCharger;
    private Validity valid;
    private List<String> currentErrors = new ArrayList<>();
    private List<Charger> currentChargers = new ArrayList<>();
    private Scene stationScene;
    private String newOrUpdate;

    /**
     * Initializes the Charger Controller class.
     * @param stage the stage for the pop-up.
     * @param controller the mainscreen controller.
     * @param newStation the current station.
     */
    public void init(Stage stage, Scene scene, MainScreenController controller,
                     Station newStation, String newOrUpdate) {
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            boolean saved = checkChanges();
            if (!saved) {
                e.consume();
            }
        });
        this.stationScene = scene;
        this.station = newStation;
        this.controller = controller;
        this.newOrUpdate = newOrUpdate;
        valid = new Validity(controller);
        setChargerAndPlugDropDown();
        currentChargers.clear();
        currentChargers.addAll(station.getChargers());
        if (Objects.equals(newOrUpdate, "new")) {
            chargerTitle.setText("Add Charger Information");
        } else {
            chargerTitle.setText("Add/Edit Charger Information");
            saveStationButton.setVisible(false);
        }


    }

    /**
     * Function to set all chargers in the charger dropDown.
     */
    public void setChargerAndPlugDropDown() {

        types = controller.getDataService().getStationDao().getChargerTypes();
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.addAll(types);
        plugTypeDropDown.setItems(typeOptions);

        ObservableList<Charger> options = FXCollections.observableArrayList();
        options.addAll(station.getChargers());
        chargerDropDown.setItems(options);
        if (!options.isEmpty()) {
            chargerDropDown.getSelectionModel().select(0);
        }

        opButton.setSelected(true);

    }

    /**
     * Action event handler so charger information is displayed when
     * the corresponding charger is selected.
     * @param actionEvent user selects a charger from the
     *     charger drop down.
     */
    public void chargerSelected(ActionEvent actionEvent) {

        Charger current = chargerDropDown.getValue();
        currentlySelectedCharger = chargerDropDown.getSelectionModel().getSelectedIndex();
        if (current != null) {
            String op = current.getOperative();
            opButton.setSelected(op.equals("Operative"));

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

        boolean isValid = checkValues();

        if (!isValid) {
            AlertMessage.createListMessageStation("Invalid Changes made.",
                    "Please fix the changes with errors.", currentErrors);
            currentErrors.clear();
        } else {
            List<Charger> chargers = station.getChargers();
            int wattage = Integer.parseInt(wattageText.getText());
            String plugType = plugTypeDropDown.getValue();
            String op;
            if (opButton.isSelected()) {
                op = "Operative";
            } else {
                op = "Not Operative";
            }
            Charger updating;
            if (currentlySelectedCharger == station.getChargers().size()) {
                updating = new Charger(plugType, op, wattage);
                chargers.add(updating);
                station.setChargers(chargers);
                currentChargers.add(updating);
            } else {
                updating = chargers.get(currentlySelectedCharger);
                Charger classUpdating = currentChargers.get(currentlySelectedCharger);
                updating.setWattage(wattage);
                classUpdating.setWattage(wattage);
                updating.setOperative(op);
                classUpdating.setOperative(op);
                updating.setPlugType(plugType);
                classUpdating.setPlugType(plugType);
            }
            
            if (Objects.equals(newOrUpdate, "update")) {
                controller.getDataService().getStationDao().update(station);
                try {
                    controller.updateStationsFromDatabase();
                } catch (DatabaseException e) {
                    throw new RuntimeException(e);
                }
                controller.setTextAreaInMainScreen(station.toString());
            }
            setChargerAndPlugDropDown();
            chargerDropDown.getSelectionModel().clearAndSelect(currentlySelectedCharger);
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
            currentErrors.add("Wattage must be a valid integer between 1 and 500");
        } else {
            wattageText.setStyle("");
        }

        if (plugTypeDropDown.getValue() == null) {
            plugTypeDropDown.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            returnable = false;
            currentErrors.add("A PlugType must be selected.");
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
        currentlySelectedCharger = station.getChargers().size();

    }


    /**
     * Function to delete the currently selected charger.
     * @param actionEvent when the user selects delete charger
     */
    public void deleteCharger(ActionEvent actionEvent) {
        List<Charger> chargers = station.getChargers();

        if (currentlySelectedCharger >= chargers.size()) {
            AlertMessage.createMessage("Unable to delete the currently selected charger.",
                    "Please save your changes first.");
        } else {
            if (currentlySelectedCharger == 0 && chargers.size() == 1) {
                AlertMessage.createMessage("Unable to delete the currently selected charger.",
                        "Each station must have at least one charger.");
            } else {
                controller.getDataService().getStationDao().deleteCharger(chargers
                        .get(currentlySelectedCharger), station.getStationId());
                chargers.remove(currentlySelectedCharger);
                station.setChargers(chargers);
                controller.getDataService().getStationDao().update(station);
                try {
                    controller.updateStationsFromDatabase();
                } catch (DatabaseException e) {
                    throw new RuntimeException(e);
                }
                controller.setTextAreaInMainScreen(station.toString());
                setChargerAndPlugDropDown();
                wattageText.setText("");
                opButton.setSelected(true);
                plugTypeDropDown.getSelectionModel().clearSelection();
                chargerDropDown.getSelectionModel().clearSelection();
                currentlySelectedCharger = station.getChargers().size();
            }
        }
        //        currentlySelectedCharger = station.getChargers().size();
    }


    /**
     * Function to get the current chargers from the charger controller.
     * @return the current chargers
     */
    public List<Charger> getCurrentChargers() {
        return currentChargers;
    }

    /**
     * Returns the scene back to the Station Information Scene.
     * @param actionEvent when the return button is selected
     */
    public void returnStationInfo(ActionEvent actionEvent) {
        boolean saved = checkChanges();
        if (saved) {
            stage.setScene(stationScene);
            stage.setTitle("Current Station");
        }

    }


    /**
     * Function used to check if there are any unsaved changes within the pop-up and alert the user
     * if so.
     * @return true if there are unsaved changes
     */
    private boolean checkChanges() {
        boolean unsavedChanges = false;

        if (station.getChargers().isEmpty() || (currentlySelectedCharger == station
                .getChargers().size())) {
            if (wattageText.getText().length() > 0) {
                unsavedChanges = true;
            }
            if (!plugTypeDropDown.getSelectionModel().isEmpty()) {
                unsavedChanges = true;
            }
        } else {

            if (wattageText.getText().length() > 0 && Integer.parseInt(wattageText.getText())
                    != station.getChargers().get(currentlySelectedCharger).getWattage()) {
                unsavedChanges = true;
            }
            String compare;
            if (opButton.isSelected()) {
                compare = "Operative";
            } else {
                compare = "Not Operative";
            }
            if (!compare.equals(station.getChargers().get(currentlySelectedCharger)
                    .getOperative())) {
                unsavedChanges = true;
            }
            if (!plugTypeDropDown.getSelectionModel().getSelectedItem().equals(
                    station.getChargers().get(currentlySelectedCharger).getPlugType())) {
                unsavedChanges = true;
            }
        }
        if (unsavedChanges) {
            Alert alert = AlertMessage.unsavedChanges();
            ButtonType cancel = alert.getButtonTypes().get(0);
            ButtonType result = alert.showAndWait().orElse(cancel);

            return !cancel.equals(result);
        }
        return true;

    }


    /**
     * Saves the new station and adds it to the database, and refreshes the page.
     * @param actionEvent when the save station button is selected
     */
    public void saveStation(ActionEvent actionEvent) {
        if (station.getChargers().isEmpty()) {
            AlertMessage.createMessage("Unable to save Station.",
                    "Please ensure you have added at least one Charger "
                            + "to your Station.");
        } else {
            try {
                controller.getDataService().getStationDao().add(station);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            try {
                controller.updateStationsFromDatabase();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            stage.close();
        }
        controller.setSelected(controller.getPrevSelected());
    }
}
