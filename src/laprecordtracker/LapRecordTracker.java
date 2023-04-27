/**
 * 
 */
package laprecordtracker;

import java.io.File;
import java.util.List;

/**
 * CRC sisällöt tähän
 * @author Matti Savolainen
 * @version 24.3.2023
 *
 */
public class LapRecordTracker {
    
    private Kierrosajat kierrosajat = new Kierrosajat();
    private Kilparadat kilparadat = new Kilparadat();
    private Pelit pelit = new Pelit();
    
    private String hakemisto = "kierrosajat";
    
    /**
     * Lisätään uusi peli
     * @param peli lisättävä peli
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Peli peli) throws SailoException {
        pelit.lisaa(peli);
    }
    
    
    /**
     * Lisätään uusi kierrosaika
     * @param kierrosaika lisättävä kierrosaika
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Kierrosaika kierrosaika) throws SailoException {
        kierrosajat.lisaa(kierrosaika);
    }
    
    
    /**
     * Lisätään uusi kilparata laprecordtrackeriin
     * @param kil lisättävä kilparata
     */
    public void lisaa(Kilparata kil) {
        kilparadat.lisaa(kil);
    }
    
    
    /**
     * @return kierrosaikojen lukumäärä
     */
    public int getKierrosaikoja() {
        return kierrosajat.getLkm();
    }
    
    
    /**
     * Antaa pelien i:n pelin
     * @param kierrosaika monesko peli
     * @return peli paikasta i
     */
    public Peli annaPeli(Kierrosaika kierrosaika) {
        int tunnusNro = kierrosaika.getTunnusNro();
        tunnusNro -= 1;
        return pelit.anna(tunnusNro); //TODO: indeksi on väärin jossain kohtaa sillä heittää IndexOutOfBoundsExc
    }
    
    
    /**
     * Antaa kierrosaikojen i:n kierrosajan
     * @param i monesko kierrosaika
     * @return kierrosaika paikasta i
     */
    public Kierrosaika annaKierrosaika(int i) {
        return kierrosajat.anna(i);
    }
    
    
    /**
     * Haetaan kierrosajan kilparata
     * @param kierrosaika kierrosaika jolle kilparata haetaan
     * @return tietorakenne jossa viitteet löydettyihin kilparatoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * LapRecordTracker laprecordtracker = new LapRecordTracker();
     * Kierrosaika zonda1 = new Kierrosaika(), zonda2 = new Kierrosaika(), zonda3 = new Kierrosaika();
     * zonda1.rekisteroi(); zonda2.rekisteroi(); zonda3.rekisteroi();
     * int id1 = zonda1.getTunnusNro();
     * int id2 = zonda2.getTunnusNro();
     * Kilparata kilpa11 = new Kilparata(id1); laprecordtracker.lisaa(kilpa11);
     * Kilparata kilpa12 = new Kilparata(id1); laprecordtracker.lisaa(kilpa12);
     * Kilparata kilpa21 = new Kilparata(id2); laprecordtracker.lisaa(kilpa21);
     * Kilparata kilpa22 = new Kilparata(id2); laprecordtracker.lisaa(kilpa22);
     * Kilparata kilpa23 = new Kilparata(id2); laprecordtracker.lisaa(kilpa23);
     * 
     * List<Kilparata> loytyneet;
     * loytyneet = laprecordtracker.annaKilparadat(zonda3);
     * loytyneet.size() === 0;
     * loytyneet = laprecordtracker.annaKilparadat(zonda1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == kilpa11 === true;
     * loytyneet.get(1) == kilpa12 === true;
     * loytyneet = laprecordtracker.annaKilparadat(zonda2);
     * loytyneet.size() === 3; 
     * loytyneet.get(0) == kilpa21 === true;
     * </pre>
     */
    public List<Kilparata> annaKilparadat(Kierrosaika kierrosaika){
        return kilparadat.annaKilparadat(kierrosaika.getTunnusNro());
    }
    
    
    /**
     * Lukee laprecordtrackerin tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        kierrosajat = new Kierrosajat();
        kilparadat = new Kilparadat();
        pelit = new Pelit();
        
        hakemisto = nimi;
        kierrosajat.lueTiedostosta(nimi);
        kilparadat.lueTiedostosta(nimi);
        pelit.lueTiedostosta(nimi);
    }
    
    
    /**
     * Tallentaa laprecordtrackerin tiedot tiedostoon
     * @throws SailoException jos tallentaminen epäonnistuu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kierrosajat.tallenna(hakemisto);
        }   catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            kilparadat.tallenna(hakemisto);
        }   catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        
        try {
            pelit.tallenna(hakemisto);
        }   catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }
    

    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        LapRecordTracker laprecordtracker = new LapRecordTracker();
        
        try {
            laprecordtracker.lueTiedostosta("testiRadat");
        }   catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
        Kierrosaika zonda = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();        
        zonda.rekisteroi();
        zonda.taytaKierrosajanTiedot();
        zonda2.rekisteroi();
        zonda2.taytaKierrosajanTiedot();
        
        try {
            laprecordtracker.lisaa(zonda);
            laprecordtracker.lisaa(zonda2);
            
            for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
                Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
                kierrosaika.tulosta(System.out);
            }
            
            laprecordtracker.tallenna();
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        Peli acorsa = new Peli();
        acorsa.rekisteroi();
        acorsa.taytaPeliTiedot(3);
        
        try {
            laprecordtracker.lisaa(zonda);
            laprecordtracker.lisaa(zonda2);
            laprecordtracker.lisaa(acorsa);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
            Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
            kierrosaika.tulosta(System.out);
            System.out.println("-------------------------------------------------");
        }
    }

}
