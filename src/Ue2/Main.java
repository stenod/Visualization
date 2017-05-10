package Ue2;

/**
 * Created by saubaer on 26.04.17.
 * PackageName: Ue2
 * ProjektName: Visualization_Jung
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Ue2.fxml"));
        primaryStage.setTitle("Übung 2");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

