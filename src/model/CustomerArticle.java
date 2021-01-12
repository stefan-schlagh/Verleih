package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.List;

public class CustomerArticle {

    private Loan loan;
    private StringProperty articleName = new SimpleStringProperty();
    private StringProperty staffName = new SimpleStringProperty();
    private StringProperty startDateString = new SimpleStringProperty();
    private StringProperty endDateString = new SimpleStringProperty();
    private BooleanProperty returned = new SimpleBooleanProperty();

    public CustomerArticle(Loan loan){
        this.loan = loan;
        setArticleName(loan.getArticle().getName());
        setStaffName(loan.getStaff().getName());
        setStartDateString(loan.getStartDateString());
        setEndDateString(loan.getEndDateString());
        setReturned(loan.isReturned());
    }

    public void update(){
        setArticleName(loan.getArticle().getName());
        setStaffName(loan.getStaff().getName());
        setStartDateString(loan.getStartDateString());
        setEndDateString(loan.getEndDateString());
        setReturned(loan.isReturned());
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getArticleName() {
        return articleName.get();
    }

    public StringProperty articleNameProperty() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName.set(articleName);
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

    public boolean isReturned() {
        return returned.get();
    }

    public BooleanProperty returnedProperty() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned.set(returned);
    }

    public static List<CustomerArticle> toCustomerArticleList(List<Loan> loans){

        List<CustomerArticle> customerArticles = new ArrayList<>();

        for(int i = 0;i < loans.size();i++){
            customerArticles.add(new CustomerArticle(loans.get(i)));
        }
        return customerArticles;
    }
}
