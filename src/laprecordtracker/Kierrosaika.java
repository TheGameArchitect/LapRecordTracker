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
        //out.println("Simulaattori: ");
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
     * @return kierrosaika
     */
    public String getKierrosaika() {
        return this.kierrosaika;
    }
    
    
    /**
     * @return kierrosajan ajoavut
     */
    public String getAjoavut() {
        return this.ajoavut;
    }


    /**
     * @return kierrosajan auto
     */
    public String getAuto() {
        return this.auto;
    }


    /**
     * @return kierrosajan keli
     */
    public String getKeli() {
        return this.keli;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että seuraava numero on aina
     * suurempi kuin tähän mennessä suurin
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Selvittää kierrosajan tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta kierrosajan tiedot otetaan
     * @example
     * <pre name="test">
     * Kierrosaika kierrosaika = new Kierrosaika();
     * kierrosaika.parse("    4 |  Nissan GT-R     | 02.25.733 ");
     * kierrosaika.getTunnusNro() === 4;
     * kierrosaika.toString().startsWith("4|Nissan GT-R|02.25.733|") === true;
     * 
     * kierrosaika.rekisteroi();
     * int n = kierrosaika.getTunnusNro();
     * kierrosaika.parse(""+(n+20));
     * kierrosaika.rekisteroi();
     * kierrosaika.getTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        auto = Mjonot.erota(sb, '|', auto);
        kierrosaika = Mjonot.erota(sb, '|', kierrosaika);
        renkaat = Mjonot.erota(sb, '|', renkaat);
        keli = Mjonot.erota(sb, '|', keli);
        ajoavut = Mjonot.erota(sb, '|', ajoavut);
        kommentit = Mjonot.erota(sb, '|', kommentit);
    }
    
    
    /**
     * Palauttaa kierrosajan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kierrosaika tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Kierrosaika kierrosaika = new Kierrosaika();
     *  kierrosaika.parse("    2  |   Ford Escort    | 2.43.523");
     *  kierrosaika.toString().startsWith("2|Ford Escort|2.43.523") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                auto + "|" +
                kierrosaika + "|" +
                renkaat + "|" +
                keli + "|" +
                ajoavut + "|" +
                kommentit + "|";
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
        kommentit = "Epavakaa yli 200km/h vauhdissa.";
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
