package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laprecordtracker.Kilparata;

/**
 * @author Matti Savolainen
 * @version 16.2.2023
 */
public class LisaaKilparataGUIController implements ModalControllerInterface<Kilparata>, Initializable {

    @FXML
    private Button buttonPeruuta;

    @FXML
    private Button buttonTallenna;

    @FXML
    private TextField textKilparata;
    
    @FXML
    private Label labelVirhe;
    

    @FXML
    void buttonPeruuta() {
        oletusRata = null;
        ModalController.closeStage(textKilparata);
    }

    @FXML
    void buttonTallenna() {
        if (textKilparata.getText() != null && textKilparata.getText().trim().equals("")) {
            naytaVirhe("Tekstikenttä ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(textKilparata);
    }
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    @Override
    public Kilparata getResult() {
        return oletusRata;
    }

    @Override
    public void handleShown() {
        textKilparata.requestFocus();
    }

    /**
     * @param oletus ikkunan tekstikentän oletusteksti avatessa
     */
    public void setDefault(String oletus) {
        textKilparata.setText(oletus);   
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public void setDefault(Kilparata oletus) {
        oletusRata = oletus;
        textKilparata.clear();
    }


    // ================================================================
    
    // private LapRecordTracker laprecordtracker;
    private Kilparata oletusRata;
    
    private void alusta() {
        textKilparata.setOnKeyReleased(e -> kasitteleMuutosKilparataan(textKilparata));
    }
    
    
    private void kasitteleMuutosKilparataan(TextField textRata) {
        String s = textRata.getText();
        String virhe = null;
        virhe = oletusRata.setKilparata(s);
        if (virhe == null) {
            naytaVirhe(virhe);
        } else {
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param uusi mikä rata näytetään oletuksena, tässä irrelevanttia
     * @return null, jos painetaan cancel, muuten kilparadan nimi
     */
    public static Kilparata kysyRata(Stage modalityStage, Kilparata uusi) {
        return ModalController.showModal(LisaaKilparataGUIController.class.getResource("LisaaKilparata.fxml"),
                                "LapRecordTracker",
                                modalityStage, uusi);
    }
}
