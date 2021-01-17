package controller.mainwindow.customerarticles;

import controller.dbqueries.ExceptionLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CustomerArticle;

import java.io.IOException;

public class CustomerArticlesActions extends Stage {

    public CustomerArticlesActions(CustomerArticle customerArticle){

        super();

        setTitle("Optionen");

        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);

        scene.getStylesheets().add(getClass().getResource("../../../view/mainwindow/customerArticlesActions.css").toExternalForm());

        setScene(scene);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent cAAPane =
                    fxmlLoader.load(getClass().getResource("../../../view/mainwindow/customerArticlesActions.fxml").openStream());

            CustomerArticlesActionsController actionsController = fxmlLoader.getController();

            actionsController.setLoan(customerArticle.getLoan());
            actionsController.setCustomerArticle(customerArticle);
            actionsController.init();

            rootPane.setCenter(cAAPane);

        } catch (IOException e){
            ExceptionLog.write(e);
            rootPane.setCenter(new Label("error"));
        }

    }
}
