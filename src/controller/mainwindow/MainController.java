package controller.mainwindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane articlePane;

    @FXML
    private BorderPane customerPane;

    @FXML
    private TextField addCustomerFirstName;

    @FXML
    private TextField addCustomerLastName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerPane.setCenter(new CustomerTable());
    }

    @FXML
    void addCustomerFirstNameKeyPressed(KeyEvent event) {

    }

    @FXML
    void addCustomerLastNameKeyPressed(KeyEvent event) {

    }

    @FXML
    void addCustomerSaveMouseClicked(MouseEvent event) {

    }
}
