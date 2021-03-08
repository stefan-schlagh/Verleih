package controller.mainwindow;

import controller.ShowAlert;
import controller.dbqueries.*;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Article;
import model.Customer;
import model.Staff;
import model.database.Database;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane articlePane;

    @FXML
    private BorderPane customerPane;

    @FXML
    private TextField addArticleName;

    @FXML
    private TextField addCustomerFirstName;

    @FXML
    private TextField addCustomerLastName;

    private ArticleTable articleTable = null;
    private ShowCustomerTable customerTable;

    @FXML
    private Button deleteArticle;

    @FXML
    private Button deleteCustomer;

    @FXML
    private Label usernameLabel;

    private Property<Staff> loggedInStaff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTable = new ShowCustomerTable();
            customerTable.getCustomerProperty().addListener(new ChangeListener<Customer>() {
                @Override
                public void changed(ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) {
                    /*
                        if value is null --> hide deleteCustomer
                        else
                            show
                     */
                    deleteCustomer.setVisible(newValue != null);
                }
            });
            //set visibility of deleteCustomer to false after init
            deleteCustomer.setVisible(false);
            customerPane.setCenter(customerTable);

            articleTable = new ArticleTable();
            articleTable.getArticleProperty().addListener(new ChangeListener<Article>() {
                @Override
                public void changed(ObservableValue<? extends Article> observableValue, Article oldValue, Article newValue) {
                    /*
                        if value is null --> hide deleteCustomer
                        else
                            show
                     */
                    deleteArticle.setVisible(newValue != null);
                }
            });
            deleteArticle.setVisible(false);
            articlePane.setCenter(articleTable);
        } catch (IOException e){
            ExceptionLog.write(e);
        }
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

    public void deleteCustomerMouseClicked(MouseEvent event){

        Customer selectedCustomer = customerTable.getSelectedCustomer();
        if(LoanQueries.getActiveCustomerLoans(selectedCustomer.getCid()).size() > 0)
            ShowAlert.showInformation("Der Kunde hat noch nicht alle Artikel zurückgegeben!");
        else if(ShowAlert.showConfirmation(
                "Kunde " + selectedCustomer.getNameString() + " wirklich löschen?"
            ) == ButtonType.YES) {

            CustomerQueries.deleteCustomer(selectedCustomer);
            customerTable.updateData();
        }
    }

    @FXML
    void addArticleNameKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            saveArticle();
    }
    @FXML
    void addArticleSaveMouseClicked(MouseEvent event) {
        saveArticle();
    }

    public void saveArticle(){
        String name = addArticleName.getText();

        if(name.length() < 1)
            ShowAlert.showInformation("Name ist leer");

        else{
            int aid = ArticleQueries.addArticle(name);
            Article a = new Article(aid,name,true);
            articleTable.addItem(a);
            ShowAlert.showInformation("erfolgreich gespeichert");
        }
    }

    public void deleteArticleMouseClicked(MouseEvent event){

        Article selectedArticle = articleTable.getSelectedArticle();
        if(!selectedArticle.isAvailable())
            ShowAlert.showInformation("Artikel ist derzeit verliehen. Löschen nicht möglich!");
        else if(
            ShowAlert.showConfirmation(
                    "Artikel " + selectedArticle.getName() + " wirklich löschen?"
            ) == ButtonType.YES){

            ArticleQueries.deleteArticle(selectedArticle);
            articleTable.updateData();
        }
    }

    public void setLoggedInStaff(Property<Staff> loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
        //set loggedInStaff further down
        articleTable.setLoggedInStaff(loggedInStaff);
        // set username
        usernameLabel.setText("Benutzername: " + loggedInStaff.getValue().getName());
        this.loggedInStaff.addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observableValue, Staff oldValue, Staff newValue) {
                usernameLabel.setText("Benutzername: " + newValue.getName());
            }
        });
    }

    @FXML
    void articleTabSelected(Event event) {
        /*
            update articleTable, when tab selected
         */
        if(articleTable != null)
            articleTable.updateData();
        //hide delete button
        deleteArticle.setVisible(false);
    }

    @FXML
    void customerTabSelected(Event event) {
        /*
            update customer, when tab selected
         */
        if(customerTable != null)
            customerTable.updateData();
        //hide delete button
        deleteCustomer.setVisible(false);
    }

    @FXML
    void changePassword(MouseEvent event) {
        // input new password
        PasswordInputDialog dialog = new PasswordInputDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> {
            if(password.equals("err"))
                ShowAlert.showInformation("Fehler!");
            else if(password.equals("neq"))
                ShowAlert.showInformation("Passwörter stimmen nicht überein!");
            /*
                check password length
                    length has to be greater than or equal to 8
             */
            else if(password.length() < 8)
                ShowAlert.showInformation("Passwort zu kurz!");
            // set new password
            else {
                StaffQueries.setPassword(loggedInStaff.getValue().getSid(), password);
                ShowAlert.showInformation("Passwort wurde geändert!");
            }
        });
    }

    /*
        delete database, initialize empty one
     */
    @FXML
    void deleteData(MouseEvent event) {
        // ask if they really want to do it
        if(ShowAlert.showConfirmation("Wirklich alles löschen?") == ButtonType.YES) {
            // delete database
            Database.delete();
            // initialize new database
            Database.init();

            ShowAlert.showInformation("Alle Daten gelöscht!");
        }
    }
    @FXML
    void addData(MouseEvent event) {
        if (ShowAlert.showConfirmation("Daten hinzufügen?") == ButtonType.YES) {
            // add standard data to DB
            Database.addData();
            ShowAlert.showInformation("Daten hinzugefügt!");
        }
    }
}
