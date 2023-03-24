/**
 * 
 */
package laprecordtracker;

import java.util.List;

/**
 * CRC sisällöt tähän
 * @author Matti Savolainen
 * @version 24.3.2023
 *
 */
public class LapRecordTracker {
    
    private final Kierrosajat kierrosajat = new Kierrosajat();
    private final Kilparadat kilparadat = new Kilparadat();
    
    
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
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        LapRecordTracker laprecordtracker = new LapRecordTracker();
        
        Kierrosaika zonda = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();
        zonda.rekisteroi();
        zonda.taytaKierrosajanTiedot();
        zonda2.rekisteroi();
        zonda2.taytaKierrosajanTiedot();
        
        try {
            laprecordtracker.lisaa(zonda);
            laprecordtracker.lisaa(zonda2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
        for (int i = 0; i < laprecordtracker.getKierrosaikoja(); i++) {
            Kierrosaika kierrosaika = laprecordtracker.annaKierrosaika(i);
            kierrosaika.tulosta(System.out);
        }
    }

}
