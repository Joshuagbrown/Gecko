package seng202.team6.controller;

import java.io.IOException;
import javafx.event.ActionEvent;


/**
 * Interface for add/edit station pop-ups in the application.
 * Author: Tara Lipscombe
 */
public interface StationController {


    /**
     * Function which sets the fields of the edit-station pop up to be filled with the
     * information from the selected station.
     */
    void setFields();


    /**
     * Function to save changes made by user and update database.
     * @param actionEvent when save button is clicked
     */
    void savingChanges(ActionEvent actionEvent) throws IOException, InterruptedException;


    /**
     * Function to check that all new values are of correct type/format.
     */
    Boolean checkValues() throws IOException, InterruptedException;


    /**
     * Function to initialize the view/edit charger pop-up.
     * @param actionEvent when the "View chargers" button is clicked
     */
    void viewChargers(ActionEvent actionEvent) throws IOException;


    /**
     * Function used to delete the currently selected Station from the database and map.
     * @param actionEvent when the user selects the "Delete Station" button
     */
    void deleteSelectedStation(ActionEvent actionEvent);


}
