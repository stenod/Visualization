package Ue1;

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
    public Label label11;
    public LineChart chartRelation;

    private final ArrayList<Double> x = new ArrayList<Double>();
    private int count = 0;
    private final XYChart.Series series = new XYChart.Series();
    private final XYChart.Series series1 = new XYChart.Series();

    public void calculateX() {
        double mean = 0;
        Random random = new Random();
        int i = random.nextInt(91) + 10;

        double flaecheGeschaetzt = Math.PI * Math.pow(circle.getRadius(), 2.0);
        double flaecheReal = Math.PI * Math.pow(circle2.getRadius(), 2.0);


        x.add(Math.log(0.5) / Math.log(flaecheReal/flaecheGeschaetzt));

        for (double value: x) {
            mean += value;
        }
        //label.setText("reale Fläche: " + flaecheReal + " Fläche: " + flaecheGeschaetzt);
        label1.setText("gewähltes X: " + mean/ x.size());
        label11.setText("gewähltes Verhältnis: " + flaecheReal/flaecheGeschaetzt);

        count++;

        series.getData().add(new XYChart.Data(String.valueOf(count), mean/x.size()));

        series1.getData().add(new XYChart.Data(String.valueOf(count), flaecheReal/flaecheGeschaetzt));

        //chart.getData().add(series);
        circle2.setRadius(i);

        if (!chart.getData().contains(series)) {
            chart.getData().add(series);
            chartRelation.getData().add(series1);

        }
    }
}


