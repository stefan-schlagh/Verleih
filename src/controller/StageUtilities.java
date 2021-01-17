package controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

public class StageUtilities {
    /**
     * close the stage.
     * @param event the event that triggered the close. e.g. a ActionEvent
     */
    public static void closeStage(Event event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    /**
     * close the stage.
     * @param node a element within the stage
     */
    public static void closeStage(Node node){
        Stage stage  = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
