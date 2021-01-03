package controller.mainwindow;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import model.Customer;

import java.io.IOException;

public class ShowCustomerTable extends CustomerTable {

    public ShowCustomerTable() throws IOException {
        super();

        TableColumn<Customer, Button> showActiveCol = new TableColumn<>("Zeige Positionen");
        getTable().getColumns().add(showActiveCol);

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Positionen",(Customer c) -> {
            //this.getItems().remove(c);
            System.out.println(c);
            return c;
        }));
    }
}
