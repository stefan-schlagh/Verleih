package controller.mainwindow.lendarticle;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class LendArticleController {

    @FXML
    private GridPane buttonSubmit;

    @FXML
    private Label labelCustomer;

    @FXML
    private Label labelArticle;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    void submitClicked(MouseEvent event) {

    }

}
