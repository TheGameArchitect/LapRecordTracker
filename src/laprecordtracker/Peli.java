/**
 * 
 */
package laprecordtracker;

import java.io.OutputStream;
import java.io.PrintStream;

import kanta.KierrosaikaTarkistus;

/**
 * @author Matti Savolainen
 * @version 13.4.2023
 *
 */
public class Peli {

    private String      testiLukema = "";
    
    private int         tunnusNro   = 0;
    private String      peli        = "";
    
    private static int  seuraavaNro = 1;
    
    
    /**
     * Tulostetaan kierrosajan tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " ");
        out.println("Peli: " + peli);
    }
    
    
    /**
     * Tulostetaan pelien tiedot.
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Palauttaa pelin tunnusnumeron.
     * @return pelin tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * @return peli
     */
    public String getPeli() {
        return this.peli;
    }
    
    
    /**
     * Antaa uudelle pelille seuraavan rekisterinumeron.
     * @return pelin uusi tunnusNro
     * @example
     * <pre name="test">
     *  Peli acorsa = new Peli();
     *  acorsa.getTunnusNro() === 0;
     *  acorsa.rekisteroi();
     *  Peli automobilista = new Peli();
     *  automobilista.rekisteroi();
     *  int n1 = acorsa.getTunnusNro();
     *  int n2 = automobilista.getTunnusNro();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot pelille.
     * TODO: poista kun kaikki toimii
     */
    public void taytaPeliTiedot() {
        testiLukema = KierrosaikaTarkistus.arvoKierrosaika();
        peli = "Assetto Corsa " + testiLukema;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Peli acorsa = new Peli();
        Peli automobilista = new Peli();
        
        acorsa.rekisteroi();
        automobilista.rekisteroi();
        
        acorsa.tulosta(System.out);
        
        acorsa.taytaPeliTiedot();
        automobilista.taytaPeliTiedot();
        
        acorsa.tulosta(System.out);
        automobilista.tulosta(System.out);
    }

}
