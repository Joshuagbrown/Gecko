package seng202.team6.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * Controller class for help toolbar screen fxml.
 */
public class HelpToolBarController implements ScreenController {

    private MainScreenController controller;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.controller = controller;
    }

    /**
     * Calls the initPageInfo function with the Map Help html file as input.
     * @param actionEvent Load Map Help button clicked.
     */
    public void loadMapHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/MapHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the Data Help html file as input.
     * @param actionEvent Load Data Help button clicked.
     */
    public void loadDataHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/DataHelpPage.html"));
    }

    /**
     *  Calls the initPageInfo function with the general help html file as input.
     * @param actionEvent Loads general help button clicked.
     */
    public void loadGeneralHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/MainHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the login html file as input.
     * @param actionEvent Loads login help button clicked
     */
    public void loadLoginHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/LoginHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the filter html file as input.
     * @param actionEvent Loads filter help button clicked
     */
    public void loadFilterHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/FilterHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the edit info html file as input.
     * @param actionEvent Loads editing info screen help button clicked
     */
    public void loadEditHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/EditDataHelpPage.html"));
    }

    /**
     * Calls the initPageInfo function with the my d file as input.
     * @param actionEvent Loads editing info screen help button clicked
     */
    public void loadDetailsHelp(ActionEvent actionEvent) {
        controller.getHelpController().initPageInfo(getClass().getResourceAsStream(
                "/html/DetailsHelpPage.html"));
    }
}
