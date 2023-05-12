package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laprecordtracker.Kierrosaika;
import laprecordtracker.Kilparata;

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
    @FXML private static ComboBoxChooser<Kilparata> chooserKilparata;
    @FXML private TextField textKommentit;
    @FXML private TextField textRenkaat;
    @FXML private TextField textSimu;
    @FXML private Label labelVirhe;
    
    //private String oletusVastaus = null;

    @FXML
    private void buttonCancel() {
        kierrosaikaKohdalla = null;
        ModalController.closeStage(textAika);
    }

    @FXML
    private void buttonTallenna() {
        if (kierrosaikaKohdalla != null && kierrosaikaKohdalla.getKierrosaika().trim().equals("")) {
            naytaVirhe("Kierrosaika ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(textAika);
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
        naytaKommentitJaAuto(kierrosaikaKohdalla);
    }
    
    
    // ====================================================================================
    
    private Kierrosaika kierrosaikaKohdalla;
    private TextField[] edits;
    
    
    private void alusta() {
        edits = new TextField[] {textAika, textAjoavut, textKeli, textRenkaat};
        textAika.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(textAika));
    }
    
    
    private void kasitteleMuutosKierrosaikaan(TextField edit) {
        if (kierrosaikaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = kierrosaikaKohdalla.setKierrosaika(s);
        if (virhe == null) {
            naytaVirhe(virhe);
        } else {
            naytaVirhe(virhe);
        }
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
     * Laitetaan kilparadan tieto oikeaan komponenttiin
     * @param kilparata näytettävä kilparata
     */
    public static void naytaKilparata(Kilparata kilparata) {
        if (kilparata == null) return;
        String rata = kilparata.toString();
        chooserKilparata.setRivit(rata);
    }
    
    
    private void naytaKommentitJaAuto(Kierrosaika kierrosaika) {
        if (kierrosaika == null) return;
        textKommentit.setText(kierrosaika.getKommentit());
        textAuto.setText(kierrosaika.getAuto());
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
