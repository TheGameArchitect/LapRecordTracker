package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * @author Matruusi
 * @version 16.2.2023
 *
 */
public class PoistaAikaKyselyGUIController implements ModalControllerInterface<String>, Initializable {

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOk;

    @FXML
    void buttonCancel() {
        ModalController.closeStage(buttonCancel);
    }

    @FXML
    void buttonOk() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa.");
        ModalController.closeStage(buttonOk);
    }
    
    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //
    }

    @Override
    public void setDefault(String oletus) {
        //   
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
}
