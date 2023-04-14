/**
 * 
 */
package laprecordtracker;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.KierrosaikaTarkistus;

/**
 * @author Matti Savolainen
 * @version 13.4.2023
 *
 */
public class Peli {

    private String      testiLukema = "";
    
    private int         tunnusNro   = 0;
    private int         idNro;
    private String      peli        = "";
    
    private static int  seuraavaNro = 1;
    
    
    /** */
    public Peli() {
        // ei tehdä mitään
    }
    
    
    /**
     * Alustetaan tietyn kierrosajan peli.
     * @param idNro kierrosajan viitenumero
     */
    public Peli(int idNro) {
        this.idNro = idNro;
    }
    
    
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
     * Palautetaan mihin kierrosaikaan peli kuuluu
     * @return pelin id
     */
    public int getPeliNro() {
        return idNro;
    }
    
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Palauttaa pelin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return peli tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Peli peli = new Peli();
     * peli.parse("    5 |    1   |    Assetto Corsa  ");
     * peli.toString() === "5|1|Assetto Corsa";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + idNro + "|" + peli;
    }
    
    
    /**
     * Selvittää pelin tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta pelin tiedot otetaan
     * @example
     * <pre name="test">
     * Peli peli = new Peli();
     * peli.parse("    5 |    1   |    Assetto Corsa  ");
     * peli.getPeliNro() === 1;
     * peli.toString() === "5|1|Assetto Corsa";
     * 
     * peli.rekisteroi();
     * int n = peli.getTunnusNro();
     * peli.parse(""+(n+20));
     * peli.rekisteroi();
     * peli.getTunnusNro() === n+20+1;
     * peli.toString() === "" + (n+20+1) + "|1|Assetto Corsa";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        idNro = Mjonot.erota(sb, '|', idNro);
        peli = Mjonot.erota(sb, '|', peli);
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
     * @param nro viite kierrosaikaan, jonka pelistä on kyse
     */
    public void taytaPeliTiedot(int nro) {
        idNro = nro;
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
        
        acorsa.taytaPeliTiedot(1);
        automobilista.taytaPeliTiedot(2);
        
        acorsa.tulosta(System.out);
        automobilista.tulosta(System.out);
    }

}
