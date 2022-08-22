package seng202.team6.controller;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FAQController implements ScreenController{
    public Text generalFeatureText;
    public Text advanceFeatureText;
    private Stage stage;

    @Override
    public void init(Stage stage) {
        this.stage = stage;
        try {
            loadGeneralFeatureText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadGeneralFeatureText() throws FileNotFoundException {
        Scanner text = new Scanner(new File("src/main/resources/fxml/UserManual"));

        String lines = "";
        while (text.hasNextLine()){
            lines += text.nextLine() + '\n';

        }
        generalFeatureText.setText(lines);
    }
}
