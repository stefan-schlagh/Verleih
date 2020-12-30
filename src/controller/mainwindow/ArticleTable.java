package controller.mainwindow;

import controller.dbqueries.ArticleQueries;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Article;

public class ArticleTable extends TableView<Article> {

    ObservableList<Article> articleObservableList;

    public ArticleTable(){

        TableColumn<Article, Integer> idCol = new TableColumn<>("Artikelnummer");
        TableColumn<Article, String> nameCol = new TableColumn<>("Artikelname");

        //TODO availableCol

        getColumns().addAll(idCol,nameCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("aid"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        articleObservableList = ArticleQueries.getArticleList();
        setItems(articleObservableList);

        setEditable(true);
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
    }

    public void addItem(Article a){
        articleObservableList.add(a);
    }

    public void updateItems(){
        articleObservableList = ArticleQueries.getArticleList();
        setItems(articleObservableList);
    }
}
