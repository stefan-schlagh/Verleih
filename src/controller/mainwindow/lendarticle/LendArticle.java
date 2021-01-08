package controller.mainwindow.lendarticle;

import controller.dbqueries.ExceptionLog;
import javafx.beans.property.Property;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Article;
import model.Customer;
import model.Loan;
import model.Staff;

import java.io.IOException;

public class LendArticle extends Stage {

    public LendArticle(Article a, Property<Staff> loggedInStaff){
        super();

        setTitle("Artikel verleihen");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);

        scene.getStylesheets().add(getClass().getResource("../../../view/mainwindow/lendArticle.css").toExternalForm());

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

                        LendArticleController lendArticleController = fxmlLoader.getController();

                        lendArticleController.setCustomer(customer);
                        lendArticleController.setArticle(a);
                        lendArticleController.setLoggedInStaff(loggedInStaff);

                        lendArticleController.setLabelArticle(a.getName());
                        lendArticleController.setLabelCustomer(
                                customer.getFirstName() + " " + customer.getLastName()
                        );
                        lendArticleController.setLabelStaff(loggedInStaff.getValue().getName());
                        //set callback
                        lendArticleController.setCompletionCallback(new CompletionCallback<Loan>() {
                            @Override
                            public void call(Loan loan) {
                                hide();
                            }
                        });

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
