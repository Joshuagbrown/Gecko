package seng202.team6.controller;

import javafx.scene.control.TableCell;
import javafx.scene.text.Text;


/**
 * Class used when multiple lines are needed in a single cell.
 * @param <S> type of cell
 */
class MultiLineCell<S> extends TableCell<S, String> {


    /**
     * Function used to update item.
     * @param item the String to update
     * @param empty if it is empty or not
     */
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
