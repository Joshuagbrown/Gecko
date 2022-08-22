package seng202.team6.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScreen<ScrollPaneMainScreen> {


    public Parent LoadBigScreen(Stage stage, String screen, ScrollPane ScrollPaneMainScreen) throws IOException {
        //Parent dataViewParent = null;
        Parent dataViewParent = null;
        try {
            // Load our sales_table.fxml file
            FXMLLoader viewLoader = new FXMLLoader(getClass().getResource(screen));
            // Get the root FXML element after loading
            dataViewParent = viewLoader.load();
            // Get access to the controller the FXML is using
            ScreenController screenController = viewLoader.getController();
            // Initialise the controller
            screenController.init(stage);

            // Set the root of our new component to the center of the borderpane
            //ScrollPaneMainScreen.setContent(dataViewParent);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataViewParent;
        //return (dataViewParent);
    }

    public void LoadToolBar(Stage stage, String toolBar, BorderPane toolBarPane,ScrollPane ScrollPaneMainScreen) throws IOException {

        //Parent mapToolBarParent = null;
        try {

            FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource(toolBar));


            // Get the root FXML element after loading
            Parent toolBarParent = toolBarLoader.load();
            // Get access to the controller the FXML is using
            ToolBarController toolBarController = toolBarLoader.getController();
            // Initialise the controller
            toolBarController.init(stage,ScrollPaneMainScreen);

            // Set the root of our new component to the center of the borderpane

            toolBarPane.setCenter(toolBarParent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
