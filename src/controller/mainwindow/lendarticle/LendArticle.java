package controller.mainwindow.lendarticle;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LendArticle extends Stage {

    public LendArticle(){
        super();
        setTitle("artikel verleihen");
        Scene scene = new Scene(new Pane());
        this.setScene(scene);
    }
}
