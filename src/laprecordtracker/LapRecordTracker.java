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
    
    private Kilparata apuKilparata;
    private Kierrosaika kierrosaikaKohdalla;    
    private String hakemisto = "kierrosajat";
    
    /**
     * Lisätään uusi peli
     * @param peli lisättävä peli
     * @param peliNimi pelin nimi
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Peli peli, String peliNimi) throws SailoException {
        peli.setNimi(peliNimi);
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
     * Korvaa kierrosajan, jolla sama id
     * @param valittuAika millä korvataan
     * @throws SailoException jos joku menee pieleen
     */
    public void korvaaTailisaa(Kierrosaika valittuAika) throws SailoException {
        kierrosajat.korvaaTaiLisaa(valittuAika);
    }
    
    
    /**
     * Lisätään uusi kilparata laprecordtrackeriin
     * @param rata lisättävä kilparata
     */
    public void lisaa(Kilparata rata) {
        kilparadat.lisaa(rata);
    }
    
    
    /**
     * @return kierrosaikojen lukumäärä
     */
    public int getKierrosaikoja() {
        return kierrosajat.getLkm();
    }
    
    
    /**
     * @return kilparatojen lukumäärä
     */
    public int getKilparatoja() {
        return kilparadat.getLkm();
    }
    
    
    /**
     * @return apukilparata uuden tekemistä varten
     */
    public Kilparata getApuKilparata() {
        return this.apuKilparata;
    }
    
    
    /**
     * @param kilparata käyttäjän antamilla tiedoilla täytettä kilparata
     * @return null jos virhe
     */
    public Kilparata setApuKilparata(Kilparata kilparata) {
        apuKilparata = kilparata;
        return null;
    }
    
    
    /**
     * @return käyttöliittymässä valittu kierrosaika
     */
    public Kierrosaika getKierrosaikaKohdalla() {
        return this.kierrosaikaKohdalla;
    }
    
    
    /**
     * @param kierrosaika käyttöliittymässä valittu kierrosaika
     * @return null jos virhe
     */
    public Kierrosaika setKierrosaikaKohdalla(Kierrosaika kierrosaika) {
        kierrosaikaKohdalla = kierrosaika;
        return null;
    }
    
    
    /**
     * Antaa pelien i:n pelin
     * @param i monesko peli
     * @return peli paikasta i
     */
    public Peli annaPeli(int i) {
        return pelit.anna(i);
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
     * Antaa kilparatojen i:n kilparadan
     * @param i monesko rata haetaan
     * @return i:nes kilparata
     */
    public Kilparata annaKilparata(int i) {
        return kilparadat.annaRata(i);
    }
    
    
    /**
     * Haetaan kierrosajan kilparata
     * @param kierrosaika kierrosaika jolle kilparata haetaan
     * @return tietorakenne jossa viitteet löydettyihin kilparatoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *LapRecordTracker laprecordtracker = new LapRecordTracker();
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
    public List<Kilparata> annaKilparadat(Kierrosaika kierrosaika) {
        return kilparadat.annaKilparadat(kierrosaika.getTunnusNro());
    }
    
    
    /**
     * Etsitään kierrosajan ensimmäinen kilparata
     * @param tunnusNro minkä kierrosajan kilparata etsitään
     * @return kierrosajan ensimmäinen kilparata
     */
    public String annaKilparataNimi(int tunnusNro) {
        String rata = new String();
        rata = kilparadat.annaKilparataNimi(tunnusNro);
        return rata;
    }
    
    
    /**
     * Haetaan halutun kierrosajan auton nimi
     * @param tunnusNro minkä kierrosajan autoa etsitään
     * @return kierrosajan auton nimi
     */
    public String annaAuto(int tunnusNro) {
        String auto = new String();
        auto = kierrosajat.annaAuto(tunnusNro);
        return auto;
    }
    
    
    /**
     * @param kierrosaika poistettava kierrosaika
     * @return 0 jos poistaminen onnistui
     */
    public int poista(Kierrosaika kierrosaika) {
        if (kierrosaika == null) return 0;
        int ret = kierrosajat.poista(kierrosaika.getTunnusNro());
        return ret;
    }
    
    
    /**
     * 
     * @param kilparata poistettava kilparata
     * @return 0 jos poistaminen onnistui
     */
    public int poistaRata(Kilparata kilparata) {
        if (kilparata == null) return 0;
        int ret = kilparadat.poista(kilparata.getTunnusNro());
        return ret;
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
            //laprecordtracker.lisaa(acorsa);
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
