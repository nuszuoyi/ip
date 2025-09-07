package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Amadeus;  // Import from main package instead of Amadeus11

/**
 * A GUI for Amadeus using FXML.
 */
public class Main extends Application {

    private Amadeus amadeus = new Amadeus();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Amadeus");  // Add window title
            stage.setMinHeight(220);    // Set minimum height
            stage.setMinWidth(417);     // Set minimum width
            
            fxmlLoader.<MainWindow>getController().setAmadeus(amadeus);  // inject the Amadeus instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Main entry point for the JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}