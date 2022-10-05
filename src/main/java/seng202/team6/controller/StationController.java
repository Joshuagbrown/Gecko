package seng202.team6.controller;

import javafx.stage.Stage;

/**
 * Interface for add/edit station pop-ups in the application.
 * Author: Tara Lipscombe
 */
public interface StationController {

    /**
     * Init method.
     * @param stage the stage for pop-up
     * @param controller the controller for the mainscreen
     */
    void init(Stage stage, MainScreenController controller);


}
