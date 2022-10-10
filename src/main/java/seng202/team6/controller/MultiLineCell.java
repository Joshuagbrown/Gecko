package seng202.team6.controller;

import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

class MultiLineCell<S> extends TableCell<S, String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        Text text = new Text();
        setGraphic(text);
        text.wrappingWidthProperty().bind(widthProperty());
        text.textProperty().bind(itemProperty());
        text.setStyle("-fx-fill: -fx-text-background-color;");
        text.setLineSpacing(5);
    }
}
