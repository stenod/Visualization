package Ue3;

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

        Parent root = FXMLLoader.load(getClass().getResource("Ue3.fxml"));
        primaryStage.setTitle("Übung 3");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);

    }
}

