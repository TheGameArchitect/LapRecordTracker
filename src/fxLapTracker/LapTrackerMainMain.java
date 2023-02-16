package fxLapTracker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Matruusi
 * @version 17.2.2023
 * 
 */
public class LapTrackerMainMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LapTrackerMainGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            //final LapTrackerMainGUIController laptrackermainCtrl = (LapTrackerMainGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("laptrackermain.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LapTrackerMain");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}