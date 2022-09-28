package seng202.team6.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * Alert class to pop up a message box and display the message.
 * database access.
 * @author Phyu Wai Lwin.
 */
public class AlertMessage {

    /**
     * Alert Message class constructor that.
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

    /**
     * Creates a pop-up message and displays the error.
     * @param title The title of the pop-up.
     * @param header The header of the pop-up.
     * @param rows The list of values to show.
     */
    public static void createListMessage(String title, String header, List<String> rows) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        ListView<String> listView = new ListView<>();
        listView.getItems().setAll(rows);
        listView.setPrefWidth(Region.USE_COMPUTED_SIZE);
        alert.getDialogPane().setContent(listView);
        alert.getDialogPane().setPrefWidth(300);
        alert.showAndWait();
    }
}
