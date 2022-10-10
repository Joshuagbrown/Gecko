package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
    private WebView webView;
    @FXML
    public TextArea linesTextArea;

    @Override
    public void init(Stage stage,MainScreenController controller) {
        initPageInfo(getClass().getResourceAsStream("/html/MainHelpPage.html"));

        //showFileLines(getClass().getResourceAsStream("/TextFiles/MainHelpPage.txt"));
    }

    /**
     * Takes a html file and sets the page.
     * @param file html file
     */
    public void initPageInfo(InputStream file) {
        WebEngine webEngine;
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.loadContent(getHtml(file));
        String content = getHtml(getClass().getResourceAsStream("/stylesheets/htmlMain.css"));
        String dataPrefix = "data:text/css;charset=utf-8;base64,";
        webEngine.setUserStyleSheetLocation(dataPrefix
                + Base64.getEncoder().encodeToString(content.getBytes()));
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
    //    public void showFileLines(InputStream file) {
    //        this.linesTextArea.clear();
    //
    //        List<String> lines = new ArrayList<>();
    //        String line;
    //        try {
    //            BufferedReader br = new BufferedReader(new InputStreamReader(file));
    //
    //            while ((line = br.readLine()) != null) {
    //                lines.add(line);
    //            }
    //            br.close();
    //        } catch (IOException ex) {
    //            ex.printStackTrace();
    //        }
    //        for (String eachLine : lines) {
    //            this.linesTextArea.appendText(eachLine + "\n");
    //        }
    //    }
}