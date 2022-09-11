package seng202.team6.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FaqController implements ScreenController {
    public Text generalFeatureText;
    public Text advanceFeatureText;
    private Stage stage;

    @Override
    public void init(Stage stage, MainScreenController controller) {
        this.stage = stage;
        try {
            loadGeneralFeatureText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads user manual text file onto the screen.
     *
     * @throws FileNotFoundException exception thrown when user manual file is not found
     */
    public void loadGeneralFeatureText() throws FileNotFoundException {
        Scanner text = new Scanner(new File("src/main/resources/fxml/UserManual"));

        String lines = "";
        while (text.hasNextLine()) {
            lines += text.nextLine() + '\n';

        }
        generalFeatureText.setText(lines);
    }
}
