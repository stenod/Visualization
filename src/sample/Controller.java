package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    public Circle circle;
    public Slider slider;
    public Circle circle2;
    public Label label;
    public Label label1;
    public LineChart chart;

    private ArrayList<Double> x = new ArrayList<Double>();
    private int count = 0;
    private XYChart.Series series = new XYChart.Series();

    public void calculateX() {
        double mean = 0;
        Random random = new Random();
        int i = random.nextInt(91) + 10;

        x.add(Math.log(2) / Math.log(circle2.getRadius()/circle.getRadius()));

        for (double value: x) {
            mean += value;
        }
        label1.setText("X = " + mean/ x.size());

        count++;


        series.getData().add(new XYChart.Data(String.valueOf(count), mean/x.size()));
        //chart.getData().add(series);
        circle2.setRadius(i);

        if (!chart.getData().contains(series)) {
            chart.getData().add(series);
        }
    }
}


