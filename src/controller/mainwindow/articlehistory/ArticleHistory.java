package controller.mainwindow.articlehistory;

import controller.dbqueries.ExceptionLog;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Article;

import java.io.IOException;

public class ArticleHistory extends Stage {

    public ArticleHistory(Article a){
        super();

        setTitle("Artikelverlauf");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane,500,400);
        setScene(scene);

        try {

            ArticleHistoryTable articleHistoryTable = new ArticleHistoryTable(a);
            rootPane.setCenter(articleHistoryTable);

        } catch(IOException e){
            ExceptionLog.write(e);

            rootPane.setCenter(new Label("could not load table"));
        }
    }
}
