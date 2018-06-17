package FrontEnd;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class MainWindowController{

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    private ObservableList<String> list = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {

        String[] skills = new String[6];
        
        skills[0] = "Hab 1";
        skills[1] = "Hab 2";
        skills[2] = "Hab 3";
        skills[3] = "Hab 4";
        skills[4] = "Hab 5";
        skills[5] = "Hab 6";

        list.addAll(Arrays.asList(skills));
        System.out.println("hello");

        xAxis.setCategories(list);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        System.out.println("hello");
        
        series.getData().add(new XYChart.Data<>("Hab 1", 100));
        series.getData().add(new XYChart.Data<>("Hab 2", 50));
        series.getData().add(new XYChart.Data<>("Hab 3", 70));
        series.getData().add(new XYChart.Data<>("Hab 4", 20));
        series.getData().add(new XYChart.Data<>("Hab 5", 25));
        series.getData().add(new XYChart.Data<>("Hab 6", 80));
        
        barChart.getData().add(series);
    }

}