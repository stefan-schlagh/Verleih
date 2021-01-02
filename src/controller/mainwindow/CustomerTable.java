package controller.mainwindow;

import TableFilter.FilterTable;
import TableFilter.Filterable;
import controller.dbqueries.CustomerQueries;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Customer;

import java.io.IOException;

public class CustomerTable extends FilterTable<Customer> {

    private ObservableList<Customer> customerObservableList;

    public CustomerTable() throws IOException {
        super();

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("Vorname");
        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Nachname");

        TableColumn<Customer,String> nameCol = new TableColumn<>("Name");

        TableColumn<Customer,Button> showActiveCol = new TableColumn<>("Zeige Positionen");

        nameCol.getColumns().addAll(firstNameCol,lastNameCol);
        getTable().getColumns().addAll(nameCol,showActiveCol);

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        //showActiveCol.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Positionen",(Customer c) -> {
            //this.getItems().remove(c);
            System.out.println(c);
            return c;
        }));

        customerObservableList = CustomerQueries.getCustomerList();
        addData(customerObservableList);

        getTable().setEditable(true);

        addEventToTableColumn(1,firstNameCol);
        addEventToTableColumn(2,lastNameCol);

        addFilterProperty(new Filterable<Customer>() {
            @Override
            public String getFilterString(Customer customer) {
                return customer.getFirstName();
            }
        });
        addFilterProperty(new Filterable<Customer>() {
            @Override
            public String getFilterString(Customer customer) {
                return customer.getLastName();
            }
        });
    }
    public void addItem(Customer c){
        addData(c);
    }

    public void addEventToTableColumn(int columnIndex,TableColumn<Customer,String> tableColumn){

        tableColumn.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                String newValue = event.getNewValue();
                TablePosition<Customer,String> pos = event.getTablePosition();

                Customer c = event.getTableView().getItems().get(pos.getRow());
                c.setColumn(columnIndex,newValue);
                //update in DB
                CustomerQueries.updateCustomer(c);
            }
        });
    }
}
