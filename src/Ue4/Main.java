package Ue4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by saubaer on 12.07.17.
 * PackageName: Ue4
 * ProjektName: Visualization_Jung
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Ue4.fxml"));
        Scene scene = new Scene(root, 950, 600);
        primaryStage.setTitle("Übung 4");
        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);

    }
}
