package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpController implements ScreenController {
    @FXML
    public TextArea linesTextArea;
    private Stage stage;

    @Override
    public void init(Stage stage,MainScreenController controller) {
        this.stage = stage;
        showFileLines(getClass().getResourceAsStream("/TextFiles/MainHelpPage.txt"));
    }

    /**
     * This function takes a text file, reads it, and displays it in the text area.
     * @param file This takes a text file.
     */
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
        for (String eachLine : lines) {
            this.linesTextArea.appendText(eachLine + "\n");
        }
    }
}