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
     * Calls the initPageInfo function with the login html file as input.
     * @param actionEvent Loads login help button clicked
     */
    public void loadLoginHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/LoginHelpPage.html"));
        setSelected(loginHelpButton);
    }
}
