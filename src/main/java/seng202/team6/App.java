package seng202.team6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.controller.MainApplication;
import seng202.team6.gui.MainWindow;

/**
 * Default entry point class.
 */
public class App {
    private static final Logger log = LogManager.getLogger();

    /**
     * Entry point which runs the javaFX application.
     * @param args program arguments from command line.
     */
    public static void main(String[] args) {
        MainApplication.main(args);
    }
}
