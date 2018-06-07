package FrontEnd;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainWindowController implements Initializable {
	
	@FXML
	private BarChart upperBarChart; 
	@FXML
	private ListView<String> listaCursos;
	
	
	public  MainWindowController() {
		
	}
	
	public void btnInicio() {
		System.out.println("BOTON INICIO");
	}


	
	public void setBarChart() {
		upperBarChart.setVerticalGridLinesVisible(false);
		upperBarChart.setVerticalZeroLineVisible(false);

		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setBarChart();
		listaCursos.getItems().addAll("Curso1", "Curso2", "Curso3", "Curso4");

		
		
	}
}
