package controller.mainwindow;

import controller.ShowAlert;
import controller.dbqueries.CustomerQueries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane articlePane;

    @FXML
    private BorderPane customerPane;

    @FXML
    private TextField addCustomerFirstName;

    @FXML
    private TextField addCustomerLastName;

    private CustomerTable customerTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable = new CustomerTable();
        customerPane.setCenter(customerTable);
    }

    @FXML
    void addCustomerFirstNameKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            saveCustomer();
    }

    @FXML
    void addCustomerLastNameKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            saveCustomer();
    }

    @FXML
    void addCustomerSaveMouseClicked(MouseEvent event) {
        saveCustomer();
    }

    public void saveCustomer(){

        String firstName = addCustomerFirstName.getText();
        String lastName = addCustomerLastName.getText();

        if(firstName.length() < 1)
            ShowAlert.showInformation("Vorname ist leer");

        else if(lastName.length() < 1)
            ShowAlert.showInformation("Nachname ist leer");

        else {
            int cid = CustomerQueries.addCustomer(firstName, lastName);
            Customer c = new Customer(cid,firstName,lastName);
            customerTable.addItem(c);
            ShowAlert.showInformation("erfolgreich gespeichert");
        }
    }
}
