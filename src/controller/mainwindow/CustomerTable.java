package controller.mainwindow;

import controller.dbqueries.CustomerQueries;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Customer;

public class CustomerTable extends TableView<Customer> {

    private ObservableList<Customer> customerObservableList;

    public CustomerTable() {

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("Vorname");
        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Nachname");

        TableColumn<Customer,String> nameCol = new TableColumn<>("Name");

        TableColumn<Customer,Button> showActiveCol = new TableColumn("Zeige Positionen");

        nameCol.getColumns().addAll(firstNameCol,lastNameCol);
        this.getColumns().addAll(nameCol,showActiveCol);

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        //showActiveCol.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Positionen",(Customer c) -> {
            //this.getItems().remove(c);
            System.out.println(c);
            return c;
        }));

        customerObservableList = CustomerQueries.getCustomerList();
        this.setItems(customerObservableList);

        this.setEditable(true);

        addEventToTableColumn(1,firstNameCol);
        addEventToTableColumn(2,lastNameCol);
    }
    public void addItem(Customer c){
        customerObservableList.add(c);
    }
    public void updateItems(){
        customerObservableList = CustomerQueries.getCustomerList();
        this.setItems(customerObservableList);
    }

    public void addEventToTableColumn(int columnIndex,TableColumn<Customer,String> tableColumn){

        tableColumn.setCellFactory(TextFieldTableCell.<Customer>forTableColumn());
        tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                String newValue = event.getNewValue();
                TablePosition<Customer,String> pos = event.getTablePosition();
                System.out.println("Wert: " + newValue + " Person.Person: " + pos.getRow());

                Customer c = event.getTableView().getItems().get(pos.getRow());
                c.setColumn(columnIndex,newValue);
                //update in DB
                CustomerQueries.updateCustomer(c);
            }
        });
    }
}
