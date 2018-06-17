package FrontEnd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class MainWindow extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			System.out.println("hola");
			Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/Detail.fxml"));
			Scene scene = new Scene(root,1024,768);
			System.out.println("hola");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			FXMLLoader loader = new FXMLLoader();
			System.out.println("hola");
	        loader.setLocation(getClass().getResource("/FrontEnd/Detail.fxml"));
			MainWindowController controller = loader.getController();
	        System.out.println(controller);
			System.out.println("hola");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

