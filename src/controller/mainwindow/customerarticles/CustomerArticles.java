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

        /*
            TODO:   show articles that are overdue
                    filter returned/ not returned articles
         */
        setTitle("Artikel anzeigen");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane,500,400);
        // add stylesheet
        scene.getStylesheets().add(getClass().getResource("../../../view/mainwindow/mainWindow.css").toExternalForm());
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
