package controller.mainwindow;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Customer;

public class CustomerTable extends TableView<Customer> {

    public CustomerTable() {

        TableColumn<Customer, String> vornameCol = new TableColumn<>("Vorname");
        TableColumn<Customer, String> nachnameCol = new TableColumn<>("Nachname");

        TableColumn<Customer,String> nameCol = new TableColumn<>("Name");

        TableColumn<Customer,Button> showActiveCol = new TableColumn("Zeige aktive Positionen");

        nameCol.getColumns().addAll(vornameCol,nachnameCol);
        this.getColumns().addAll(nameCol,showActiveCol);

        vornameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachnameCol.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        //showActiveCol.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Remove",(Customer c) -> {
            //this.getItems().remove(c);
            System.out.println(c);
            return c;
        }));
    }
}
