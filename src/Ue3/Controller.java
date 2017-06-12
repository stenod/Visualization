package Ue3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by saubaer on 31.05.17.
 * PackageName: Ue3
 * ProjektName: Visualization_Jung
 */
public class Controller {

    public Polygon polygonRight;
    public Polygon polygonLeft;
    public Label mpgLabel;
    public Label GewichtLabel;
    public Label PSLabel;
    public Label JahrLabel;
    public Label ZylinderLabel;
    public Label HubraumLabel;
    public Label BeschleunigungLabel;
    public ListView CarListLeft;
    public Label topicNameLeft;
    public ListView CarListRight;
    public Label topicNameRight;
    public ComboBox originChoiceBoxLeft;
    public ComboBox originChoiceBoxRight;
    public RadioButton MeanWertLeft;
    public RadioButton ModalWertLeft;
    public RadioButton MeanWertRight;
    public RadioButton ModalWertRight;

    final ToggleGroup groupRight = new ToggleGroup();
    final ToggleGroup groupLeft = new ToggleGroup();


    private Double zylinder = 0.0;
    private Double hubraum = 0.0;
    private Double ps = 0.0;
    private Double gewicht = 0.0;
    private Double beschleunigung = 0.0;
    private Double mpg = 0.0;
    private Double jahr = 0.0;

    DecimalFormat df = new DecimalFormat("#.##");

    private String[][] carList = new String[407][11];

    public void initialize() {
        MeanWertLeft.setToggleGroup(groupLeft);
        ModalWertLeft.setToggleGroup(groupLeft);

        MeanWertRight.setToggleGroup(groupRight);
        ModalWertRight.setToggleGroup(groupRight);

        MeanWertLeft.setSelected(true);
        MeanWertRight.setSelected(true);

        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> origins = FXCollections.observableArrayList();

        String csvFile = "/Users/saubaer/Documents/Visualization_Jung/data/cars.csv";
        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                carList[i] = line.split(cvsSplitBy);

                items.add(i, carList[i][0]);
                System.out.println("Country [code= " + Arrays.toString(carList[i]));
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        CarListRight.setItems(items);
        CarListLeft.setItems(items);

        CarListRight.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    topicNameRight.setText((String) new_val);

                    Double[] data = filterCars(carList, "name", (String) new_val, groupRight.getSelectedToggle());

                    showPolygon(data, Color.color(1.0, 0.3, 0.8, 0.5), polygonRight);

                });


        CarListLeft.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    topicNameLeft.setText((String) new_val);

                    Double[] data = filterCars(carList, "name", (String) new_val, groupLeft.getSelectedToggle());

                    showPolygon(data, Color.color(0.4549, 0.5804, 1, 0.502), polygonLeft);

                });

        origins.addAll("European", "American", "Japanese", "NA");
        originChoiceBoxLeft.setItems(origins);
        originChoiceBoxRight.setItems(origins);


        /*PolarItem.setFill(Color.ALICEBLUE);
        PolarItem.setStroke(Color.BLUE);
        PolarItem.setContent("M0, 0 L 100, 100 L"
                + size + "," + size + " " + size / 2 + "," + 2 * size / 3 + "z");*/
    }

    public void showCarsByOriginRight() {

        Object origin = originChoiceBoxRight.getSelectionModel().getSelectedItem();

        Double[] data = filterCars(carList, "origin", (String) origin, groupRight.getSelectedToggle());

        showPolygon(data, Color.color(1.0, 0.3, 0.8, 0.5), polygonRight);
    }


    public void showCarsByOriginLeft() {

        Object origin = originChoiceBoxLeft.getSelectionModel().getSelectedItem();

        Double[] data = filterCars(carList, "origin", (String) origin, groupLeft.getSelectedToggle());

        showPolygon(data, Color.color(0.4549, 0.5804, 1, 0.502), polygonLeft);
    }


    private void showPolygon(Double[] data, Color color, Polygon polygon) {

        polygon.getPoints().clear();
        polygon.setVisible(true);
        polygon.setFill(color);
        polygon.getPoints().addAll(
                0.0, data[0],
                data[1], data[1],
                data[2], 0.0,
                data[3], -data[3],
                0.0, -data[4],
                -data[5], -data[5],
                -data[6], 0.0,
                -50.0, 50.0,
                0.0, data[0]);
    }

    private Double[] filterCars(String[][] carList, String filterBy, String filterValue, Toggle filterType) {

        Double[] data = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        int count = 0;

        switch (filterBy) {
            case "origin":
                for (String[] car : carList) {
                    if (filterValue.equals(car[9])) {

                        if (Objects.equals(car[5], "NA")) {
                            car[5] = String.valueOf(0);
                        }
                        if (Objects.equals(car[2], "NA")) {
                            car[2] = String.valueOf(0);
                        }

                        data[0] += Double.valueOf(car[3]) * 12.5;
                        data[1] += Double.valueOf(car[4]) / 6.07;
                        data[2] += Double.valueOf(car[5]) / 2.3;
                        data[3] += Double.valueOf(car[6]) / 68.54;
                        data[4] += Double.valueOf(car[7]) * 4.03;
                        data[5] += Double.valueOf(car[2]) * 2.14;
                        data[6] += Double.valueOf(car[8]) * 1.21;
                        count++;
                    }
                }

                if (filterType.getToggleGroup().getSelectedToggle() == MeanWertRight || filterType.getToggleGroup().getSelectedToggle() == MeanWertLeft) {
                    for (int i = 0; i < data.length; i++) {
                        data[i] = Double.valueOf((String.valueOf((data[i] / count)).substring(0,4)));
                    }
                }
                else {
                    for (int i = 0; i < data.length; i++) {
                        data[i] = mode(data);
                    }
                }

                ZylinderLabel.setText("Zylinder: " + data[0]);
                HubraumLabel.setText("Hubraum: " + data[1]);
                PSLabel.setText("PS: " + data[2]);
                GewichtLabel.setText("Gewicht: " + data[3]);
                BeschleunigungLabel.setText("Beschleunigung: " + data[4]);
                mpgLabel.setText("mpg: " + data[5]);
                JahrLabel.setText("Jahr:" + data[6]);
                break;
            case "name":
                for (String[] car : carList) {
                    if (Objects.equals(filterValue, car[0])) {

                        ZylinderLabel.setText("Zylinder: " + car[3]);
                        HubraumLabel.setText("Hubraum: " + car[4]);
                        PSLabel.setText("PS: " + car[5]);
                        GewichtLabel.setText("Gewicht: " + car[6]);
                        BeschleunigungLabel.setText("Beschleunigung: " + car[7]);
                        mpgLabel.setText("mpg: " + car[2]);
                        JahrLabel.setText("Jahr:" + car[8]);


                        if (Objects.equals(car[5], "NA")) {
                            car[5] = String.valueOf(0);
                        }
                        if (Objects.equals(car[2], "NA")) {
                            car[2] = String.valueOf(0);
                        }

                        data[0] = Double.valueOf(car[3]) * 12.5;
                        data[1] = Double.valueOf(car[4]) / 6.07;
                        data[2] = Double.valueOf(car[5]) / 2.3;
                        data[3] = Double.valueOf(car[6]) / 68.54;
                        data[4] = Double.valueOf(car[7]) * 4.03;
                        data[5] = Double.valueOf(car[2]) * 2.14;
                        data[6] = Double.valueOf(car[8]) * 1.21;
                    }
                }

        }
        return data;
    }

    private static double mode(Double[] array) {
        double mode = array[0];
        int maxCount = 0;
        for (Double value : array) {
            int count = 1;
            for (Double anArray : array) {
                if (Objects.equals(anArray, value)) count++;
                if (count > maxCount) {
                    mode = value;
                    maxCount = count;
                }
            }
        }
        return mode;
    }
}
