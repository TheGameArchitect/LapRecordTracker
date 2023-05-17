package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import laprecordtracker.Kierrosaika;
import laprecordtracker.Kilparata;
import laprecordtracker.LapRecordTracker;
import laprecordtracker.Peli;
import laprecordtracker.SailoException;

/**
 * @author Matti Savolainen
 * @version 16.2.2023
 *
 */
public class MuokkaaAikaaGUIController implements ModalControllerInterface<LapRecordTracker>, Initializable {

    @FXML private TextField textAika;
    @FXML private TextField textAjoavut;
    @FXML private TextField textAuto;
    @FXML private TextField textKeli;
    @FXML private ComboBoxChooser<Kilparata> chooserKilparata;
    @FXML private TextArea textKommentit;
    @FXML private TextField textRenkaat;
    @FXML private TextField textSimu;
    @FXML private Label labelVirhe;

    @FXML
    private Button buttonLisaaPeli() {
        uusiPeli();
        return null;
    }
    
    @FXML
    private void buttonCancel() {
        laprecordtracker.setKierrosaikaKohdalla(null);
        ModalController.closeStage(textAika);
    }

    @FXML
    private void buttonTallenna() {
        if (laprecordtracker.getKierrosaikaKohdalla() != null && laprecordtracker.getKierrosaikaKohdalla().getKierrosaika().trim().equals("")) {
            naytaVirhe("Kierrosaika ei saa olla tyhjä");
            return;
        }
        if (chooserKilparata.getSelectionModel().getSelectedItem().getObject() == null) {
            naytaVirhe("Kilparata pitää valita");
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
    public LapRecordTracker getResult() {
        return laprecordtracker;
        //return oletusVastaus;
    }

    @Override
    public void handleShown() {
        textRenkaat.requestFocus();
        //alusta();
    }

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //alusta();
    }
    
    
    @Override
    public void setDefault(LapRecordTracker oletus) {
        laprecordtracker = oletus;
        alusta();
        //kierrosaikaKohdalla = oletus;
        naytaKierrosaika(edits, textKommentit, laprecordtracker.getKierrosaikaKohdalla());
        naytaKommentitJaAuto(laprecordtracker.getKierrosaikaKohdalla());
    }
    
    
    // ====================================================================================
    
    private LapRecordTracker laprecordtracker;
    //private Kierrosaika valittuKierrosaika;
    private TextField[] edits;
    
    
    private void alusta() {
        edits = new TextField[] {textAika, textAjoavut, textKeli, textRenkaat};
        textAuto.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(1, textAuto));
        textRenkaat.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(2, textRenkaat));
        textAjoavut.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(3, textAjoavut));
        textKeli.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(4, textKeli));
        textAika.setOnKeyReleased(e -> kasitteleMuutosKierrosaikaan(5, textAika));
        textKommentit.setOnKeyReleased(e -> kasitteleMuutosKommentteihin(textKommentit));
        
        int rataIndeksi = 1;
        for (int i = 0; i < laprecordtracker.getKilparatoja(); i++) {
            Kilparata rata = laprecordtracker.annaKilparata(rataIndeksi);
            chooserKilparata.add(rata.getKilparata(), rata);
            rataIndeksi++;
        }
        chooserKilparata.addSelectionListener(e -> kasitteleMuutosKilparataan(chooserKilparata));
    }
    
    
    private void uusiPeli() {
        Peli uusi = new Peli();
        uusi.rekisteroi();
        try {
            laprecordtracker.lisaa(uusi, textSimu.getText());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Pelin lisääminen ei onnistunut " + e.getMessage());
        }
    }
    
    
    private void kasitteleMuutosKilparataan(ComboBoxChooser<Kilparata> edit) {
        Kilparata rata = edit.getSelectionModel().getSelectedItem().getObject();
        if (rata == null) return;
        Kierrosaika aika = laprecordtracker.getKierrosaikaKohdalla();
        aika.setRataId(rata.getTunnusNro());
        laprecordtracker.setKierrosaikaKohdalla(aika);
    }
    
    
    /**
     * Käsitellään kierrosaikaan tulleet muutokset
     * @param k kenttä johon sijoitetaan
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosKierrosaikaan(int k, TextField edit) {
        if (laprecordtracker.getKierrosaikaKohdalla() == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = laprecordtracker.getKierrosaikaKohdalla().aseta(k, s);
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            naytaVirhe(virhe);
            edit.getStyleClass().add("normaali");
        } else {
            Dialogs.setToolTipText(edit, virhe);
            naytaVirhe(virhe);
            edit.getStyleClass().add("virhe");
        }
    }
    
    
    private void kasitteleMuutosKommentteihin(TextArea edit) {
        if (laprecordtracker.getKierrosaikaKohdalla() == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = laprecordtracker.getKierrosaikaKohdalla().setKommentit(s);
        if (virhe == null) {
            naytaVirhe(virhe);
        } else {
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Laitetaan kierrosajan tietoja TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param kommentti kierrosajan kommentti
     * @param kierrosaika näytettävä kierrosaika
     */
    public static void naytaKierrosaika(TextField[] edits, TextArea kommentti, Kierrosaika kierrosaika) {
        if (kierrosaika == null) return;
        edits[0].setText(kierrosaika.getKierrosaika());
        edits[1].setText(kierrosaika.getAjoavut());
        //edits[2].setText(kierrosaika.getAuto());
        edits[2].setText(kierrosaika.getKeli());
        //edits[4].setText(kierrosaika.getKommentit());
        edits[3].setText(kierrosaika.getRenkaat());
        kommentti.setText(kierrosaika.getKommentit());
    }
    
    
    private void naytaKommentitJaAuto(Kierrosaika kierrosaika) {
        if (kierrosaika == null) return;
        textKommentit.setText(kierrosaika.getKommentit());
        textAuto.setText(kierrosaika.getAuto());
    }
    
    
    /**
     * TODO: muokkaa kommentit tms. kuntoon
     * Luodaan kierrosajan kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static LapRecordTracker kysyKierrosaika(Stage modalityStage, LapRecordTracker oletus) {
        return ModalController.showModal(MuokkaaAikaaGUIController.class.getResource("MuokkaaAikaa.fxml"),
                                                                    "Kierrosaika", modalityStage, oletus);
    }
}
