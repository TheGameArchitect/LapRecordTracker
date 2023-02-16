package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * Luokka pääikkunan käyttöliittymän tapahtuminen käsittelyä varten.
 * @author Matruusi
 * @version 17.2.2023
 *
 */
 public class LapTrackerMainGUIController implements Initializable {
     
    @FXML
    private void buttonLisaaRata() {
        ModalController.showModal(LisaaKilparataGUIController.class.getResource("LisaaKilparata.fxml"), "Kilparata", null, "");
        Dialogs.showMessageDialog("Vielä ei osata lisätä ratoja.");
    }

    @FXML
    private void buttonMuokAika() {
        ModalController.showModal(MuokkaaAikaaGUIController.class.getResource("MuokkaaAikaa.fxml"), "Kierrosaika", null, "");
        Dialogs.showMessageDialog("Vielä ei osata muokata aikoja.");
    }

    @FXML
    private void buttonPoistaAika() {
        ModalController.showModal(PoistaAikaKyselyGUIController.class.getResource("PoistaAikaKysely.fxml"), "", null, "");
    }

    @FXML
    private void buttonUusiAika() {
        ModalController.showModal(UusiAikaGUIController.class.getResource("UusiAika.fxml"), "Kierrosaika", null, "");
        Dialogs.showMessageDialog("Vielä ei osata lisätä aikoja.");
    }

    @FXML
    private ListChooser<?> listAutot;

    @FXML
    private ListChooser<?> listKilparadat;

    @FXML
    private Menu menuApua;

    @FXML
    private Menu menuTiedost;
    
    @FXML
    private void menuSimu1() {
        Dialogs.showMessageDialog("Vielä ei osata vaihtaa simulaattoreita.");
    }

    @FXML
    private void menuSimu2() {
        Dialogs.showMessageDialog("Vielä ei osata vaihtaa simulaattoreita.");
    }
    
    @FXML
    private void menuSimu3() {
        Dialogs.showMessageDialog("Vielä ei osata vaihtaa simulaattoreita.");
    }
    
    @FXML
    private void menuSimu4() {
        Dialogs.showMessageDialog("Vielä ei osata vaihtaa simulaattoreita.");
    }
    
    @FXML
    private void menuSimu5() {
        Dialogs.showMessageDialog("Vielä ei osata vaihtaa simulaattoreita.");
    }
    
    @FXML
    private void menuTallenna() {
        tallenna();
    }
    
    @FXML
    private void menuAvaa() {
        ModalController.showModal(AvaaVirheGUIController.class.getResource("AvaaVirhe.fxml"), "", null, "");
    }
    
    @FXML
    private void menuSulje() {
        tallenna();
        ModalController.showModal(SuljeKyselyGUIController.class.getResource("SuljeKysely.fxml"), "", null, "");
        //Platform.exit();
    }
    
    @FXML
    private void menuTietoja() {
        ModalController.showModal(TietojaSovelluksestaGUIController.class.getResource("TietojaSovelluksesta.fxml"), "", null, "");
    }

    @FXML
    private TextField textAjoavut;

    @FXML
    private TextField textKeli;

    @FXML
    private TextField textKierrosaika;

    @FXML
    private TextField textHaku;

    @FXML
    private TextArea textKommentit;

    @FXML
    private TextField textRenkaat;

    @FXML
    private TextField textSimulaattori;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // 
    }
    
    @FXML
    private Label labelVirhe;
    
    @FXML
    private String virheTeksti = "Haku ei toimi vielä!";
     
    @FXML
    private void textHaku() {
        String hakuTeksti = textHaku.getText();
        if (hakuTeksti.isEmpty()) naytaVirhe(null);
        else naytaVirhe(virheTeksti);
    }

    // ============================================================================
    
    /**
     * Kertoo käyttäjällä, jos annettu hakutermi ei ole oikeanlainen
     * @param virhe Käyttäjän kirjoittama teksti
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennus ei vielä toimi.");
    }
    
    
}