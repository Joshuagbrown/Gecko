package seng202.team6.controller;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;


/**
 * Alert class to pop up a message box and display the message.
 * database access.
 * @author Phyu Wai Lwin.
 */
public class AlertMessage {


    private static ButtonType cancel;

    /**
     * Alert Message class constructor that.
     */
    private AlertMessage() {
        cancel = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
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

    /**
     * Creates a pop-up message and displays the error.
     * @param title The title of the pop-up.
     * @param header The header of the pop-up.
     * @param rows The list of values to show.
     */
    public static void createListMessageStation(String title, String header, List<String> rows) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        ListView<String> listView = new ListView<>();
        listView.getItems().setAll(rows);
        listView.setPrefWidth(500);
        alert.getDialogPane().setContent(listView);
        alert.getDialogPane().setPrefWidth(500);
        alert.getDialogPane().setPrefHeight(300);
        alert.showAndWait();


    }

    /**
     * Creates a pop-up message and displays the error.
     * @param header The header message.
     * @param body The error messages.
     * @return the optional button type to invoke function call
     */
    public static Optional<ButtonType> createMessageWithAction(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message:");
        alert.setHeaderText(header);
        alert.setContentText(body);
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }


    /**
     * Function to get the button type used to cancel a close request.
     * @return the button type
     */
    public static ButtonType getCancelButton() {
        return cancel;
    }

    /**
     * Creates a pop-up to indicate to the user that they have unsaved
     * changes.
     * @return the alert pop-up
     */
    public static Alert unsavedChanges() {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
        return new Alert(AlertType.WARNING, """
                You currently have unsaved Changes.\s
                Select 'Cancel' to return and save changes.""",
                cancel, ok);
    }

    /**
     * Function called to create a pop-up to let the user know they need to
     * sign in (or sign up) to use this current feature.
     * @return the alert pop-up
     */
    public static Alert noAccess() {
        ButtonType login = new ButtonType("Go to Login / Sign-up");
        return new Alert(AlertType.ERROR, "Unable to access this feature.\n"
                + "Please login or sign-up", login);
    }

    /**
     * Function called to create a pop-up to let the user know they need to
     * sign in (or sign up) to use this current feature (for filtering from home).
     * @return the alert pop-up
     */
    public static Alert notLoggedIn() {
        ButtonType login = new ButtonType("Go to Login / Sign-up");
        return new Alert(AlertType.ERROR, "Home location is not yet assigned.\n"
                + "Please login or sign-up", login);
    }





}
