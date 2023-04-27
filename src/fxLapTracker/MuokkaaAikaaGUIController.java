package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laprecordtracker.Kierrosaika;

/**
 * @author Matruusi
 * @version 16.2.2023
 *
 */
public class MuokkaaAikaaGUIController implements ModalControllerInterface<Kierrosaika>, Initializable {

    @FXML private TextField textAika;
    @FXML private TextField textAjoavut;
    @FXML private TextField textAuto;
    @FXML private TextField textKeli;
    @FXML private ComboBoxChooser<?> chooserKilparata;
    @FXML private TextField textKommentit;
    @FXML private TextField textRenkaat;
    @FXML private TextField textSimu;
    
    //private String oletusVastaus = null;

    @FXML
    private void buttonCancel() {
        ModalController.closeStage(textAika);
    }

    @FXML
    private void buttonTallenna() {
        //oletusVastaus = textAika.getText();
        ModalController.closeStage(textAika);
    }

    @Override
    public Kierrosaika getResult() {
        return null;
        //return oletusVastaus;
    }

    @Override
    public void handleShown() {
        textAika.requestFocus();
    }

    /**
     * @param oletus e
     */
    /**
    public void setDefault(String oletus) {
        textAika.setText(oletus);
    }**/

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    
    @Override
    public void setDefault(Kierrosaika oletus) {
        kierrosaikaKohdalla = oletus;
        naytaKierrosaika(edits, kierrosaikaKohdalla);
    }
    
    
    // ====================================================================================
    
    private Kierrosaika kierrosaikaKohdalla;
    private TextField[] edits;
    
    
    private void alusta() {
        edits = new TextField[] {textAika, textAjoavut, textKeli, textRenkaat};
    }
    
    
    /**
     * Laitetaan kierrosajan tietoja TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param kierrosaika näytettävä kierrosaika
     */
    public static void naytaKierrosaika(TextField[] edits, Kierrosaika kierrosaika) {
        if (kierrosaika == null) return;
        edits[0].setText(kierrosaika.getKierrosaika());
        edits[1].setText(kierrosaika.getAjoavut());
        //edits[2].setText(kierrosaika.getAuto());
        edits[2].setText(kierrosaika.getKeli());
        //edits[4].setText(kierrosaika.getKommentit());
        edits[3].setText(kierrosaika.getRenkaat());
    }
    
    
    /**
     * Luodaan kierrosajan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kierrosaika kysyKierrosaika(Stage modalityStage, Kierrosaika oletus) {
        return ModalController.showModal(MuokkaaAikaaGUIController.class.getResource("MuokkaaAikaa.fxml"),
                                                                    "Kierrosaika", modalityStage, oletus);
    }
}
