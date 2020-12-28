package controller;

import controller.dbqueries.LoginException;
import controller.login.LoginController;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.database.Database;

import java.io.IOException;

public class Main extends Application {

    private BorderPane root;
    private BooleanProperty loggedIn;

    @Override
    public void start(Stage stage) throws Exception {

        Database d = new Database();
        d.init();

        root = new BorderPane();
        loggedIn = new SimpleBooleanProperty(false);

        loggedIn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                updateRootPane();
            }
        });

        updateRootPane();

        Scene scene = new Scene(root);

        stage.setTitle("Verleih");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * the pane shown in the root pane gets updated
     */
    private void updateRootPane(){
        try {
            if (loggedIn.getValue()) {
                //show main window
                root.setCenter(FXMLLoader.load(getClass().getResource("../view/mainWindow.fxml")));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
                //show login window
                root.setCenter(loader.load());
                String css = this.getClass().getResource("../view/login.css").toExternalForm();
                root.getStylesheets().add(css);
                //get login controller
                LoginController loginController = loader.getController();
                loginController.setLoggedIn(loggedIn);
            }
        }catch (IOException e){

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
