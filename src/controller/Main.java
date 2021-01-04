package controller;

import controller.dbqueries.ExceptionLog;
import controller.login.LoginController;
import controller.mainwindow.MainController;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Staff;
import model.database.Database;

import java.io.IOException;

public class Main extends Application {

    private BorderPane root;
    private BooleanProperty loggedIn;
    private Property<Staff> loggedInStaff = new SimpleObjectProperty<>();

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

        scene.getStylesheets().addAll(
                getClass().getResource("../view/mainwindow/customer.css").toExternalForm(),
                getClass().getResource("../view/mainwindow/lendArticle.css").toExternalForm()
        );

        scene.getStylesheets().add(getClass().getResource("../view/mainwindow/lendArticle.css").toExternalForm());

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mainwindow/mainWindow.fxml"));
                //show main window
                root.setCenter(loader.load());
                //get main controller
                MainController mainController = loader.getController();
                mainController.setLoggedInStaff(loggedInStaff);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login/login.fxml"));
                //show login window
                root.setCenter(loader.load());
                //get login controller
                LoginController loginController = loader.getController();
                loginController.setLoggedIn(loggedIn);
                loginController.setLoggedInStaff(loggedInStaff);
            }
        }catch (IOException e){
            ExceptionLog.write(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
