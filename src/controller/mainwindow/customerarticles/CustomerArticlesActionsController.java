package controller.mainwindow.customerarticles;

import controller.ShowAlert;
import controller.dbqueries.ArticleQueries;
import controller.dbqueries.LoanQueries;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Article;
import model.CustomerArticle;
import model.Loan;
import java.time.LocalDate;


public class CustomerArticlesActionsController {

    private CustomerArticle customerArticle;
    private Loan loan;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label articleLabel;

    @FXML
    void returnNowClicked(MouseEvent event) {
        // set end date to today
        loan.setEndDate(LocalDate.now());
        // set returned to true
        loan.setReturned(true);
        //update
        LoanQueries.updateLoan(loan);
        customerArticle.update();
        // update article
        Article a = loan.getArticle();
        a.setAvailable(true);
        ArticleQueries.updateArticle(a);
        // show prompt
        ShowAlert.showInformation("Artikel wurde erfolgreich als zurückgegeben eingetragen!");
    }

    public void init() {

        articleLabel.setText("Aktionen für Artikel " + getLoan().getArticle().getName());
        //set value of datePicker
        endDatePicker.setValue(loan.getEndDate());
        /*
            set boundaries
            end date has to be at least 1 week in the future,
            max 6 months in the future
         */
        LocalDate dateNow = LocalDate.now();
        LocalDate minDate = dateNow.plusDays(7);
        LocalDate maxDate = dateNow.plusMonths(6);
        endDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
        //listen when value of datePicker is changed
        endDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldDate, LocalDate newDate) {
                // set end date and update loan
                loan.setEndDate(newDate);
                LoanQueries.updateLoan(loan);
                customerArticle.update();
                // show prompt
                ShowAlert.showInformation("Rückgabedatum wurde geändert");
            }
        });
    }

    public CustomerArticle getCustomerArticle() {
        return customerArticle;
    }

    public void setCustomerArticle(CustomerArticle customerArticle) {
        this.customerArticle = customerArticle;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
