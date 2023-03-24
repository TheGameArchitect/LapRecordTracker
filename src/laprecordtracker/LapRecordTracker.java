/**
 * 
 */
package laprecordtracker;

/**
 * CRC sisällöt tähän
 * @author Matruusi
 * @version 24.3.2023
 *
 */
public class LapRecordTracker {
    
    Kierrosajat kierrosajat = new Kierrosajat();
    
    
    /**
     * Lisätään uusi kierrosaika
     * @param kierrosaika lisättävä kierrosaika
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Kierrosaika kierrosaika) throws SailoException {
        kierrosajat.lisaa(kierrosaika);
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
