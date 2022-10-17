package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for help toolbar screen fxml.
 */
public class HelpToolBarController implements ScreenController {


    @FXML
    public Button generalHelpButton;
    @FXML
    public Button mapHelpButton;
    @FXML
    public Button dataHelpButton;
    @FXML
    public Button loginHelpButton;
    private MainScreenController controller;

    private Button currentlySelected;


    /**
     * Function to initialize the help toolbar screen.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
        setSelected(generalHelpButton);
    }

    /**
     * Changes style of new selected button and resets the style of the previously selected
     * button.
     * @param button the new selected button.
     */
    private void setSelected(Button button) {
        if (currentlySelected != null) {
            currentlySelected.setStyle("");
        }
        if (button != null) {
            currentlySelected = button;
            button.setStyle("-fx-border-color: #FFFFFF");
        }

    }

    /**
     * Calls the initPageInfo function with the Map Help html file as input.
     * @param actionEvent Load Map Help button clicked.
     */
    public void loadMapHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/MapHelpPage.html"));
        setSelected(mapHelpButton);
    }

    /**
     * Calls the initPageInfo function with the Data Help html file as input.
     * @param actionEvent Load Data Help button clicked.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/DataHelpPage.html"));
        setSelected(dataHelpButton);
    }

    /**
     *  Calls the initPageInfo function with the general help html file as input.
     * @param actionEvent Loads general help button clicked.
     */
    public void loadGeneralHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/MainHelpPage.html"));
        setSelected(generalHelpButton);
    }

    /**
     * Calls the initPageInfo function with the login / signup html file as input.
     * @param actionEvent Loads login /signup when help button clicked
     */
    public void loadLoginSignupHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/LoginSignupHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the filter html file as input.
     * @param actionEvent Loads filter when help button clicked
     */
    public void loadFilterHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/FilterHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the edit info html file as input.
     * @param actionEvent Loads editing info screen help button clicked
     */
    public void loadAddEditStationHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/EditAddStationHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the my d file as input.
     * @param actionEvent Loads data page help button clicked
     */
    public void loadDetailsHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/DetailsHelpPage.html"));
    }
}
