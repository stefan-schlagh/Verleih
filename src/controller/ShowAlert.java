package controller;

import javafx.scene.control.Alert;

public class ShowAlert {

    public static void showInformation(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
