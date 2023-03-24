package fxLapTracker;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import laprecordtracker.Kierrosaika;
import laprecordtracker.Kilparata;
import laprecordtracker.LapRecordTracker;
import laprecordtracker.SailoException;
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
        //ModalController.showModal(LisaaKilparataGUIController.class.getResource("LisaaKilparata.fxml"), "Kilparata", null, "");
        //Dialogs.showMessageDialog("Vielä ei osata lisätä ratoja.");
        uusiKilparata();
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
        //ModalController.showModal(UusiAikaGUIController.class.getResource("UusiAika.fxml"), "Kierrosaika", null, "");
        //Dialogs.showMessageDialog("Vielä ei osata lisätä aikoja.");
        uusiAika();
    }

    @FXML
    private ListChooser<?> listAutot;

    @FXML
    private ListChooser<Kierrosaika> listKilparadat;
    
    @FXML
    private GridPane panelKierrosaika;

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
        alusta();
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
    
    private LapRecordTracker laprecordtracker;
    
    
    private void alusta() {
        textKommentit.setFont(new Font("Courier New", 12));
        
        listKilparadat.clear();
        listKilparadat.addSelectionListener(e -> naytaKierrosaika());
    }
    
    
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
    
    
    /**
     * Tarkistetaan, että onko tallennus tehty
     * @return true jos sovelluksen saa sulkea, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * Haetaan kierrosajat uudelleen
     * @param jnro mikä kierrosaika valitaan aktiiviseksi
     */
    private void hae(int jnro) {
        listKilparadat.clear();
        
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
            Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
            if (kierrosaika.getTunnusNro() == jnro) index = i;
            listKilparadat.add("" + kierrosaika.getKierrosaika(), kierrosaika);
        }
        listKilparadat.setSelectedIndex(index);
    }
    
    
    private void naytaKierrosaika() {
        Kierrosaika kierrosaikaKohdalla = listKilparadat.getSelectedObject();
        
        if (kierrosaikaKohdalla == null) return;
        
        textKommentit.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(textKommentit)) {
            tulosta(os, kierrosaikaKohdalla);
        }
    }

    /**
     * Tulostaa kierrosajan tiedot
     * @param os tietovirta johon tulostetaan
     * @param kierrosaika tulostettava kierrosaika
     */
    private void tulosta(PrintStream os, final Kierrosaika kierrosaika) {
        os.println("---------------------------------------------");
        kierrosaika.tulosta(os);
        os.println("---------------------------------------------");
        List<Kilparata> kilparadat = laprecordtracker.annaKilparadat(kierrosaika);
        for (Kilparata kil : kilparadat)
            kil.tulosta(os);
        os.println("---------------------------------------------");
    }
    
    
    /**
     * Asetetaan käytettävä laprecordtracker-olio
     * @param laprecordtracker jota käytetään
     */
    public void setLapRecordTracker(LapRecordTracker laprecordtracker) {
        this.laprecordtracker = laprecordtracker;
    }
    
    
    /**
     * Tekee uuden tyhjän kilparadan editointia varten
     */
    public void uusiKilparata() {
        Kierrosaika kierrosKohdalla = listKilparadat.getSelectedObject();
        if (kierrosKohdalla == null) return;
        Kilparata kil = new Kilparata();
        kil.rekisteroi();
        kil.taytaKilparataTiedot(kierrosKohdalla.getTunnusNro()); // TODO: korvaa dialogilla
        laprecordtracker.lisaa(kil);
        hae(kierrosKohdalla.getTunnusNro());
    }
    
    
    private void uusiAika() {
        Kierrosaika uusi = new Kierrosaika();
        uusi.rekisteroi();
        uusi.taytaKierrosajanTiedot();
        try {
            laprecordtracker.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        hae(uusi.getTunnusNro());
    }
}