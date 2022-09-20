package seng202.team6.controller;

import javafx.scene.control.Alert;
/**
 * Alert class to pop up a message box and display the message.
 * database access.
 * @author Phyu Wai Lwin.
 */
public class AlertMessage {

    /**
     * Alert Message class constructor that
     */
    private AlertMessage() {
        throw new IllegalStateException("Alert Message Class");
    }

    /**
     * Creates a pop-up message and displays the error.
     * @param header The header message.
     * @param body The error messages.
     */
    public static void createMessage(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message:");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
