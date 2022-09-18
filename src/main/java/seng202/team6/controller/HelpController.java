package seng202.team6.controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class HelpController implements ScreenController {
    private Stage stage;
    @FXML
    private TextArea linesTextArea;


    @Override
    public void init(Stage stage,MainScreenController controller) {
        this.stage = stage;
        showFileLines(getClass().getResourceAsStream("/TextFiles/MainHelpPage.txt"));
    }

    public void showFileLines(InputStream file) {
        this.linesTextArea.clear();

        List<String> lines = new ArrayList<String>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file));

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (String eachLine : lines ) {
            this.linesTextArea.appendText(eachLine + "\n");
        }
    }
}