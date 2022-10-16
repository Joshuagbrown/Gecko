package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 * Controller class for help screen fxml.
 */
public class HelpController implements ScreenController {
    /**
     * the text Area to load the information.
     */
    @FXML
    private WebView helpPage;
    @FXML
    public TextArea linesTextArea;


    /**
     * Function to initialize the help screens.
     * @param stage Primary Stage of the application.
     * @param controller The Controller class for the main screen.
     */
    @Override
    public void init(Stage stage,MainScreenController controller) {
        initPageInfo(getClass().getResourceAsStream("/html/MainHelpPage.html"));

    }

    /**
     * Takes a html file and sets the page.
     * @param file html file
     */
    public void initPageInfo(InputStream file) {
        WebEngine webEngine;
        webEngine = helpPage.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.loadContent(getHtml(file));
        String uri = getClass().getResource("/stylesheets/htmlMain.css").toExternalForm();
        webEngine.setUserStyleSheetLocation(uri);
    }

    /**
     * This function takes a html file, reads it, and displays it in the text area.
     * @param file input html file
     */
    public String getHtml(InputStream file) {
        assert file != null;
        return new BufferedReader(
                new InputStreamReader(file, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}