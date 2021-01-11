package controller.mainwindow.customerarticles;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Loan;
import java.time.LocalDate;


public class CustomerArticlesActionsController {

    private Loan loan;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label articleLabel;

    @FXML
    void returnNowClicked(MouseEvent event) {
        System.out.println("returnNowClicked");
    }

    public void init() {

        articleLabel.setText("Aktionen für Artikel " + getLoan().getArticle().getName());
        //set value of datePicker
        endDatePicker.setValue(loan.getEndDate());
        /*
            set boundaries
            new date has to be after prev endDate
         */
        LocalDate minDate = loan.getEndDate();
        LocalDate maxDate = LocalDate.of(minDate.getYear() + 1,minDate.getMonth(),minDate.getDayOfMonth());
        endDatePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
        //listen when value of datePicker is changed
        endDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate date, LocalDate t1) {
                System.out.println("endDatePickerChanged");
            }
        });
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
