package controller.mainwindow.lendarticle;

import controller.ShowAlert;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Article;
import model.Customer;
import model.Loan;
import model.Staff;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LendArticleController implements Initializable {

    private Customer customer;
    private Article article;
    private Property<Staff> loggedInStaff;

    @FXML
    private Label labelCustomer;

    @FXML
    private Label labelArticle;

    @FXML
    private Label labelStaff;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    void submitClicked(MouseEvent event) {
        //is endDate filled in?
        if(endDate.getValue() == null)
            ShowAlert.showInformation("Rückgabedatum ist leer!");
        else {
            // model is created
            Loan loan = new Loan(
                    customer,
                    article,
                    loggedInStaff.getValue(),
                    startDate.getValue(),
                    endDate.getValue()
            );
            //TODO: save in DB

        }
    }

    public void setLabelCustomer(String text) {
        labelCustomer.setText(text);
    }

    public void setLabelArticle(String text){
        labelArticle.setText(text);
    }

    public void setLabelStaff(String text) {
        labelStaff.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startDate.setValue(LocalDate.now());

        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = LocalDate.of(minDate.getYear() + 1,minDate.getMonth(),minDate.getDayOfMonth());
        endDate.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setLoggedInStaff(Property<Staff> loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
    }
}
