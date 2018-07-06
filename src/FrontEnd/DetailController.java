package FrontEnd;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import DB.Participante;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DetailController implements Initializable{

    @FXML	private BarChart<String, Integer> barChart;
    @FXML	private CategoryAxis xAxis;
    @FXML	private Text nombreAlumno = new Text();
    @FXML	private ImageView btnBack;
    @FXML	private Stage pStage; 
    @FXML	private Stage sStage; 
    @FXML	private ImageView btnClose;
    @FXML   private AnchorPane topWindow;
    private ObservableList<String> list = FXCollections.observableArrayList();
    private Participante alumno; 
	private double xOffset = 0; 
	private double yOffset = 0;
    
    
    public  DetailController(Participante p, Stage pStage, Stage sStage) {
    	this.sStage = sStage;
    	this.pStage = pStage; 
    	alumno = p;
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	nombreAlumno.setText(alumno.getNombre());
    	
    	
		// MOVIMIENTO DE VENTANA DESDE TOP WINDOWS
		topWindow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
		
		topWindow.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sStage.setX(event.getScreenX() - xOffset);
                sStage.setY(event.getScreenY() - yOffset);
            }
        });
    	
    	
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
        barChart.setLegendVisible(false);
        

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        System.out.println("hello");
        
        series.getData().add(new XYChart.Data<>("Hab 1", 100));
        series.getData().add(new XYChart.Data<>("Hab 2", 50));
        series.getData().add(new XYChart.Data<>("Hab 3", 70));
        series.getData().add(new XYChart.Data<>("Hab 4", 20));
        series.getData().add(new XYChart.Data<>("Hab 5", 25));
        series.getData().add(new XYChart.Data<>("Hab 6", 80));
        
        barChart.getData().add(series);
        
        
        // BOTON BACK 
		btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 pStage.setX(event.getScreenX() - xOffset);
		    	 pStage.setY(event.getScreenY() - yOffset);
		    	 pStage.show();
		         sStage.hide();		         
		         event.consume();
		     }
		});
		
		
		btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		         sStage.close();		         
		         event.consume();
		     }
		});
	}

}
