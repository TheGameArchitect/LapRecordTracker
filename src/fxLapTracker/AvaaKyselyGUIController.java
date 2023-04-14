package fxLapTracker;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Matti Savolainen
 * @version 14.4.2023
 *
 */
public class AvaaKyselyGUIController implements ModalControllerInterface<String> {

    @FXML private TextField textVastaus;
    private String vastaus = null;
    
    @FXML
    private Button buttonAvaa;

    @FXML
    private Button buttonPeruuta;
    

    @FXML
    private void buttonAvaa() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }

    @FXML
    private void buttonPeruuta() {
        ModalController.closeStage(textVastaus);
    }

    
    @Override
    public String getResult() {
        return vastaus;
    }

    
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }

    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }
    
    // =============================================================================
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä käytetään oletuksena
     * @return null jos painetaan Peruuta, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AvaaKyselyGUIController.class.getResource("AvaaKysely.fxml"),
                "LapRecordTracker",
                modalityStage, oletus);
    }

}
