package controller.mainwindow.customerarticles;

import TableFilter.FilterTable;
import TableFilter.Filterable;
import controller.dbqueries.LoanQueries;
import controller.mainwindow.ActionButtonTableCell;
import controller.mainwindow.CheckBoxTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Customer;
import model.CustomerArticle;
import model.Loan;
import java.io.IOException;
import java.util.List;

public class CustomerArticlesTable extends FilterTable<CustomerArticle> {

    public CustomerArticlesTable(Customer customer) throws IOException {
        super();

        TableColumn<CustomerArticle,String> articleCol = new TableColumn<>("Artikel");
        TableColumn<CustomerArticle,String> staffCol = new TableColumn<>("angelegt von");
        TableColumn<CustomerArticle,String> startDateCol = new TableColumn<>("Start");
        TableColumn<CustomerArticle,String> endDateCol = new TableColumn<>("Ende");
        TableColumn<CustomerArticle,Boolean> returnedCol = new TableColumn<>("zurückgegeben");
        TableColumn<CustomerArticle, Button> actionCol = new TableColumn<>("mehr");

        List<Loan> loanList = LoanQueries.getCustomerLoans(customer.getCid());

        addData(CustomerArticle.toCustomerArticleList(loanList));

        getTable().getColumns().addAll(articleCol,staffCol,startDateCol,endDateCol,returnedCol,actionCol);

        articleCol.setCellValueFactory(new PropertyValueFactory<CustomerArticle,String>("articleName"));
        staffCol.setCellValueFactory(new PropertyValueFactory<CustomerArticle,String>("staffName"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<CustomerArticle,String>("startDateString"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<CustomerArticle,String>("endDateString"));

        actionCol.setCellFactory(ActionButtonTableCell.<CustomerArticle>forTableColumn("mehr",(CustomerArticle customerArticle) -> {
            CustomerArticlesActions dialog = new CustomerArticlesActions(customerArticle);
            dialog.showAndWait();
            return customerArticle;
        }));

        Callback<TableColumn<CustomerArticle, Boolean>, TableCell<CustomerArticle, Boolean>> booleanCellFactory =
                new Callback<TableColumn<CustomerArticle, Boolean>, TableCell<CustomerArticle, Boolean>>() {
                    @Override
                    public TableCell<CustomerArticle, Boolean> call(TableColumn<CustomerArticle, Boolean> p) {
                        return new CheckBoxTableCell<CustomerArticle>(false);
                    }
                };
        returnedCol.setCellFactory(booleanCellFactory);
        returnedCol.setCellValueFactory(new PropertyValueFactory<CustomerArticle,Boolean>("returned"));
        returnedCol.setEditable(false);

        addFilterProperty(new Filterable<CustomerArticle>() {
            @Override
            public String getFilterString(CustomerArticle customerArticle) {
                return customerArticle.getArticleName();
            }
        });
        addFilterProperty(new Filterable<CustomerArticle>() {
            @Override
            public String getFilterString(CustomerArticle customerArticle) {
                return customerArticle.getStaffName();
            }
        });
        addFilterProperty(new Filterable<CustomerArticle>() {
            @Override
            public String getFilterString(CustomerArticle customerArticle) {
                return customerArticle.getStartDateString();
            }
        });
        addFilterProperty(new Filterable<CustomerArticle>() {
            @Override
            public String getFilterString(CustomerArticle customerArticle) {
                return customerArticle.getEndDateString();
            }
        });
    }

}
