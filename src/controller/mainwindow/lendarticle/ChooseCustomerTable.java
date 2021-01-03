package controller.mainwindow.lendarticle;

import controller.mainwindow.CustomerTable;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import model.Customer;

import java.io.IOException;

public class ChooseCustomerTable extends CustomerTable {

    private CompletionCallback<Customer> completionCallback;

    public ChooseCustomerTable() throws IOException {
        super();

        //since this table is there to choose a customer, no edit function
        getTable().setEditable(false);
        // choose customer on doubleClick
        getTable().setRowFactory(customerTableView -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY &&
                        event.getClickCount() >= 2) {

                    Customer c = row.getItem();

                    if(c != null && completionCallback != null){
                        completionCallback.call(c);
                    }
                }
            });
            return row;
        });
    }

    public void setCompletionCallback(CompletionCallback<Customer> completionCallback) {
        this.completionCallback = completionCallback;
    }
}
