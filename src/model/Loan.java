package model;

import controller.dbqueries.ArticleQueries;
import controller.dbqueries.LoanQueries;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {

    private int lid;

    private Customer customer;
    private Article article;
    private Staff staff;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean returned;

    /**
     * create a Loan, lid not known already
     * @param customer customer
     * @param article article
     * @param staff staff
     * @param startDate startDate
     * @param endDate endDate, can be changed
     */
    public Loan(Customer customer, Article article, Staff staff, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.article = article;
        this.staff = staff;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returned = false;
    }
    /**
     * create a Loan, lid already known
     * @param customer customer
     * @param article article
     * @param staff staff
     * @param startDate startDate
     * @param endDate endDate, can be changed
     */
    public Loan(int lid,Customer customer, Article article, Staff staff, LocalDate startDate, LocalDate endDate,boolean returned){
        this(customer, article, staff, startDate, endDate);
        this.lid = lid;
        this.returned = returned;
    }
    /**
     * the loan is returned, not updated in DB
     *  --> call
     *      LoanQueries.updateLoan(loan);
     *      ArticleQueries.updateArticle(loan.getArticle);
     */
    public void returnLoan(){
        // set end date to today
        this.setEndDate(LocalDate.now());
        // set returned to true
        this.setReturned(true);
        // update article
        Article a = this.getArticle();
        a.setAvailable(true);
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public int getLid() {
        return lid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Article getArticle() {
        return article;
    }

    public Staff getStaff() {
        return staff;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getStartDateString(){
        return startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getEndDateString(){
        return endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public boolean isReturned() {
        return returned;
    }
}
