package controller.mainwindow.articlehistory;

import TableFilter.FilterTable;
import TableFilter.Filterable;
import controller.dbqueries.LoanQueries;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.ArticleLoan;
import model.Loan;

import java.io.IOException;
import java.util.List;

public class ArticleHistoryTable extends FilterTable<ArticleLoan> {
    public ArticleHistoryTable(Article a) throws IOException {
        super("Suchen:");

        TableColumn<ArticleLoan,String> staffCol = new TableColumn<>("angelegt von");
        TableColumn<ArticleLoan,String> customerCol = new TableColumn<>("Kunde");
        TableColumn<ArticleLoan,String> startDateCol = new TableColumn<>("Start");
        TableColumn<ArticleLoan,String> endDateCol = new TableColumn<>("Ende");

        List<Loan> loanList = LoanQueries.getArticleLoans(a.getAid());

        addData(ArticleLoan.toArticleLoanList(loanList));

        getTable().getColumns().addAll(staffCol,customerCol,startDateCol,endDateCol);

        staffCol.setCellValueFactory(new PropertyValueFactory<ArticleLoan,String>("staffName"));
        customerCol.setCellValueFactory(new PropertyValueFactory<ArticleLoan,String>("customerName"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<ArticleLoan,String>("startDateString"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<ArticleLoan,String>("endDateString"));

        getFilterableList().add(new Filterable<ArticleLoan>() {
            @Override
            public String getFilterString(ArticleLoan articleLoan) {
                return articleLoan.getStaffName();
            }
        });
        getFilterableList().add(new Filterable<ArticleLoan>() {
            @Override
            public String getFilterString(ArticleLoan articleLoan) {
                return articleLoan.getCustomerName();
            }
        });
        getFilterableList().add(new Filterable<ArticleLoan>() {
            @Override
            public String getFilterString(ArticleLoan articleLoan) {
                return articleLoan.getStartDateString();
            }
        });
        getFilterableList().add(new Filterable<ArticleLoan>() {
            @Override
            public String getFilterString(ArticleLoan articleLoan) {
                return articleLoan.getEndDateString();
            }
        });
    }
}
