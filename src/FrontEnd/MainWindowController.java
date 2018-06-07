package FrontEnd;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class MainWindowController implements Initializable {
	

	@FXML
	private ListView<String> listaCursos;
	@FXML
	private HBox bBoxSuperior;
	
	
	public  MainWindowController() {
		
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaCursos.getItems().addAll("Curso1", "Curso2", "Curso3", "Curso4");
		bBoxSuperior.setAlignment(Pos.CENTER_LEFT);

		
		
	}
}
