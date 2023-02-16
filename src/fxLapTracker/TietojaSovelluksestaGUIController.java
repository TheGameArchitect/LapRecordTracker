package fxLapTracker;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
//import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Matruusi
 * @version 16.2.2023
 *
 */
public class TietojaSovelluksestaGUIController implements ModalControllerInterface<String> {

    @FXML
    private Button buttonOk;
    
    @FXML
    private void buttonOk() {
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
}
