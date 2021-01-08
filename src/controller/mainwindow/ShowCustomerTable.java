package controller.mainwindow;

import controller.mainwindow.customerarticles.CustomerArticles;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import model.Customer;

import java.io.IOException;

public class ShowCustomerTable extends CustomerTable {

    public ShowCustomerTable() throws IOException {
        super();

        TableColumn<Customer, Button> showActiveCol = new TableColumn<>("Zeige verleihte Artikel");
        getTable().getColumns().add(showActiveCol);

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Artikel",(Customer c) -> {

            CustomerArticles dialog = new CustomerArticles(c);
            dialog.showAndWait();
            return c;
        }));
    }
}
