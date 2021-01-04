package model;

import java.time.LocalDate;

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
    public Loan(int lid,Customer customer, Article article, Staff staff, LocalDate startDate, LocalDate endDate){
        this(customer, article, staff, startDate, endDate);
        this.lid = lid;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isReturned() {
        return returned;
    }
}
