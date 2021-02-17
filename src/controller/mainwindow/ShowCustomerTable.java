package controller.mainwindow;

import controller.mainwindow.customerarticles.CustomerArticles;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import model.Customer;

import java.io.IOException;

public class ShowCustomerTable extends CustomerTable {

    private Property<Customer> customerProperty = new SimpleObjectProperty<>();

    public ShowCustomerTable() throws IOException {
        super();

        TableColumn<Customer, Button> showActiveCol = new TableColumn<>("Zeige verliehene Artikel");
        getTable().getColumns().add(showActiveCol);

        showActiveCol.setCellFactory(ActionButtonTableCell.<Customer>forTableColumn("Artikel",(Customer c) -> {

            CustomerArticles dialog = new CustomerArticles(c);
            dialog.showAndWait();
            return c;
        }));

        setCustomerProperty(null);

        getTable().setRowFactory(customerTableView -> {
            TableRow<Customer> row = new TableRow<>();
            row.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                    // is value selected?
                    if(newValue){
                        //set customer selected
                        Customer c = row.getItem();
                        customerProperty.setValue(c);
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void updateData() {
        super.updateData();
        // after updating the data, there is no
        customerProperty.setValue(null);
    }

    public Customer getSelectedCustomer() {
        return customerProperty.getValue();
    }

    public Property<Customer> getCustomerProperty() {
        return customerProperty;
    }

    public void setCustomerProperty(Customer c) {
        customerProperty.setValue(c);
    }
}
