package seng202.team6.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        showFileLines("C:\\Users\\64211\\Desktop\\Team_6 project\\team-6\\src\\main\\java\\seng202\\team6\\TextFiles\\MainHelpPage.txt");
    }

    public void showFileLines(String file) {
        linesTextArea.clear();

        List<String> lines = new ArrayList<String>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (String eachLine : lines ) {
            linesTextArea.appendText(eachLine + "\n");
        }
    }
}