package FrontEnd;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.FocusModel;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontEnd/MainSelectPane.fxml")); 
        MainWindowController mainWindowController = new MainWindowController();
        loader.setController(mainWindowController); 
        Pane mainPane = (Pane) loader.load(); 
        Scene scene = new Scene(mainPane); 
        
        primaryStage.setTitle("Sympathetic Tool");
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
}