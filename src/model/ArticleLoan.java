package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class ArticleLoan {

    public Loan loan;
    public StringProperty staffName = new SimpleStringProperty();
    public StringProperty customerName = new SimpleStringProperty();
    private StringProperty startDateString = new SimpleStringProperty();
    private StringProperty endDateString = new SimpleStringProperty();

    public ArticleLoan(Loan loan){
        setLoan(loan);
        setStaffName(loan.getStaff().getName());
        setCustomerName(loan.getCustomer().getName());
        setStartDateString(loan.getStartDateString());
        setEndDateString(loan.getEndDateString());
    }

    public void update(){
        setStaffName(loan.getStaff().getName());
        setCustomerName(loan.getCustomer().getName());
        setStartDateString(loan.getStartDateString());
        setEndDateString(loan.getEndDateString());
    }

    public static List<ArticleLoan> toArticleLoanList(List<Loan> loans){

        List<ArticleLoan> articleLoans = new ArrayList<>();

        for (Loan value : loans) {
            articleLoans.add(new ArticleLoan(value));
        }
        return articleLoans;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getStaffName() {
        return staffName.get();
    }

    public StringProperty staffNameProperty() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName.set(staffName);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getStartDateString() {
        return startDateString.get();
    }

    public StringProperty startDateStringProperty() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString.set(startDateString);
    }

    public String getEndDateString() {
        return endDateString.get();
    }

    public StringProperty endDateStringProperty() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString.set(endDateString);
    }
}
