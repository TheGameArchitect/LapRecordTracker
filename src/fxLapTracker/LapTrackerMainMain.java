package fxLapTracker;

import javafx.application.Application;
import javafx.stage.Stage;
import laprecordtracker.LapRecordTracker;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Matti Savolainen
 * @version 17.2.2023
 * 
 */
public class LapTrackerMainMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LapTrackerMainGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final LapTrackerMainGUIController laptrackermainCtrl = (LapTrackerMainGUIController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("laptrackermain.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LapRecordTracker");
            
            
            primaryStage.setOnCloseRequest((event) -> {
                if (!laptrackermainCtrl.voikoSulkea()) event.consume();
            });
            
            LapRecordTracker laprecordtracker = new LapRecordTracker();
            laptrackermainCtrl.setLapRecordTracker(laprecordtracker);
            
            
            primaryStage.show();
            // if (!laptrackermainCtrl.avaa()) Platform.exit();
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