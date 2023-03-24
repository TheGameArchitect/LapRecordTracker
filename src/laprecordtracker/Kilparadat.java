/**
 * 
 */
package laprecordtracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Matti Savolainen
 * @version 24.3.2023
 *
 */
public class Kilparadat implements Iterable<Kilparata> {
    
    private String               tiedostonNimi    = "";
    
    /** Taulukko kilparadoista */
    private final Collection<Kilparata> alkiot    = new ArrayList<Kilparata>();
    
    
    /**
     * Kilparatojen alustaminen
     */
    public Kilparadat() {
        // toistaiseksi ei tarvitse tehdä mitään
    }
    
    
    /**
     * Lisää uuden kilparadan tietorakenteeseen. Ottaa kilparadan omistukseensa.
     * @param kil lisättävä kilparata
     */
    public void lisaa(Kilparata kil) {
        alkiot.add(kil);
    }
    
    
    /**
     * Lukee kierrosajat tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Tallentaa kierrosajat tiedostoon.
     * TODO kesken
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa laprecordtrackerin kilparatojen lukumäärän
     * @return kilparatojen lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Iteraattori kaikkien kilparatojen läpikäymiseen
     * @return kilparataiteraattori
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Kilparadat kilparadat = new Kilparadat();
     * Kilparata kilpa21 = new Kilparata(2); kilparadat.lisaa(kilpa21);
     * Kilparata kilpa11 = new Kilparata(1); kilparadat.lisaa(kilpa11);
     * Kilparata kilpa22 = new Kilparata(2); kilparadat.lisaa(kilpa22);
     * Kilparata kilpa12 = new Kilparata(1); kilparadat.lisaa(kilpa12);
     * Kilparata kilpa23 = new Kilparata(2); kilparadat.lisaa(kilpa23);
     * 
     * Iterator<Kilparata> i2=kilparadat.iterator();
     * i2.next() === kilpa21;
     * i2.next() === kilpa11;
     * i2.next() === kilpa22;
     * i2.next() === kilpa12;
     * i2.next() === kilpa23;
     * i2.next() === kilpa12;   #THROWS NoSuchElementException
     * 
     * int n = 0;
     * int jnrot[] = {2,1,2,1,2};
     * 
     * for (Kilparata kil : kilparadat) {
     *     kil.getKierrosaikaNro() === jnrot[n]; n++;
     * }
     * 
     * n === 5;
     * 
     * </pre>
     */
    @Override
    public Iterator<Kilparata> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan kaikki kierrosajan kilparadat.
     * @param tunnusnro kierrosajan tunnusnumero jolle kilparata haetaan
     * @return tietorakenne jossa viitteet löydettyyn kilparataan
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Kilparadat kilparadat = new Kilparadat();
     * Kilparata kilpa21 = new Kilparata(2); kilparadat.lisaa(kilpa21);
     * Kilparata kilpa11 = new Kilparata(1); kilparadat.lisaa(kilpa11);
     * Kilparata kilpa22 = new Kilparata(2); kilparadat.lisaa(kilpa22);
     * Kilparata kilpa12 = new Kilparata(1); kilparadat.lisaa(kilpa12);
     * Kilparata kilpa23 = new Kilparata(2); kilparadat.lisaa(kilpa23);
     * Kilparata kilpa51 = new Kilparata(5); kilparadat.lisaa(kilpa51);
     * 
     * List<Kilparata> loytyneet;
     * loytyneet = kilparadat.annaKilparadat(3);
     * loytyneet.size() === 0;
     * loytyneet = kilparadat.annaKilparadat(1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == kilpa11 === true;
     * loytyneet.get(1) == kilpa12 === true;
     * loytyneet = kilparadat.annaKilparadat(5);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == kilpa51 === true;
     * </pre>
     */
    public List<Kilparata> annaKilparadat(int tunnusnro) {
        List<Kilparata> loydetyt = new ArrayList<Kilparata>();
        for (Kilparata kil : alkiot)
            if (kil.getKierrosaikaNro() == tunnusnro) loydetyt.add(kil);
        return loydetyt;
    }
    
    
    /**
     * Testiohjelma kilparadoille
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kilparadat kilparadat = new Kilparadat();
        Kilparata kilpa1 = new Kilparata();
        kilpa1.taytaKilparataTiedot(2);
        Kilparata kilpa2 = new Kilparata();
        kilpa2.taytaKilparataTiedot(1);
        Kilparata kilpa3 = new Kilparata();
        kilpa3.taytaKilparataTiedot(2);
        Kilparata kilpa4 = new Kilparata();
        kilpa4.taytaKilparataTiedot(2);
        
        
        kilparadat.lisaa(kilpa1);
        kilparadat.lisaa(kilpa2);
        kilparadat.lisaa(kilpa3);
        kilparadat.lisaa(kilpa2);
        kilparadat.lisaa(kilpa4);
        
        System.out.println("=================== Kilparadat testi =========================");
        
        List<Kilparata> kilparadat2 = kilparadat.annaKilparadat(1);
        
        for (Kilparata kil : kilparadat2) {
            System.out.print(kil.getKierrosaikaNro() + " ");
            kil.tulosta(System.out);
        }
    }

}
