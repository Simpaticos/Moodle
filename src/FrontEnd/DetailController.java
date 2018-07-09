package FrontEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.LineNumberInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import Clasificador.Clasificador;
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
    @FXML   private Text txtConsejo;
    private ObservableList<String> list = FXCollections.observableArrayList();
    private Participante alumno; 
	private double xOffset = 0; 
	private double yOffset = 0;
	private String rutaResultado; 
	private Clasificador cl;
	private int conflictoActual = 0;
	private List<String> listaConflictos = new ArrayList<>();
    
    
    public  DetailController(Participante p, Stage pStage, Stage sStage, String rutaResultado, Clasificador cl) {
    	this.sStage = sStage;
    	this.pStage = pStage; 
    	alumno = p;
    	this.rutaResultado = rutaResultado.substring(0, rutaResultado.length()-1); 
    	this.cl = cl; 
    }
    
    
    private String getConflicto(int numeroConflictos) {
		String r = null;
		if (numeroConflictos != 0 && (conflictoActual < numeroConflictos)) {
		    	r = listaConflictos.get(conflictoActual);
		    	conflictoActual = conflictoActual + 1; 
		 } 
		else if (numeroConflictos != 0 && conflictoActual == numeroConflictos) {
			 	conflictoActual = 0; 
			 	r = listaConflictos.get(0);
		 } 
		return r;		
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
        
        // GET CONFLICTOS  
        System.out.println("LISTA DE CONFLICTOS");
        listaConflictos = cl.getConflictoPorUsuario(alumno.getNombre(), rutaResultado + "\\Result.json");
        int numeroConflictos = listaConflictos.size(); 
        if (numeroConflictos != 0)
        	txtConsejo.setText("Consejo sobre: " + getConflicto(numeroConflictos));
        
        // SET CONFLICTOS
		txtConsejo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 if (numeroConflictos != 0)
		    		 txtConsejo.setText("Consejo sobre: " + getConflicto(numeroConflictos));

		         event.consume();
		     }
		});	
        
        
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
