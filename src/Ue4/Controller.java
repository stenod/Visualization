package Ue4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


/**
 * Created by saubaer on 12.07.17.
 * PackageName: Ue4
 * ProjektName: Visualization_Jung
 */
public class Controller {

    @FXML
    public ScatterChart<Number, Number> scatterChart;
    public ChoiceBox choiceBoxX;
    public ChoiceBox choiceBoxY;
    public ChoiceBox colorChoiceBox;
    public ChoiceBox shapeChoiceBox;
    public TextField shapeLegend;
    public TextField colorLegend;
    public TextField textYellowGreen;
    public TextField textDarkGreen;
    public TextField textYellow;
    public TextField textOrange;
    public TextField textRed;

    private Window stage;
    private String[][] carList = new String[406][11];
    private XYChart.Series marke = new XYChart.Series();
    private final XYChart.Series hersteller = new XYChart.Series();
    private final XYChart.Series mpg = new XYChart.Series();
    private final XYChart.Series zylinder = new XYChart.Series();
    private final XYChart.Series displacement = new XYChart.Series();
    private final XYChart.Series ps = new XYChart.Series();
    private final XYChart.Series gewicht = new XYChart.Series();
    private final XYChart.Series beschleunigung = new XYChart.Series();
    private final XYChart.Series jahr = new XYChart.Series();
    private final XYChart.Series herkunft = new XYChart.Series();

    Color[] colors = new Color[5];


    ObservableList<String> origins = FXCollections.observableArrayList("mpg", "Zylinder", "Displacement", "PS", "Gewicht", "Beschleunigung", "Jahr");

    public void initialize() {

        choiceBoxX.setItems(origins);
        choiceBoxY.setItems(origins);
        colorChoiceBox.setItems(origins);
        shapeChoiceBox.setItems(origins);

        choiceBoxX.getSelectionModel().select(1);
        choiceBoxY.getSelectionModel().select(3);


        colorChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {

                    int indexColor = colorChoiceBox.getSelectionModel().getSelectedIndex()+2;
                    double[] color = new double[406];

                    int i = 0;
                    for (String[] car : carList) {
                        color[i] = Double.parseDouble(car[indexColor]);
                        i++;
                    }

                    double max = 0;
                    for (int counter = 1; counter < color.length; counter++)
                    {
                        if (color[counter] > max)
                        {
                            max = color[counter];
                        }
                    }

                    colors[0] = Color.GREENYELLOW;
                    colors[1] = Color.DARKGREEN;
                    colors[2] = Color.YELLOW;
                    colors[3] = Color.ORANGE;
                    colors[4] = Color.RED;



                    for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
                        int j = 0;
                        for (XYChart.Data<Number, Number> d : s.getData()) {
                            d.getNode().getStyleClass().removeAll("greenYellow","darkGreen","yellow","orange","red");
                            String c = null;
                            if (color[j] < max/5) {
                                c = "greenYellow";
                            } else if (color[j] < 2*(max/5)) {
                                c = "darkGreen";
                            } else if (color[j] < 3*(max/5)) {
                                c = "yellow";
                            } else if (color[j] < 4*(max/5)) {
                                c = "orange";
                            } else if (color[j] <= max) {
                                c = "red";
                            }

                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(new_val + " = " + d.getXValue().toString() + "\n" +
                                    choiceBoxY.getValue().toString() + " = " + d.getYValue().toString());
                            Tooltip.install(d.getNode(), tooltip);

                            d.getNode().getStyleClass().add(c);
                            //Adding class on hover
                            d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                            //Removing class on exit
                            d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
                            j++;
                        }
                    }

                    textYellowGreen.setText(" <= " + max/5);
                    textDarkGreen.setText(" <= " + 2*(max/5));
                    textYellow.setText(" <= " + 3*(max/5));
                    textOrange.setText(" <= " + 4*(max/5));
                    textRed.setText(" <= " + max);

                }
        );


        shapeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {

                    int indexShape = shapeChoiceBox.getSelectionModel().getSelectedIndex()+2;
                    double[] shape = new double[406];

                    int i = 0;
                    for (String[] car : carList) {
                        shape[i] = Double.parseDouble(car[indexShape]);
                        i++;
                    }

                    double max = 0;
                    for (int counter = 1; counter < shape.length; counter++)
                    {
                        if (shape[counter] > max)
                        {
                            max = shape[counter];
                        }
                    }



                    for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
                        int j = 0;
                        for (XYChart.Data<Number, Number> d : s.getData()) {
                            double c = 0;
                            if (shape[j] < max/5) {
                                c = 0.75;
                            } else if (shape[j] < 2*(max/5)) {
                                c = 1;
                            } else if (shape[j] < 3*(max/5)) {
                                c = 1.25;
                            } else if (shape[j] < 4*(max/5)) {
                                c = 1.5;
                            } else if (shape[j] <= max) {
                                c = 1.75;
                            }

                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(new_val + " = " + d.getXValue().toString() + "\n" +
                                    choiceBoxY.getValue().toString() + " = " + d.getYValue().toString());
                            Tooltip.install(d.getNode(), tooltip);

                            d.getNode().setScaleX(c);
                            d.getNode().setScaleY(c);

                            //Adding class on hover
                            d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                            //Removing class on exit
                            d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
                            j++;
                        }
                    }

                }
        );



        choiceBoxX.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    XYChart.Series<Number, Number> series = new XYChart.Series<>();

                    scatterChart.getData().clear();
                    scatterChart.getXAxis().setLabel(new_val.toString());

                    int indexX = choiceBoxX.getSelectionModel().getSelectedIndex()+2;
                    int indexY = choiceBoxY.getSelectionModel().getSelectedIndex()+2;


                    for (String[] car : carList) {
                        series.getData().add(new XYChart.Data(Double.parseDouble(car[indexX]), Double.parseDouble(car[indexY])));
                    }


                    scatterChart.getData().add(series);

                    for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
                        for (XYChart.Data<Number, Number> d : s.getData()) {

                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(new_val + " = " + d.getXValue().toString() + "\n" +
                                    choiceBoxY.getValue().toString() + " = " + d.getYValue().toString());
                            Tooltip.install(d.getNode(), tooltip);

                            //Adding class on hover
                            d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                            //Removing class on exit
                            d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
                        }
                    }

                }
        );

        choiceBoxY.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    XYChart.Series<Number, Number> series = new XYChart.Series<>();
                    scatterChart.getData().clear();
                    scatterChart.getYAxis().setLabel(String.valueOf(choiceBoxY.getSelectionModel().getSelectedItem()));


                    int indexY = choiceBoxY.getSelectionModel().getSelectedIndex() + 2;
                    int indexX = choiceBoxX.getSelectionModel().getSelectedIndex() + 2;

                    for (String[] car : carList) {
                        series.getData().add(new XYChart.Data(Double.parseDouble(car[indexX]), Double.parseDouble(car[indexY])));
                    }


                    scatterChart.getData().add(series);

                    for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
                        for (XYChart.Data<Number, Number> d : s.getData()) {

                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(new_val + " = " + d.getXValue().toString() + "\n" +
                                    choiceBoxX.getValue().toString() + " = " + d.getYValue().toString());
                            Tooltip.install(d.getNode(), tooltip);

                            //Adding class on hover
                            d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                            //Removing class on exit
                            d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
                        }
                    }
                }
        );
    }

    public void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            readFile(file);
        }
    }

    public void readFile(File file) {

        String csvFile = file.getAbsolutePath();
        String line = "";
        String cvsSplitBy = ";";
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                carList[i] = line.split(cvsSplitBy);

                marke.getData().add(new XYChart.Data(String.valueOf(i), carList[i][0]));
                hersteller.getData().add(new XYChart.Data(i, carList[i][1]));
                mpg.getData().add(new XYChart.Data(i, Double.parseDouble(carList[i][2])));
                zylinder.getData().add(new XYChart.Data(i, Integer.parseInt(carList[i][3])));
                displacement.getData().add(new XYChart.Data(i, Double.parseDouble(carList[i][4])));
                ps.getData().add(new XYChart.Data(i, Integer.parseInt(carList[i][5])));
                gewicht.getData().add(new XYChart.Data(i, Integer.parseInt(carList[i][6])));
                beschleunigung.getData().add(new XYChart.Data(i, Double.parseDouble(carList[i][7])));
                jahr.getData().add(new XYChart.Data(i, Integer.parseInt(carList[i][8])));
                herkunft.getData().add(new XYChart.Data(i, carList[i][9]));
                System.out.println("Country [code= " + Arrays.toString(carList[i]));

                //series.getData().add(new XYChart.Data(String.valueOf(i),Integer.parseInt(carList[i][3])));
                series.getData().add(new XYChart.Data(Integer.parseInt(carList[i][3]), Integer.parseInt(carList[i][5])));

                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Defining the axes
        scatterChart.getXAxis().setLabel("Zylinder");
        scatterChart.getYAxis().setLabel("PS");


        scatterChart.getData().add(series);

        for (XYChart.Series<Number, Number> s : scatterChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {

                Tooltip tooltip = new Tooltip();
                tooltip.setText("Zylinder = " + d.getXValue().toString() + "\n PS = " + d.getYValue().toString());
                Tooltip.install(d.getNode(), tooltip);

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    public static <T extends Comparable<T>> T findMax(T[] array){
        T max = array[0];
        for(T data: array){
            if(data.compareTo(max)>0)
                max =data;
        }
        return max;
    }
}
