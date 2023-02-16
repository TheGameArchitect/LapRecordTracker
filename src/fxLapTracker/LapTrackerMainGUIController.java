package fxLapTracker;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.SplitMenuButton;

/**
 * Luokka pääikkunan käyttöliittymän tapahtuminen käsittelyä varten.
 * @author Matruusi
 * @version 1.2.2023
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
    private SplitMenuButton menuSimuvalinta;

    @FXML
    private Menu menuTiedost;
    
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
    private TextField textKilparata;

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

    // ============================================================================
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennus ei vielä toimi.");
    }
    
    
}