package seng202.team6.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScreen<ScrollPaneMainScreen> {


    public Parent LoadBigScreen(Stage stage, String screen, MainScreenController controller) throws IOException {
        //Parent dataViewParent = null;
        //Parent dataViewParent = null;

            // Load our sales_table.fxml file
            FXMLLoader viewLoader = new FXMLLoader(getClass().getResource(screen));
            // Get the root FXML element after loading
            Parent dataViewParent = viewLoader.load();
            // Get access to the controller the FXML is using
            ScreenController screenController = viewLoader.getController();

            // Initialise the controller
            screenController.init(stage, controller);

            //controller.setScreenController( viewLoader.getController());

            // Set the root of our new component to the center of the borderpane
            //ScrollPaneMainScreen.setContent(dataViewParent);
            //return viewLoader;
            return dataViewParent;


        //return dataViewParent;
        //return (dataViewParent);
    }

    public Parent LoadToolBar(Stage stage, String toolBar, BorderPane toolBarPane,MainScreenController controller) throws IOException {

        //Parent mapToolBarParent = null;


            FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource(toolBar));


            // Get the root FXML element after loading
            Parent toolBarParent = toolBarLoader.load();
            // Get access to the controller the FXML is using
            ToolBarController toolBarController = toolBarLoader.getController();
            // Initialise the controller
            toolBarController.init(stage, controller);

            // Set the root of our new component to the center of the borderpane

            return toolBarParent;




    }
}
