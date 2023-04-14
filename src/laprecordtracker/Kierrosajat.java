package laprecordtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Matti Savolainen
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
     * TESTIT EI TOIMI NYT KOSKA SailoExceptionia EI TULE SILLÄ TAULUKON KOKOA KASVATETAAN, JOS SE KÄY PIENEKSI
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
     * 1|1|6.37.745|Pagani Zonda R|Medium|Puolipilvinen|ABS|1|Epävakaa yli 200km/h vauhdissa|
     * 2|2|1.58.534|Ferrari 458 GT3|Soft|Aurinkoinen|ABS|3||
     * 3|3|1.33.424|Mitsubishi Lancer Evo|Wet|Kaatosade|ABS, TCS2|4||
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
        //} catch (IOException e) {
        //    throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
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
            // e.printStackTrace();
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
