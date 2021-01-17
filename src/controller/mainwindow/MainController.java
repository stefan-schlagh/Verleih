package controller.mainwindow;

import controller.ShowAlert;
import controller.dbqueries.ArticleQueries;
import controller.dbqueries.CustomerQueries;
import controller.dbqueries.ExceptionLog;
import javafx.beans.property.Property;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private Property<Staff> loggedInStaff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTable = new ShowCustomerTable();
            customerPane.setCenter(customerTable);

            articleTable = new ArticleTable();
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

    public void setLoggedInStaff(Property<Staff> loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
        //set loggedInStaff further down
        articleTable.setLoggedInStaff(loggedInStaff);
    }

    @FXML
    void articleTabSelected(Event event) {
        /*
            update articleTable, when tab selected
         */
        if(articleTable != null)
            articleTable.updateData();
    }
    /*
        delete database, initialize empty one
     */
    @FXML
    void deleteData(MouseEvent event) {
        // delete database
        Database.delete();
        // initialize new database
        Database.init();

        ShowAlert.showInformation("Alle Daten gelöscht!");
    }
    @FXML
    void addData(MouseEvent event) {
        // add standard data to DB
        Database.addData();

        ShowAlert.showInformation("Daten hinzugefügt!");
    }
}
