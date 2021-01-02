package controller.mainwindow.articlehistory;

import controller.dbqueries.ExceptionLog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ArticleHistory extends Stage {

    public ArticleHistory(){
        super();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../../view/mainWindow/articleHistory.fxml"));

            Scene scene = new Scene(root);
            setTitle("Artikelverlauf");
            this.setScene(scene);
        }catch(IOException e){
            ExceptionLog.write(e);
        }
    }
}
