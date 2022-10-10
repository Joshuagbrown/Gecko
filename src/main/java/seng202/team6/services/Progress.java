package seng202.team6.services;


import javafx.scene.control.ProgressBar;
import seng202.team6.controller.ProgressController;

/**
 * Class to initialize progress bar pop-up.
 * Author: Tara Lipscombe
 */
public class Progress {

    /**
     * Fills out the progress bar.
     */
    public static void fill(ProgressController controller) {
        float current = 0F;

        while (current < 1F) {
            controller.setProgress(current);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            current += 0.1F;

        }
    }


}
