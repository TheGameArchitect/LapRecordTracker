/**
 * 
 */
package laprecordtracker;

import java.util.Arrays;

/**
 * @author Matruusi
 * @version 13.4.2023
 *
 */
public class Pelit {

    private static final int MAX_PELEJA = 5;
    
    int lkm = 0;
    private Peli[] alkiot;
    
    /**
     * Luodaan alustava taulukko
     */
    public Pelit() {
        alkiot = new Peli[MAX_PELEJA];
    }
    
    
    /**
     * Lisää uuden pelin tietorakenteeseen. Ottaa pelin omistukseensa.
     * @param peli lisättävän pelin viite. 
     * @throws SailoException jos tietorakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Pelit pelit = new Pelit();
     * Peli acorsa = new Peli(), acorsa2 = new Peli();
     * pelit.getLkm() === 0;
     * pelit.lisaa(acorsa); pelit.getLkm() === 1;
     * pelit.lisaa(acorsa2); pelit.getLkm() === 2;
     * pelit.lisaa(acorsa); pelit.getLkm() === 3;
     * pelit.anna(0) === acorsa;
     * pelit.anna(1) === acorsa2;
     * pelit.anna(2) === acorsa;
     * pelit.anna(1) == acorsa === false;
     * pelit.anna(1) == acorsa2 === true;
     * pelit.lisaa(acorsa); pelit.getLkm() === 4;
     * pelit.lisaa(acorsa); pelit.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Peli peli) throws SailoException {
        if (lkm >= alkiot.length) {
            alkiot = Arrays.copyOf(alkiot, alkiot.length+10);
        }
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita, taulukon koon kasvattaminen ei onnistunut");
        alkiot[lkm] = peli;
        lkm++;
    }
    
    
    /**
     * Palauttaa viitteen i:teen peliin.
     * @param i minkä pelin viite halutaan
     * @return viite peliin, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Peli anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Palauttaa Peli-taulukon pelien lukumäärän.
     * @return pelien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Pelit pelit = new Pelit();
        Peli acorsa = new Peli();
        Peli automobilista = new Peli();
        
        acorsa.rekisteroi();
        acorsa.taytaPeliTiedot(1);
        automobilista.rekisteroi();
        automobilista.taytaPeliTiedot(2);
        
        try {
            pelit.lisaa(acorsa);
            pelit.lisaa(automobilista);
        } catch (SailoException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
        
        System.out.println("======================= Kierrosajat testi ===========================");
        
        for (int i = 0; i < pelit.getLkm(); i++) {
            Peli pCars = pelit.anna(i);
            System.out.println("Peli indeksi: " + i);
            pCars.tulosta(System.out);
        }
    }

}
