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
 * @version 24.3.2023
 *
 */
public class Kilparata {
    
    private int tunnusNro;
    private int idNro;
    private String kilparata;
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan kilparata. Toistaiseksi ei tarvitse tehdä mitään.
     */
    public Kilparata() {
        // Vielä ei tarvita mitään
    }
    
    
    /**
     * Alustetaan tietyn kierrosajan kilparata.
     * @param idNro kierrosajan viitenumero
     */
    public Kilparata(int idNro) {
        this.idNro = idNro;
    }
    
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Palauttaa kilparadan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kilparata tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Kilparata kilparata = new Kilparata();
     * kilparata.parse("    5 |    1   |    Nordschleife  ");
     * kilparata.toString() === "5|1|Nordschleife";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + idNro + "|" + kilparata;
    }
    
    
    /**
     * Selvittää kilparadan tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro;
     * @param rivi josta kilparadan tiedot otetaan
     * @example
     * <pre name="test">
     * Kilparata kilparata = new Kilparata();
     * kilparata.parse("    5 |    1   |    Nordschleife  ");
     * kilparata.getKierrosaikaNro() === 1;
     * kilparata.toString() === "5|1|Nordschleife";
     * 
     * kilparata.rekisteroi();
     * int n = kilparata.getTunnusNro();
     * kilparata.parse(""+(n+20));
     * kilparata.rekisteroi();
     * kilparata.getTunnusNro() === n+20+1;
     * kilparata.toString() === "" + (n+20+1) + "|1|Nordschleife";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        idNro = Mjonot.erota(sb, '|', idNro);
        kilparata = Mjonot.erota(sb, '|', kilparata);
    }
    
    
    /**
     * Antaa kilparadalle seuraavan rekisterinumeron.
     * @return kilparadan uusi tunnusnro
     * @example
     * <pre name="test">
     * Kilparata kilpa1 = new Kilparata();
     * kilpa1.getTunnusNro() === 0;
     * kilpa1.rekisteroi();
     * Kilparata kilpa2 = new Kilparata();
     * kilpa2.rekisteroi();
     * int n1 = kilpa1.getTunnusNro();
     * int n2 = kilpa2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * @return kierrosajan kilparata
     */
    public String getKilparata() {
        return this.kilparata;
    }
    

    /**
     * Luo uuden kilparadan käyttäjän antamalla nimellä
     * @param rataNimi Käyttäjän antama nimi uudelle radalla
     */
    public void luoUusiRata(String rataNimi) {
        Kilparata uusi = new Kilparata();
        uusi.rekisteroi();
        uusi.kilparata = rataNimi;
    }
    
    
    /**
     * Apumetodi, jolla täytetään testiarvot Kilparadalle.
     * Arvotaan satunnainen luku radan perään, jotta ei olisi samoja tietoja usealla
     * eri kierrosajalla.
     * @param nro viite kierrosaikaan, jonka kilparadasta on kyse
     */
    public void taytaKilparataTiedot(int nro) {
        idNro = nro;
        kilparata = "Nordschleife " + KierrosaikaTarkistus.rand(0, 999);
    }
    
    
    /**
     * Tulostetaan kilparadan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(kilparata);
    }
    
    
    /**
     * Tulostetaan kilparadan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Palautetaan kilparadan oma id
     * @return kilparadan id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palautetaan mihin kierrosaikaan kilparata kuuluu
     * @return kierrosajan id
     */
    public int getKierrosaikaNro() {
        return idNro;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kilparata kil = new Kilparata();
        kil.taytaKilparataTiedot(2);
        kil.tulosta(System.out);
    }
}
