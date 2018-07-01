package FrontEnd;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import DB.ExcelGenerator;
import DB.Participante;
import javafx.application.Application;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import weka.core.pmml.jaxbbindings.TableLocator;

public class SearchController extends Application implements Initializable {

	@FXML private TextField searchInput = new TextField();
    @FXML private TableView<Participante> tableSearch = new TableView<>();
    @FXML private TableColumn<Participante, String> nombreYApellido;
    @FXML private TableColumn<Participante, String> nroId;
    @FXML private ImageView btnClose;
	private Stage pStage;
	private ObservableList<Participante> data = FXCollections.observableArrayList();
	private Stage sStage = new Stage(); 
	private Participante p = null; 
	
	//Base de Datos
	private ExcelGenerator db;

	
	
	public SearchController (Stage primaryStage) {
		pStage = primaryStage;
	}
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//LOAD TABLA INICIAL
		FilteredList<Participante> filteredData = new FilteredList<>(data, e -> true);
		db = new ExcelGenerator();
		db.loadParticipantes();
		nombreYApellido.setCellValueFactory(new PropertyValueFactory<Participante,String>("nombre"));
		nroId.setCellValueFactory(new PropertyValueFactory<Participante,String>("id"));		
		for (Participante p: db.getParticipantes())
			data.add(p);
		tableSearch.setItems(data);
		
		
		//BUSQUEDA MEDIANTE TEXTFIELD NOMBRE O ID
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
		
		
		//ALUMNO SELECCIONADO
		tableSearch.setRowFactory(tv -> {
			TableRow<Participante> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty())) {
					Participante rowData = row.getItem();
					try {
						p = rowData; 
						this.start(sStage);
						pStage.hide();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					System.out.println(rowData.getNombre() + " " + rowData.getId());
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
		
		
		
	}





	@Override
	public void start(Stage secondStage) throws Exception {
		if (p != null) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
		DetailController c = new DetailController(p, pStage, secondStage);
		loader.setController(c);
		Pane mainPane = (Pane) loader.load();
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		secondStage.setScene(scene);
		secondStage.initStyle(StageStyle.UNDECORATED);
		secondStage.setWidth(pStage.getWidth());
		secondStage.setHeight(pStage.getHeight());
		secondStage.show();	
		}
	}	
}
