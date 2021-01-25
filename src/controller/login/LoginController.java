package controller.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Staff;

import controller.dbqueries.LoginException;
import controller.dbqueries.StaffQueries;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private BooleanProperty loggedIn;
    private Property<Staff> loggedInStaff;

    @FXML
    private TextField loginName;

    @FXML
    private Label nameErrorMsg;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Label passwordErrorMsg;

    @FXML
    void nameKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            submit();
    }

    @FXML
    void onSubmitClick(MouseEvent event) {
        submit();
    }

    @FXML
    void passwordKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            submit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideErrorMessages();
    }
    /**
     * hide error messages
     */
    public void hideErrorMessages(){
        nameErrorMsg.setVisible(false);
        nameErrorMsg.setManaged(false);
        passwordErrorMsg.setVisible(false);
        passwordErrorMsg.setManaged(false);
    }
    /**
        is called when submit button is pressed or after enter in textField
     */
    void submit () {

        hideErrorMessages();

        String name = loginName.getText();
        String password = loginPassword.getText();
        // is name empty?
        if(name.length() < 1){
            nameErrorMsg.setVisible(true);
            nameErrorMsg.setManaged(true);
            nameErrorMsg.setText("Pflichtfeld!");
        }
        // is password empty?
        if(password.length() < 1){
            passwordErrorMsg.setVisible(true);
            passwordErrorMsg.setManaged(true);
            passwordErrorMsg.setText("Pflichtfeld!");
        }
        // both filled
        if(name.length() > 1 && password.length() > 1){
            try{
                int sid = StaffQueries.login(name,password);

                Staff staff = new Staff(sid,name);
                loggedInStaff.setValue(staff);

                loggedIn.setValue(true);

            }catch (LoginException e){
                if(e.getErrorCode() == LoginException.USERNAME_NOT_EXISTING){
                    nameErrorMsg.setVisible(true);
                    nameErrorMsg.setManaged(true);
                    nameErrorMsg.setText("Benutzername nicht vorhanden!");
                }else if(e.getErrorCode() == LoginException.WRONG_PASSWORD) {
                    passwordErrorMsg.setVisible(true);
                    passwordErrorMsg.setManaged(true);
                    passwordErrorMsg.setText("falsches Passwort!");
                }
            }
        }
    }

    public void setLoggedIn(BooleanProperty loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setLoggedInStaff(Property<Staff> loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
    }
}
