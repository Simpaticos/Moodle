package FrontEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
    @FXML	private ListView<String> listCursos;
    private ObservableList<String> list = FXCollections.observableArrayList();
    private Participante alumno; 
	private double xOffset = 0; 
	private double yOffset = 0;
	private String rutaResultado; 
    
    
    public  DetailController(Participante p, Stage pStage, Stage sStage, String rutaResultado) {
    	this.sStage = sStage;
    	this.pStage = pStage; 
    	alumno = p;
    	this.rutaResultado = rutaResultado.substring(0, rutaResultado.length()-1); 
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//SET NOBRRE ALUMNO TOPWINDOW
    	nombreAlumno.setText(alumno.getNombre());
    	
    	// SET GRAFICO DE BARRAS
        String[] skills = new String[8];
        skills[0] = "Mediacion";
        skills[1] = "Requerir";
        skills[2] = "Informar";
        skills[3] = "Argumentacion";
        skills[4] = "Reconocimiento";
        skills[5] = "Tarea";
        skills[6] = "Motivar";
        skills[7] = "Mantenimiento";
        list.addAll(Arrays.asList(skills));
        xAxis.setCategories(list);
        xAxis.tickLabelFontProperty().set(Font.font(10));
        barChart.setLegendVisible(false);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();        
        // AGREGAR DATOS GRAFICO DE BARRAS 
    	File jsonInputFile = new File(rutaResultado + "\\SubHabilities.json");
    	InputStream is;
        try {
            is = new FileInputStream(jsonInputFile);
            // Create JsonReader from Json.
            JsonReader reader = Json.createReader(is);
            // Get the JsonObject structure from JsonReader.
            JsonArray alumnoArray = reader.readArray();
            reader.close();
            System.out.println("INICIO IMPRESION");
            for (int i = 0; i < alumnoArray.size(); i++) {
                JsonObject jsonobject = alumnoArray.getJsonObject(i);
                String name = jsonobject.getString("name");
                if (name.equals(alumno.getNombre())) {
                    series.getData().add(new XYChart.Data<>("Mediacion", jsonobject.getInt("Mediacion")));
                    series.getData().add(new XYChart.Data<>("Requerir", jsonobject.getInt("Requerir")));
                    series.getData().add(new XYChart.Data<>("Informar", jsonobject.getInt("Informar")));
                    series.getData().add(new XYChart.Data<>("Argumentacion", jsonobject.getInt("Argumentacion")));
                    series.getData().add(new XYChart.Data<>("Reconocimiento", jsonobject.getInt("Reconocimiento")));
                    series.getData().add(new XYChart.Data<>("Tarea", jsonobject.getInt("Tarea")));
                    series.getData().add(new XYChart.Data<>("Motivar", jsonobject.getInt("Motivar")));
                    series.getData().add(new XYChart.Data<>("Mantenimiento", jsonobject.getInt("Mantenimiento")));
                    barChart.getData().add(series);
                }    
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        
        
        //LISTA DE CURSOS 
    	ObservableList<String> cursos = FXCollections.observableArrayList(alumno.getCurso()); //Posteriormente con for addAll
    	listCursos.setItems(cursos);
    	
    	
    	
    	
    	
    	
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
		
		// BOTON CLOSE 
		btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		         sStage.close();		         
		         event.consume();
		     }
		});
	}

}
