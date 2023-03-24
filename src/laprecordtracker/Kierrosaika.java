/**
 * 
 */
package laprecordtracker;

import java.io.OutputStream;
import java.io.PrintStream;

import kanta.KierrosaikaTarkistus;

/**
 * @author Matti Savolainen
 * @version 22.3.2023
 *
 */
public class Kierrosaika {

    private int         tunnusNro   = 0;
    private String      kierrosaika = "";
    private String      auto        = "";
    private String      renkaat     = "";
    private String      keli        = "";
    private String      ajoavut     = "";
    private String      kommentit   = "";
    
    private static int  seuraavaNro = 1;
    
    /**
     * Tulostetaan kierrosajan tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + auto);
        out.println("Kierrosaika: " + kierrosaika);
        out.println("Renkaat: " + renkaat);
        out.println("Ajokeli: " + keli);
        out.println("Ajoavut: " + ajoavut);
        out.println("Kommentit: " + kommentit);
    }
    
    
    /**
     * Tulostetaan kierrosajan tiedot.
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Palauttaa kierrosajan tunnusnumeron.
     * @return kierrosajan tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Antaa uudelle kierrosajalle seuraavan rekisterinumeron.
     * @return kierrosajan uusi tunnusNro
     * @example
     * <pre name="test">
     *  Kierrosaika zonda1 = new Kierrosaika();
     *  zonda1.getTunnusNro() === 0;
     *  zonda1.rekisteroi();
     *  Kierrosaika zonda2 = new Kierrosaika();
     *  zonda2.rekisteroi();
     *  int n1 = zonda1.getTunnusNro();
     *  int n2 = zonda2.getTunnusNro();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kierrosajalle.
     * TODO: poista kun kaikki toimii
     */
    public void taytaKierrosajanTiedot() {
        kierrosaika = KierrosaikaTarkistus.arvoKierrosaika();
        auto = "Pagani Zonda R";
        renkaat = "Medium";
        keli = "Puolipilvinen";
        ajoavut = "ABS" + ", " + "TCS: " + KierrosaikaTarkistus.rand(0, 10);
        kommentit = "Epävakaa yli 200km/h vauhdissa.";
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kierrosaika zonda = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();
        
        zonda.rekisteroi();
        zonda2.rekisteroi();
        
        zonda.tulosta(System.out);
        
        zonda.taytaKierrosajanTiedot();
        zonda2.taytaKierrosajanTiedot();
        
        zonda.tulosta(System.out);
        zonda2.tulosta(System.out);
    }

}
