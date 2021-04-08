package controller.mainwindow;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PasswordInputDialog extends Dialog<String> {
    private PasswordField passwordField;
    private PasswordField passwordRepeatField;

    public PasswordInputDialog() {
        setTitle("Passwort ändern");
        setHeaderText("Neues Passwort eingeben:");

        ButtonType passwordButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");

        passwordRepeatField = new PasswordField();
        passwordRepeatField.setPromptText("Passwort wiederholen");

        VBox vBox = new VBox();

        vBox.getChildren().add(passwordField);
        vBox.getChildren().add(passwordRepeatField);

        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        HBox.setHgrow(passwordField, Priority.ALWAYS);

        getDialogPane().setContent(vBox);

        Platform.runLater(() -> passwordField.requestFocus());

        setResultConverter(dialogButton -> {
            if (dialogButton.getButtonData() == passwordButtonType.getButtonData()) {
                if(passwordField.getText().equals(passwordRepeatField.getText()))
                    return passwordField.getText();
                return "neq";
            }
            return "err";
        });
    }
}
