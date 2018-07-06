package FrontEnd;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import Clasificador.Clasificador;
import Clasificador.JsonWriter;
import DB.ExcelGenerator;
import DB.LectorArchivo;
import DB.Participante;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import weka.core.pmml.jaxbbindings.TableLocator;

public class SearchController extends Application implements Initializable {

	@FXML private TextField searchInput = new TextField();
    @FXML private TableView<Participante> tableSearch = new TableView<>();
    @FXML private TableColumn<Participante, String> nombreYApellido;
    @FXML private TableColumn<Participante, String> nroId;
    @FXML private ImageView btnClose;
    @FXML private ImageView btnAddData;
    @FXML private ImageView btnJson;
    @FXML private ImageView btnRun;
    @FXML private AnchorPane topWindow;
    
	private Stage pStage;
	private ObservableList<Participante> data = FXCollections.observableArrayList();
	private Stage sStage = new Stage(); 
	private Participante p = null; 
	private boolean styled = false; 
	private double xOffset = 0; 
	private double yOffset = 0;
	private String rutaResultado = null;
	private File dbSeleccionada = null;
	private boolean resultadoListo = false; 
	
	//Base de Datos
	private ExcelGenerator db;
	//Clasificador
	private Clasificador cl;

	
	
	public SearchController (Stage primaryStage) {
		pStage = primaryStage;
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		// TABLA INICIAL
		FilteredList<Participante> filteredData = new FilteredList<>(data, e -> true);
		
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
                pStage.setX(event.getScreenX() - xOffset);
                pStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		
		// BUSQUEDA MEDIANTE TEXTFIELD NOMBRE O ID
		searchInput.setOnKeyReleased(e -> {
			searchInput.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Participante>) alumno ->{
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					String lowerCaseFilter = newValue.toLowerCase();
					if(alumno.getId().contains(newValue)) {
						return true;
					} else if(alumno.getNombre().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					return false;
				});
			});
			SortedList<Participante> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(tableSearch.comparatorProperty());
			tableSearch.setItems(sortedData);
		});
		
		
		// ALUMNO SELECCIONADO
		tableSearch.setRowFactory(tv -> {
			TableRow<Participante> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty())) {
					Participante rowData = row.getItem();
					if (resultadoListo)
						try {
							p = rowData;
							this.start(sStage);
							pStage.hide();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						System.out.println(rowData.getNombre() + "\\R " + rowData.getId());
				}
			});
			return row; 
		});
		
		btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		         pStage.close();		         
		         event.consume();
		     }
		});
		
		// BOTON CARGAR CONSULTA BASE DE DATOS
		btnAddData.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		        FileChooser fileChooser = new FileChooser();
		        fileChooser.setTitle("Seleccionar Archivo Base de Datos");
		        dbSeleccionada = fileChooser.showOpenDialog(pStage);
		         
		 		if (dbSeleccionada != null) {
		 			btnAddData.setImage(new Image("/FrontEnd/images/addDataFull.png"));
					db = new ExcelGenerator();
					db.loadParticipantes(dbSeleccionada.getAbsolutePath());
					nombreYApellido.setCellValueFactory(new PropertyValueFactory<Participante,String>("nombre"));
					nroId.setCellValueFactory(new PropertyValueFactory<Participante,String>("id"));		
					for (Participante p: db.getParticipantes())
						data.add(p);
					tableSearch.setItems(data);
		 		} 
		    	 event.consume();
		     }
		});
		
		// BOTON CARGAR RUTA DESTINO RESULTADOS
		btnJson.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	DirectoryChooser chooser = new DirectoryChooser();
		    	chooser.setTitle("Seleccionar Ruta Destino Resultados");
		    	File ruta = chooser.showDialog(pStage); 
		        if (ruta != null) {
		        	rutaResultado = ruta.getAbsolutePath();
		        	btnJson.setImage(new Image("/FrontEnd/images/jsonFull.png"));	 
		        }
		        event.consume();
		     }
		});
		
		
		// BOTON CORRER ANALISIS COLABORACION
		btnRun.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	if (rutaResultado != null || dbSeleccionada != null) {
		    		try {
						cl = new Clasificador();
						ArrayList<Participante> participantes = LectorArchivo.obtenerDatosParticipantes(dbSeleccionada.getAbsolutePath());
						cl.clasificar(participantes);
				    	if((rutaResultado.charAt(rutaResultado.length()-1)!='/')||(rutaResultado.charAt(rutaResultado.length()-1)!='\\'))
				    		rutaResultado +="/";
				    	JsonWriter.setDir(rutaResultado);
				    	JsonWriter.dataToJSON(participantes);
				    	JsonWriter.resultToJson(cl.getResultados());
				    	btnRun.setImage(new Image("/FrontEnd/images/runFull.png"));
				    	resultadoListo = true; 
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		
		    	}
		        event.consume();
		     }
		});
		
	}





	@Override
	public void start(Stage secondStage) throws Exception {
		if (p != null) {
		System.out.println("Paso por el out de second");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
		DetailController c = new DetailController(p, pStage, secondStage, rutaResultado);
		loader.setController(c);
		Pane mainPane = (Pane) loader.load();
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		secondStage.setScene(scene);
   	 	secondStage.setX(pStage.getX());
   	 	secondStage.setY(pStage.getY());
		if (!styled) {
			secondStage.initStyle(StageStyle.UNDECORATED);
			styled = true; 
		}
		secondStage.setWidth(pStage.getWidth());
		secondStage.setHeight(pStage.getHeight());
		secondStage.show();	
		}
	}	
}
