package controller.mainwindow.customerarticles;

import controller.dbqueries.ExceptionLog;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;

public class CustomerArticles extends Stage {

    public CustomerArticles(Customer customer){
        super();

        setTitle("Artikel anzeigen");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane,500,400);
        setScene(scene);

        try {

            CustomerArticlesTable customerArticlesTable = new CustomerArticlesTable(customer);
            rootPane.setCenter(customerArticlesTable);

        } catch(IOException e){
            ExceptionLog.write(e);

            rootPane.setCenter(new Label("could not load table"));
        }
    }
}
