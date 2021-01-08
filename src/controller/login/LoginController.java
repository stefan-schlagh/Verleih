package controller.login;

import controller.ShowAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Staff;

import controller.dbqueries.LoginException;
import controller.dbqueries.StaffQueries;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LoginController {

    private BooleanProperty loggedIn;
    private Property<Staff> loggedInStaff;

    @FXML
    private VBox login_vBox;

    @FXML
    private TextField loginName;

    @FXML
    private PasswordField loginPassword;

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

    void submit () {

        String name = loginName.getText();
        String password = loginPassword.getText();
        //is name empty?
        if(name.length() < 1)
            ShowAlert.showInformation("name ist leer");
        //is password empty?
        else if(password.length() < 1)
            ShowAlert.showInformation("passwort ist leer");
        else{
            try{
                int sid = StaffQueries.login(name,password);

                Staff staff = new Staff(sid,name);
                loggedInStaff.setValue(staff);

                loggedIn.setValue(true);

            }catch (LoginException e){
                ShowAlert.showInformation(e.getMessage());
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
