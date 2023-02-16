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
public class UusiAikaGUIController implements ModalControllerInterface<String> {

    @FXML
    private TextField textAika;

    @FXML
    private TextField textAjoavut;

    @FXML
    private TextField textAuto;

    @FXML
    private TextField textKeli;

    @FXML
    private TextField textKilparata;

    @FXML
    private TextField textKommentit;

    @FXML
    private TextField textRenkaat;

    @FXML
    private TextField textSimu;
    
    private String oletusVastaus = null;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOk;

    @FXML
    void buttonCancel() {
        ModalController.closeStage(textAika);
    }

    @FXML
    void buttonOk() {
        oletusVastaus = textAika.getText();
        ModalController.closeStage(textAika);
    }

    @Override
    public String getResult() {
        return oletusVastaus;
    }

    @Override
    public void handleShown() {
        textAika.requestFocus();
    }

    @Override
    public void setDefault(String oletus) {
        textAika.setText(oletus);   
    }
    

}
