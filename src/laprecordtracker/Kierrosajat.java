package laprecordtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Tietorakenne kierrosaikojen säilömistä varten. 
 * Hoitaa kierrosaikojen tallentamisen ja tiedostosta lukemisen.
 * @author Matti Savolainen - savomaaa@student.jyu.fi
 * @version 23.3.2023
 *
 */
public class Kierrosajat {
    
    private static final int MAX_KIERROSAIKOJA = 5;
    
    int lkm = 0;
    private Kierrosaika[] alkiot;
    private String tiedostonNimi = "";
    private boolean muutettu = false;
    
    
    /**
     * Luodaan alustava taulukko
     */
    public Kierrosajat() {
        alkiot = new Kierrosaika[MAX_KIERROSAIKOJA];
    }
    
    /**
     * Lisää uuden kierrosajan tietorakenteeseen. Ottaa kierrosajan omistukseensa.
     * @param kierrosaika lisättävän kierrosajan viite. 
     * @throws SailoException jos tietorakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kierrosajat kierrosajat = new Kierrosajat();
     * Kierrosaika zonda1 = new Kierrosaika(), zonda2 = new Kierrosaika();
     * kierrosajat.getLkm() === 0;
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 1;
     * kierrosajat.lisaa(zonda2); kierrosajat.getLkm() === 2;
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 3;
     * kierrosajat.anna(0) === zonda1;
     * kierrosajat.anna(1) === zonda2;
     * kierrosajat.anna(2) === zonda1;
     * kierrosajat.anna(1) == zonda1 === false;
     * kierrosajat.anna(1) == zonda2 === true;
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 4;
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kierrosaika kierrosaika) throws SailoException {
        if (lkm >= alkiot.length) {
            alkiot = Arrays.copyOf(alkiot, alkiot.length+10);
        }
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita, taulukon koon kasvattaminen ei onnistunut");
        alkiot[lkm] = kierrosaika;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Etsitään samalla tunnusnumerolla oleva kierrosaika. Jos ei löydy,
     * niin lisätään uutena kierrosaikana.
     * @param valittuAika lisättävän kierrosajan viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException, CloneNotSupportedException
     * #PACKAGEIMPORT
     * Kierrosajat kierrosajat = new Kierrosajat();
     * Kierrosaika aika1 = new Kierrosaika(), aika2 = new Kierrosaika();
     * aika1.rekisteroi(); aika2.rekisteroi();
     * kierrosajat.getLkm() === 0;
     * kierrosajat.korvaaTaiLisaa(aika1); kierrosajat.getLkm() === 1;
     * kierrosajat.korvaaTaiLisaa(aika2); kierrosajat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Kierrosaika valittuAika) throws SailoException {
        int id = valittuAika.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = valittuAika;
                muutettu = true;
                return;
            }
        }
        lisaa(valittuAika);
    }
    
    
    /**
     * Palauttaa viitteen i:teen kierrosaikaan.
     * @param i minkä kierrosajan viite halutaan
     * @return viite kierrosaikaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Kierrosaika anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Etsii halutun kierrosajan auton nimen
     * @param tunnusNro minkä kierrosajan auto haetaan
     * @return kierrosajan auto
     */
    public String annaAuto(int tunnusNro) {
        for (Kierrosaika aika : alkiot) {
            if (aika.getTunnusNro() == tunnusNro) return aika.getAuto();
        }
        return "Auton nimeä ei löydy";
    }
    
    
    /**
     * Etsitään poistettava kierrosaika
     * @param id poistettavan kierrosajan tunnusnumero
     * @return kierrosajan järjestysnumero jos onnistuu, muuten -1
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getTunnusNro()) return i;
        return -1;
    }
    
    
    /**
     * Poistetaan haluttu kierrosaika
     * @param id poistettavan kierrosajan tunnusnumero
     * @return 1 jos onnistui, muuten 0
     */
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i+1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    
    /**
     * Palauttaa Kierrosaika-taulukon kierrosaikojen lukumäärän.
     * @return kierrosaikojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tallentaa kierrosajan tiedostoon.
     * Tiedoston muoto:
     * <pre>
     * 1|Pagani Zonda R|04.36.375|Medium|Kuiva|TCS 2|Kommentti|1|
     * 3|Volvo 940|00.32.423|Kumiset|Kaatosade|Ei|Kone keitti|1|
     * 4|Renault Megane Scenic|02.34.342|Kumiset|Kyllä|Ei|Hirvi juoksi edestä|1|
     * <pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        if (!muutettu) return;
        File ftied = new File(hakemisto + "/kierrosajat.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < this.getLkm(); i++) {
                Kierrosaika kierrosaika = this.anna(i);
                fo.println(kierrosaika.toString());
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
    }
    
    
    /**
     * @throws SailoException jos tallentaminen epäonnistuu
     * 
     */
    public void tallenna() throws SailoException {
        tallenna(tiedostonNimi);
    }
    
    
    /**
     * Lukee kierrosajat tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/kierrosajat.dat";
        String nimi = tiedostonNimi;
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if (s == null || "".equals(s) || s.charAt(0) == ';') continue;
                Kierrosaika kierrosaika = new Kierrosaika();
                kierrosaika.parse(s);
                lisaa(kierrosaika);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }
    
    
    /**
     * Testiohjelma luokan testaamista varten.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kierrosajat kierrosajat = new Kierrosajat();
        
        try {
            kierrosajat.lueTiedostosta("kierrosajat");
        }   catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        Kierrosaika zonda1 = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();
        Kierrosaika zonda3 = new Kierrosaika();
        
        zonda1.rekisteroi();
        zonda1.taytaKierrosajanTiedot();
        zonda2.rekisteroi();
        zonda2.taytaKierrosajanTiedot();
        zonda3.rekisteroi();
        zonda3.taytaKierrosajanTiedot();
        
        try {
            kierrosajat.lisaa(zonda1);
            kierrosajat.lisaa(zonda2);
            kierrosajat.lisaa(zonda3);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        System.out.println("======================= Kierrosajat testi ===========================");
        
        for (int i = 0; i < kierrosajat.getLkm(); i++) {
            Kierrosaika zonda = kierrosajat.anna(i);
            System.out.println("Kierrosaika indeksi: " + i);
            zonda.tulosta(System.out);
        }
        
        try {
            kierrosajat.tallenna("kierrosajat");
        }   catch (SailoException e) {
            System.err.println(e.getMessage());
        }
    }
}
