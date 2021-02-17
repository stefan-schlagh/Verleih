package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ShowAlert {
    /**
     * show information dialog
     * @param text the text to be shown
     */
    public static void showInformation(String text){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(text);
        alert.showAndWait();
    }
    /**
     * show confirmation dialog
     * @param text the text to be shown
     * @return the selection
     */
    public static ButtonType showConfirmation(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        return alert.getResult();
    }
}
