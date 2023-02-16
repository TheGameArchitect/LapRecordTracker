package fxLapTracker;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Matruusi
 * @version 16.2.2023
 *
 */
public class PoistaAikaKyselyGUIController implements ModalControllerInterface<String> {

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
}
