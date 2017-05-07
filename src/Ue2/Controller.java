package Ue2;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by saubaer on 26.04.17.
 * PackageName: Ue2
 * ProjektName: Visualization_Jung
 */
public class Controller {


    public Canvas canvas;
    public Slider timeSlider;
    public Pane canvasHolder;
    public Slider roundSlider;
    public TabPane tabPane;
    public TextField textField;
    public BarChart chart;
    public Button okButton;
    public Slider objectSlider;

    private int realCount;
    private final XYChart.Series series = new XYChart.Series();

    public void startRound() {

        realCount = 0;

        tabPane.getSelectionModel().select(0);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            GraphicsContext gc = canvas.getGraphicsContext2D();
            int count = (int) roundSlider.getValue();

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= timeSlider.getValue() * 1000000) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    if (count == 0) {
                        stop();
                        tabPane.getSelectionModel().select(1);
                        okButton.setDisable(false);
                    } else {
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        showCanvas(gc);
                        lastUpdate = now;
                        count -= 1;
                    }
                }

            }
        };
        timer.start();
    }

    public void calculateResult() {

        int userInput = Integer.parseInt(textField.getText());
        series.getData().add(new XYChart.Data("Anzahl", realCount));
        series.getData().add(new XYChart.Data("geschätze Anzahl", userInput));

        chart.getData().addAll(series);

        okButton.setDisable(true);
    }


    private void showCanvas(GraphicsContext gc) {

        double[] x = ThreadLocalRandom.current().doubles(0, canvas.getWidth() - 20).distinct().limit((long) objectSlider.getValue()).toArray();
        double[] y = ThreadLocalRandom.current().doubles(0, canvas.getHeight() - 20).distinct().limit((long) objectSlider.getValue()).toArray();
        //OptionalDouble w = ThreadLocalRandom.current().doubles(0, 20).distinct().limit(1).findFirst();
        //OptionalDouble h = ThreadLocalRandom.current().doubles(0, 20).distinct().limit(1).findFirst();

        for (int i = 0; i < objectSlider.getValue() - 1; i++) {
            gc = canvas.getGraphicsContext2D();
            gc.fillOval(x[i], y[i], 20, 20);
        }
        Random r = new Random();
        if (r.nextInt(1) == 0) {
            //TODO
            gc.fillRect(x[20], y[20], 20, 20);
            realCount++;
        }

    }
}
