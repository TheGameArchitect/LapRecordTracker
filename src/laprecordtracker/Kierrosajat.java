package laprecordtracker;

import java.util.Arrays;

/**
 * @author Matti Savolainen
 * @version 23.3.2023
 *
 */
public class Kierrosajat {
    
    private static final int MAX_KIERROSAIKOJA = 5;
    
    int lkm = 0;
    private Kierrosaika[] alkiot;
    
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
     * kierrosajat.anna(3) === zonda1; #THROWS IndexOutOfBoundsException
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 4;
     * kierrosajat.lisaa(zonda1); kierrosajat.getLkm() === 5;
     * kierrosajat.lisaa(zonda1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kierrosaika kierrosaika) throws SailoException {
        if (lkm >= alkiot.length) {
            alkiot = Arrays.copyOf(alkiot, alkiot.length+10);
        }
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita, taulukon koon kasvattaminen ei onnistunut");
        alkiot[lkm] = kierrosaika;
        lkm++;
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kierrosajat kierrosajat = new Kierrosajat();
        Kierrosaika zonda1 = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();
        Kierrosaika zonda3 = new Kierrosaika();
        Kierrosaika zonda4 = new Kierrosaika();
        Kierrosaika zonda5 = new Kierrosaika();
        Kierrosaika zonda6 = new Kierrosaika();
        
        zonda1.rekisteroi();
        zonda1.taytaKierrosajanTiedot();
        zonda2.rekisteroi();
        zonda2.taytaKierrosajanTiedot();
        zonda3.rekisteroi();
        zonda3.taytaKierrosajanTiedot();
        zonda4.rekisteroi();
        zonda4.taytaKierrosajanTiedot();
        zonda5.rekisteroi();
        zonda5.taytaKierrosajanTiedot();
        zonda6.rekisteroi();
        zonda6.taytaKierrosajanTiedot();
        
        try {
            kierrosajat.lisaa(zonda1);
            kierrosajat.lisaa(zonda2);
            kierrosajat.lisaa(zonda3);
            kierrosajat.lisaa(zonda4);
            kierrosajat.lisaa(zonda5);
            kierrosajat.lisaa(zonda6);
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
    }

}
