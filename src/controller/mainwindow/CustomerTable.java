package controller.mainwindow;

import controller.dbqueries.CustomerQueries;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

public class CustomerTable extends TableView<Customer> {

    private ObservableList<Customer> customerObservableList;

    public CustomerTable() {

        TableColumn<Customer, String> vornameCol = new TableColumn<>("Vorname");
        TableColumn<Customer, String> nachnameCol = new TableColumn<>("Nachname");

        TableColumn<Customer,String> nameCol = new TableColumn<>("Name");

        TableColumn<Customer,Button> showActiveCol = new TableColumn("Zeige Positionen");

        nameCol.getColumns().addAll(vornameCol,nachnameCol);
        this.getColumns().addAll(nameCol,showActiveCol);

        vornameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nachnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        //showActiveCol.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Positionen",(Customer c) -> {
            //this.getItems().remove(c);
            System.out.println(c);
            return c;
        }));

        customerObservableList = CustomerQueries.getCustomerList();
        this.setItems(customerObservableList);
    }
    public void addItem(Customer c){
        customerObservableList.add(c);
    }
    public void updateItems(){
        customerObservableList = CustomerQueries.getCustomerList();
        this.setItems(customerObservableList);
    }
}
