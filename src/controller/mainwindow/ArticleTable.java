package controller.mainwindow;

import TableFilter.FilterTable;
import TableFilter.Filterable;
import controller.ShowAlert;
import controller.dbqueries.ArticleQueries;
import controller.mainwindow.articlehistory.ArticleHistory;
import controller.mainwindow.lendarticle.LendArticle;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.Article;
import model.Staff;

import javax.swing.*;
import java.io.IOException;

public class ArticleTable extends FilterTable<Article> {

    public static final int FILTER_ALL = 0;
    public static final int FILTER_AVAILABLE = 1;
    public static final int FILTER_NOT_AVAILABLE = 2;

    private ObservableList<Article> articleObservableList;
    private Property<Staff> loggedInStaff;
    private LendArticle lendArticleDialog;
    private int selectedFilter = FILTER_ALL;

    public ArticleTable() throws IOException {
        super("Suchen:");

        TableColumn<Article, Integer> idCol = new TableColumn<>("Artikelnummer");
        TableColumn<Article, String> nameCol = new TableColumn<>("Artikelname");
        TableColumn<Article, Boolean> availableCol = new TableColumn<>("verfügbar");
        TableColumn<Article, Button> showHistory = new TableColumn<>("Zeige vergangene V.");
        TableColumn<Article, Button> lendArticle = new TableColumn<>("Artikel verleihen");

        getTable().getColumns().addAll(idCol,nameCol,availableCol,showHistory,lendArticle);

        idCol.setCellValueFactory(new PropertyValueFactory<>("aid"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        articleObservableList = ArticleQueries.getArticleList(selectedFilter);
        addData(articleObservableList);

        getTable().setEditable(true);
        idCol.setEditable(false);

        nameCol.setEditable(true);
        nameCol.setCellFactory(TextFieldTableCell.<Article>forTableColumn());
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Article, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Article, String> event) {
                String newValue = event.getNewValue();
                TablePosition<Article,String> pos = event.getTablePosition();

                Article a = event.getTableView().getItems().get(pos.getRow());
                a.setName(newValue);
                //update in DB
                ArticleQueries.updateArticle(a);
            }
        });

        Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>> booleanCellFactory =
                new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> p) {
                        return new CheckBoxTableCell<Article>(false);
                    }
                };
        availableCol.setCellFactory(booleanCellFactory);
        availableCol.setCellValueFactory(new PropertyValueFactory<Article,Boolean>("available"));
        availableCol.setEditable(false);

        showHistory.setCellFactory(ActionButtonTableCell.<Article>forTableColumn("anzeigen",(Article a) -> {

            ArticleHistory dialog = new ArticleHistory(a);
            dialog.showAndWait();
            return a;
        }));

        lendArticle.setCellFactory(ActionButtonTableCell.<Article>forTableColumn("verleihen",(Article a) -> {
            // is article available?
            if(a.isAvailable()) {
                lendArticleDialog = new LendArticle(a, loggedInStaff);
                lendArticleDialog.showAndWait();
            }else
                ShowAlert.showInformation("Artikel derzeit nicht verfügbar!");

            return a;
        }));

        addFilterProperty(new Filterable<Article>() {
            @Override
            public String getFilterString(Article article) {
                return article.getName();
            }
        });
        /*
            filter comboBox
         */
        ComboBox<String> filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll(
                "alle",
                "verfügbar",
                "verliehen"
        );
        filterComboBox.getSelectionModel().select(0);
        filterComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedFilter = filterComboBox.getSelectionModel().getSelectedIndex();
                updateData();
            }
        });
        getSearchBoxChildren().add(filterComboBox);
    }

    public void addItem(Article a){
        addData(a);
    }

    public ObservableList<Article> getArticleObservableList() {
        return articleObservableList;
    }

    public void setLoggedInStaff(Property<Staff> loggedInStaff) {
        this.loggedInStaff = loggedInStaff;
    }
    /**
     * update data of articleTable
     */
    public void updateData(){
        removeAllData();
        articleObservableList = ArticleQueries.getArticleList(selectedFilter);
        addData(articleObservableList);
    }
}
