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
        //ModalController.showModal(PoistaAikaKyselyGUIController.class.getResource("PoistaAikaKysely.fxml"), "", null, "");
        poistaKierrosaika();
    }
    
    @FXML
    private void buttonPoistaRata() {
        poistaKilparata();
    }

    @FXML
    private void buttonUusiAika() {
        //ModalController.showModal(UusiAikaGUIController.class.getResource("UusiAika.fxml"), "Kierrosaika", null, "");
        uusiAika();
        //uusiPeli();
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
        haku(0);
        
        String hakuTeksti = textHaku.getText();
        if (hakuTeksti.isEmpty()) naytaVirhe(null);
        else naytaVirhe(null);
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
            haeRataListaan(0);
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
     * Haetaan käyttäjän haluamat kilparadat listaan
     * @param jnro mikä kilparata valitaan aktiiviseksi
     */
    private void haku(int jnro) {
        listKilparadat.clear();
        String ehto = textHaku.getText().toLowerCase();
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKilparatoja(); i++) {
            Kilparata kilparata = laprecordtracker.annaKilparata(i+1);
            if (!kilparata.getKilparata().toLowerCase().contains(ehto)) continue;
            if (kilparata.getTunnusNro() == jnro) index = i;
            listKilparadat.add("" + kilparata.getKilparata(), kilparata);
        }
        listKilparadat.setSelectedIndex(index);
    }
    
    
    /**
     * Haetaan kilparadat oikeaan listaan
     * @param jnro mikä kilparata valitaan aktiiviseksi
     */
    private void haeRataListaan(int jnro) {
        listKilparadat.clear();
        int rataIndeksi = 1;
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKilparatoja(); i++) {
            if (laprecordtracker.getKilparatoja() < 0) return;
            Kilparata kilparata = laprecordtracker.annaKilparata(rataIndeksi);
            listKilparadat.add("" + kilparata.getKilparata(), kilparata);
            if (kilparata.getTunnusNro() == jnro) index = i;
            rataIndeksi++;
        }
        listKilparadat.setSelectedIndex(index);
    }
    
    
    /**
     * Haetaan kierrosajat oikeaan listaan
     * @param jnro mikä kierrosaika valitaan aktiiviseksi
     */
    private void haeKierrosaikaListaan(int jnro) {
        Kilparata valittuRata = listKilparadat.getSelectedObject();
        int rataId = 0;
        try {
            rataId = valittuRata.getTunnusNro();
        } catch (Exception e) {
            Dialogs.showMessageDialog("Kilparatoja ei löydy");
        }
        
        listAutot.clear();
        int index = 0;
        for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
            Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
            if (rataId == kierrosaika.getRataId())
                listAutot.add("" + kierrosaika.getAuto(), kierrosaika);
            if (kierrosaika.getTunnusNro() == jnro) index = i;
        }
        listAutot.setSelectedIndex(index);
    }
    
    
    private void naytaKilparata() {
        Kilparata valittuRata = listKilparadat.getSelectedObject();
        Kierrosaika aikaKohdalla = listAutot.getSelectedObject();
        naytaKierrosaika();
        haeKierrosaikaListaan(0);
        if (aikaKohdalla == null || valittuRata == null) return;
    }
    
    
    private void naytaKierrosaika() {
        Kierrosaika kierrosaikaKohdalla = listAutot.getSelectedObject();
        if (kierrosaikaKohdalla == null) return;
        
        MuokkaaAikaaGUIController.naytaKierrosaika(edits, textKommentit, kierrosaikaKohdalla);
        naytaSimulaattori(kierrosaikaKohdalla);
    }
    
    
    private void naytaSimulaattori(Kierrosaika kierrosaika) {
        textSimulaattori.clear();
        if (kierrosaika == null) return;
        Peli peli = laprecordtracker.annaPeli(kierrosaika);
        if (peli == null) return;
        textSimulaattori.setText(peli.getPeli());
    }
    
    
    private void poistaKierrosaika() {
        try {
            laprecordtracker.setKierrosaikaKohdalla(listAutot.getSelectedObject());
        } catch (Exception e) {
            Dialogs.showMessageDialog("Kierrosaikaa ei ole valittuna");
        }
        Kierrosaika kierrosaika = laprecordtracker.getKierrosaikaKohdalla();
        if (kierrosaika == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko kierrosaika: " + kierrosaika.getAuto(), "Kyllä", "Ei"))
            return;
        laprecordtracker.poista(kierrosaika);
        int index = listAutot.getSelectedIndex();
        haeKierrosaikaListaan(0);
        listAutot.setSelectedIndex(index);
    }
    
    
    private void poistaKilparata() {
        Kilparata rataKohdalla = new Kilparata();
        try {
            rataKohdalla = listKilparadat.getSelectedObject();
        } catch (Exception e) {
            Dialogs.showMessageDialog("Kilparataa ei ole valittuna");
        }
        if (rataKohdalla == null) return;
        if (listAutot.getSelectedObject() != null) {
            Dialogs.showMessageDialog("Poista ensin kaikki kierrosajat");
            return;
        }
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko kilparata: " + rataKohdalla.getKilparata(), "Kyllä", "Ei"))
            return;
        laprecordtracker.poistaRata(rataKohdalla);
        int index = listKilparadat.getSelectedIndex();
        haeRataListaan(0);
        listKilparadat.setSelectedIndex(index);
    }
    
    
    private void muokkaa() {
        laprecordtracker.setKierrosaikaKohdalla(listAutot.getSelectedObject());
        Kierrosaika valittuAika = laprecordtracker.getKierrosaikaKohdalla();
        //LapRecordTracker muokattuTracker = new LapRecordTracker();
        //muokattuTracker = laprecordtracker;
        if (valittuAika == null) return;
        try {
            valittuAika = valittuAika.clone();
        } catch (CloneNotSupportedException e) {
            // Ei voi tapahtua
        }
        MuokkaaAikaaGUIController.kysyKierrosaika(null, laprecordtracker);
        valittuAika = laprecordtracker.getKierrosaikaKohdalla();
        if (valittuAika == null) return;
        laprecordtracker.setKierrosaikaKohdalla(valittuAika);
        try {
            laprecordtracker.korvaaTailisaa(laprecordtracker.getKierrosaikaKohdalla());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tietorakenne täynnä tai kloonaus ei onnistunut " + e);
        }
        naytaSimulaattori(laprecordtracker.getKierrosaikaKohdalla());
        haeKierrosaikaListaan(laprecordtracker.getKierrosaikaKohdalla().getTunnusNro());
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
    }
    
    
    private void uusiAika() {
        Kierrosaika uusi = new Kierrosaika();
        laprecordtracker.setKierrosaikaKohdalla(uusi);
        MuokkaaAikaaGUIController.kysyKierrosaika(null, laprecordtracker);
        uusi = laprecordtracker.getKierrosaikaKohdalla();
        if (uusi == null) return;
        uusi.rekisteroi();
        try {
            laprecordtracker.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        naytaSimulaattori(uusi);
        haeKierrosaikaListaan(uusi.getTunnusNro());
    }
    
    /*
    private void uusiPeli() {
        Peli peli = new Peli();
        peli.rekisteroi();
        try {
            laprecordtracker.lisaa(peli);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
    }*/
    
    

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