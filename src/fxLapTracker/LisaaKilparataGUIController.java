package fxLapTracker;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Matruusi
 * @version 16.2.2023
 *
 */
public class LisaaKilparataGUIController implements ModalControllerInterface<String> {

    @FXML
    private Button buttonPeruuta;

    @FXML
    private Button buttonTallenna;

    @FXML
    private TextField textKilparata;
    
    private String oletusVastaus = null;

    @FXML
    void buttonPeruuta() {
        ModalController.closeStage(textKilparata);
    }

    @FXML
    void buttonTallenna() {
        oletusVastaus = textKilparata.getText();
        ModalController.closeStage(textKilparata);
    }
    
    @Override
    public String getResult() {
        return oletusVastaus;
    }

    @Override
    public void handleShown() {
        textKilparata.requestFocus();
    }

    @Override
    public void setDefault(String oletus) {
        textKilparata.setText(oletus);   
    }
}
