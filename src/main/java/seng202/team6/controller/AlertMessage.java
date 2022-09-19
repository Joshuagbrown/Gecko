package seng202.team6.controller;

import javafx.scene.control.Alert;

public class AlertMessage {

    public static void createMessage(String header, String body)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message:");
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }
}
