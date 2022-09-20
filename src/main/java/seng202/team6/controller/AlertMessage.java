package seng202.team6.controller;

import javafx.scene.control.Alert;

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
     * @param body The error message
     */
    public static void createMessage(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message:");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
