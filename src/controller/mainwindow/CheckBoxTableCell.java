package controller.mainwindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * create a tableCell with a checkbox in it
 * @param <S> type
 * @author https://stackoverflow.com/a/7973514/12913973
 */
public class CheckBoxTableCell<S> extends TableCell<S,Boolean> {

    private CheckBox checkBox;

    public CheckBoxTableCell(boolean editable) {
        checkBox = new CheckBox();
        checkBox.setDisable(true);
        if(editable)
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(editable);
    }
    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.setDisable(false);
        checkBox.requestFocus();
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setDisable(true);
    }
    public void commitEdit(Boolean value) {
        super.commitEdit(value);
        checkBox.setDisable(true);
    }
    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            checkBox.setSelected(item);
        }
    }
}
