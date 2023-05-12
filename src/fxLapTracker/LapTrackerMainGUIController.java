package fxLapTracker;

//import java.io.PrintStream;
import java.net.URL;
//import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import laprecordtracker.Kierrosaika;
import laprecordtracker.Kilparata;
import laprecordtracker.LapRecordTracker;
import laprecordtracker.Peli;
import laprecordtracker.SailoException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * Luokka pääikkunan käyttöliittymän tapahtuminen käsittelyä varten.
 * @author Matti Savolainen
 * @version 17.2.2023
 *
 */
public class LapTrackerMainGUIController implements Initializable {
    
    @FXML
    private void buttonLisaaRata() {
        //ModalController.showModal(LisaaKilparataGUIController.class.getResource("LisaaKilparata.fxml"), "Kilparata", null, "");
        uusiKilparata();
    }

    @FXML
    private void buttonMuokAika() {
        //ModalController.showModal(MuokkaaAikaaGUIController.class.getResource("MuokkaaAikaa.fxml"), "Kierrosaika", null, "");
        muokkaa();
    }

    @FXML
    private void buttonPoistaAika() {
        ModalController.showModal(PoistaAikaKyselyGUIController.class.getResource("PoistaAikaKysely.fxml"), "", null, "");
    }

    @FXML
    private void buttonUusiAika() {
        //ModalController.showModal(UusiAikaGUIController.class.getResource("UusiAika.fxml"), "Kierrosaika", null, "");
        uusiAika();
        uusiPeli();
    }
    
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
        avaa();
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
    
    @FXML
    private ListChooser<Kierrosaika> listAutot;
    @FXML
    private ListChooser<Kilparata> listKilparadat;
    @FXML
    private GridPane panelKierrosaika;
    @FXML
    private Menu menuApua;
    @FXML
    private Menu menuTiedost;
    

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
    private String laprecordtrackerNimi = "kierrosajat";
    private TextField[] edits;
    
    
    private void alusta() {
        textKommentit.setFont(new Font("Courier New", 12));
        
        listKilparadat.clear();
        listKilparadat.addSelectionListener(e -> naytaKilparata());
        listAutot.clear();
        listAutot.addSelectionListener(e -> naytaKierrosaika());
        
        edits = new TextField[] {textKierrosaika, textAjoavut, textKeli, textRenkaat};
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(textHaku).setTitle(title);
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
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = AvaaKyselyGUIController.kysyNimi(null, laprecordtrackerNimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Alustaa laprecordtrackerin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kierrosaikojen tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        laprecordtrackerNimi = nimi;
        setTitle("LapRecordTracker - " + laprecordtrackerNimi);
        
        try {
            laprecordtracker.lueTiedostosta(nimi);
            haeKierrosaikaListaan(0);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        try {
            laprecordtracker.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
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
     * Haetaan kilparadat oikeaan listaan
     * @param jnro mikä kilparata valitaan aktiiviseksi
     */
    private void haeRataListaan(int jnro) {
        listKilparadat.clear();
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKilparatoja(); i++) {
            Kilparata kilparata = laprecordtracker.annaKilparata(jnro);
            listKilparadat.add("" + laprecordtracker.annaKilparataNimi(kilparata.getTunnusNro()), kilparata);
            if (kilparata.getTunnusNro() == jnro) index = i;
        }
        listKilparadat.setSelectedIndex(index);
    }
    
    
    /**
     * Haetaan kierrosajat oikeaan listaan
     * @param jnro mikä kierrosaika valitaan aktiiviseksi
     */
    private void haeKierrosaikaListaan(int jnro) {
        listAutot.clear();
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
            Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
            listAutot.add("" + kierrosaika.getAuto(), kierrosaika);
            if (kierrosaika.getTunnusNro() == jnro) index = i;
        }
        listAutot.setSelectedIndex(index);
    }
    
    
    private void naytaKilparata() {
        Kierrosaika aikaKohdalla = listAutot.getSelectedObject();
        //naytaSimulaattori(aikaKohdalla);
        if (aikaKohdalla == null) return;
    }
    
    
    private void naytaKierrosaika() {
        Kierrosaika kierrosaikaKohdalla = listAutot.getSelectedObject();
        if (kierrosaikaKohdalla == null) return;
        
        MuokkaaAikaaGUIController.naytaKierrosaika(edits, textKommentit, kierrosaikaKohdalla);
        naytaSimulaattori(kierrosaikaKohdalla);
        /**
        listAutot.clear();
        listAutot.addSelectionListener(e -> kierrosaikaKohdalla.getAuto());
        textKierrosaika.setText(kierrosaikaKohdalla.getKierrosaika());
        textAjoavut.setText(kierrosaikaKohdalla.getAjoavut());
        textKeli.setText(kierrosaikaKohdalla.getKeli());
        **/
        
        /**
        textKommentit.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(textKommentit)) {
            tulosta(os, kierrosaikaKohdalla);
        }**/
    }
    
    
    private void naytaSimulaattori(Kierrosaika kierrosaika) {
        textSimulaattori.clear();
        if (kierrosaika == null) return;
        Peli peli = laprecordtracker.annaPeli(kierrosaika);
        if (peli == null) return;
        textSimulaattori.setText(peli.toString());
    }
    
    
    private void muokkaa() {
        Kierrosaika valittuAika = listAutot.getSelectedObject();
        if (valittuAika == null) return;
        try {
            valittuAika = valittuAika.clone();
        } catch (CloneNotSupportedException e) {
            // Ei voi tapahtua
        }
        valittuAika = MuokkaaAikaaGUIController.kysyKierrosaika(null, valittuAika);
        if (valittuAika == null) return;
        try {
            laprecordtracker.korvaaTailisaa(valittuAika);
        } catch (SailoException e) {
            // TODO: näytä dialogi virheestä
        }
        haeKierrosaikaListaan(valittuAika.getTunnusNro());
        //ModalController.showModal(MuokkaaAikaaGUIController.class.getResource("MuokkaaAikaa.fxml"), "Kierrosaika", null, kierrosaikaKohdalla);
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
        Kilparata uusi = new Kilparata();
        Kierrosaika aikaKohdalla = listAutot.getSelectedObject();
        uusi = LisaaKilparataGUIController.kysyRata(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        laprecordtracker.lisaa(uusi);
        if (aikaKohdalla != null) {
            try {
                aikaKohdalla.setRataId(uusi.getTunnusNro());
            } catch (Exception e) {
                Dialogs.showMessageDialog("Kierrosaikoja ei ole valittuna" + e);
            }
        }
        haeRataListaan(uusi.getTunnusNro());
               
        /*
        try {
            Kilparata uusi = new Kilparata();
            uusi = LisaaKilparataGUIController.kysyRata(null, uusi);
            if (uusi == null) return;
            uusi.rekisteroi();
            laprecordtracker.lisaa(uusi);
            hae(uusi.getTunnusNro());
        } catch (Exception e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }*/
        
        /*
        ModalController.showModal(LisaaKilparataGUIController.class.getResource("LisaaKilparata.fxml"), "Kilparata", null, "");
        
        Kierrosaika kierrosKohdalla = listKilparadat.getSelectedObject();
        if (kierrosKohdalla == null) return;
        Kilparata kil = new Kilparata();
        kil.rekisteroi();
        kil.taytaKilparataTiedot(kierrosKohdalla.getTunnusNro()); // TODO: korvaa dialogilla
        laprecordtracker.lisaa(kil);
        hae(kierrosKohdalla.getTunnusNro());    */
    }
    
    
    private void uusiAika() {
        Kierrosaika uusi = new Kierrosaika();
        uusi = MuokkaaAikaaGUIController.kysyKierrosaika(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        try {
            laprecordtracker.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        haeKierrosaikaListaan(uusi.getTunnusNro());
    }
    
    
    private void uusiPeli() {
        Peli peli = new Peli();
        peli.rekisteroi();
        try {
            laprecordtracker.lisaa(peli);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        //hae(pCars2.getTunnusNro());
    }
    
    

    /**
     * Tulostaa kierrosajan tiedot
     * @param os tietovirta johon tulostetaan
     * @param kierrosaika tulostettava kierrosaika
     */
    /**
    private void tulosta(PrintStream os, final Kierrosaika kierrosaika) {
        os.println("---------------------------------------------");
        kierrosaika.tulosta(os);
        os.println("---------------------------------------------");
        List<Kilparata> kilparadat = laprecordtracker.annaKilparadat(kierrosaika);
        for (Kilparata kil : kilparadat)
            kil.tulosta(os);
        os.println("---------------------------------------------");
    }**/
}