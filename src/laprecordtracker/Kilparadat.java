/**
 * 
 */
package laprecordtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Tietojärjestelmä kilparatojen säilömistä varten.
 * Tallentaa tiedot tiedostoon ja osaa lukea ne sieltä.
 * @author Matti Savolainen - savomaaa@student.jyu.fi
 * @version 24.3.2023
 *
 */
public class Kilparadat implements Iterable<Kilparata> {
    
    private String               tiedostonNimi    = "";
    
    /** Taulukko kilparadoista */
    private final Collection<Kilparata> alkiot    = new ArrayList<Kilparata>();
    
    
    /** Kilparatojen alustaminen */
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
     * @param hakemisto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * Kilparadat kilparadat = new Kilparadat();
     * Kilparata ahven21 = new Kilparata(); ahven21.taytaKilparataTiedot(2);
     * Kilparata ahven11 = new Kilparata(); ahven11.taytaKilparataTiedot(1);
     * Kilparata ahven22 = new Kilparata(); ahven22.taytaKilparataTiedot(2);
     * Kilparata ahven12 = new Kilparata(); ahven12.taytaKilparataTiedot(1);
     * Kilparata ahven23 = new Kilparata(); ahven23.taytaKilparataTiedot(2);
     * String tiedNimi = "testiRadat";
     * File ftied = new File(tiedNimi + "/kilparadat.dat");
     * ftied.delete();
     * kilparadat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * kilparadat.lisaa(ahven21);
     * kilparadat.lisaa(ahven11);
     * kilparadat.lisaa(ahven22);
     * kilparadat.lisaa(ahven12);
     * kilparadat.lisaa(ahven23);
     * kilparadat.tallenna(tiedNimi);
     * kilparadat = new Kilparadat();
     * kilparadat.lueTiedostosta(tiedNimi);
     * Iterator<Kilparata> i = kilparadat.iterator();
     * i.next().toString() === ahven21.toString();
     * i.next().toString() === ahven11.toString();
     * i.next().toString() === ahven22.toString();
     * i.next().toString() === ahven12.toString();
     * i.next().toString() === ahven23.toString();
     * i.hasNext() === false;
     * kilparadat.lisaa(ahven23);
     * kilparadat.tallenna(tiedNimi);
     * ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/kilparadat.dat";
        File ftied = new File(tiedostonNimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while (fi.hasNext()) {
                String s = fi.nextLine().trim();
                if ("".equals(s) || s.charAt(0) == ';') continue;
                Kilparata kil = new Kilparata();
                kil.parse(s);
                lisaa(kil);
            }
        }   catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + tiedostonNimi);
        }
    }
    
    /**
     * Tallentaa kierrosajat tiedostoon.
     * Tiedoston muoto:
     * <pre>
     * 1|0|KymiRing
     * 2|0|Suzuka
     * <pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos tallentaminen epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/kilparadat.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (var har : alkiot) {
                fo.println(har.toString());
            }
        }   catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
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
     *     kil.getPeliNro() === jnrot[n]; n++;
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
     * Poistetaan haluttu kilparata
     * @param id poistettavan kilparadan tunnusnumero
     * @return 1 jos onnistui
     */
    public int poista(int id) {
        List<Kilparata> loydetyt = new ArrayList<Kilparata>();
        for (Kilparata rata : alkiot) {
            if (rata.getTunnusNro() == id) loydetyt.add(rata);
        }
        alkiot.remove(loydetyt.get(0));
        return 1;
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
            if (kil.getPeliNro() == tunnusnro) loydetyt.add(kil);
        return loydetyt;
    }
    
    
    /**
     * Haetaan i:nes kilparata
     * @param i mikä kilparata palautetaan
     * @return i:nen kilparadan
     */
    public Kilparata annaRata(int i) {
        List<Kilparata> loydetyt = new ArrayList<Kilparata>();
        for (Kilparata rata : alkiot) {
            if (rata.getTunnusNro() == i) loydetyt.add(rata);
        }
        Kilparata rata = loydetyt.get(0);
        return rata;
    }
    
    
    /**
     * Etsitään kierrosajan ensimmäinen kilparata
     * @param tunnusnro käsiteltävä kierrosaika
     * @return kierrosajan ensimmäinen kilparata
     * TODO: Testaa
     */
    public String annaKilparataNimi(int tunnusnro) {
        for (Kilparata rata : alkiot) {
            if (rata.getTunnusNro() == tunnusnro) return rata.getKilparata();
        }
        return "Radan nimeä ei löydy";
    }
    
    
    /**
     * Testiohjelma kilparadoille
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kilparadat kilparadat = new Kilparadat();
        
        try {
            kilparadat.lueTiedostosta("kierrosajat");
        }   catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        
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
        
        List<Kilparata> kilparadat2 = kilparadat.annaKilparadat(2);
        
        for (Kilparata kil : kilparadat2) {
            System.out.print(kil.getPeliNro() + " ");
            kil.tulosta(System.out);
        }
        
        try {
            kilparadat.tallenna("kierrosajat");
        }   catch (SailoException e) {
            e.printStackTrace();
        }
    }

}
