package controller.mainwindow.lendarticle;

import controller.dbqueries.ExceptionLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Article;
import model.Customer;

import java.io.IOException;

public class LendArticle extends Stage {

    public LendArticle(Article a){
        super();

        setTitle("Artikel verleihen");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);
        setScene(scene);

        try {
            ChooseCustomerTable chooseCustomerTable = new ChooseCustomerTable();
            rootPane.setCenter(chooseCustomerTable);

            chooseCustomerTable.setCompletionCallback(new CompletionCallback<Customer>() {
                @Override
                public void call(Customer customer) {

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        Parent lendArticlePane =
                                fxmlLoader.load(getClass().getResource("../../../view/mainwindow/lendArticle.fxml").openStream());
                        rootPane.setCenter(lendArticlePane);

                    } catch (IOException e){
                        ExceptionLog.write(e);
                        rootPane.setCenter(new Label("error"));
                    }
                }
            });

        }catch (IOException e){
            ExceptionLog.write(e);
            rootPane.setCenter(new Label("error"));
        }
    }
}
