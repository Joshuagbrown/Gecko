package seng202.team6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;


/**
 * Controller class for the progress Pop-up.
 */
public class ProgressController {
    @FXML
    private ProgressBar progBar;


    /**
     * Function to set the progress in the progress bar.
     * @param prog the new progess to set the bar too
     */
    public void setProgress(float prog) {
        progBar.setProgress(prog);
    }
}
