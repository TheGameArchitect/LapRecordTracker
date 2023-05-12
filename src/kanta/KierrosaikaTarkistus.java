package kanta;
import static kanta.SisaltaaTarkistaja.*;

/**
 * @author Matti Savolainen
 * @version 23.3.2023
 *
 */
public class KierrosaikaTarkistus {
    
    /**
     * Tarkistetaan kierrosajan muotoilu.
     * Oikeita muotoja on kaksi:
     *  00:00:000 TAI 00.00.000
     * @param aika kierrosaika joka tutkitaan
     * @return null jos oikein, muuten virhettä kuvaava teksti
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * KierrosaikaTarkistus ajat = new KierrosaikaTarkistus();
     * ajat.tarkista("32") === "Aika on liian lyhyt. Kirjoita aika muodossa 00.00.000 tai 00:00:000.";
     * ajat.tarkista("d") === "Aika on liian lyhyt. Kirjoita aika muodossa 00.00.000 tai 00:00:000.";
     * ajat.tarkista("123:00:000") === "Liian monta merkkiä";
     * ajat.tarkista("00:s2.323") === "Saa sisältää vain numeroita, pisteitä ja kaksoispisteitä";
     * ajat.tarkista("84:00:000") === "Minuutit eivät saa mennä yli 59";
     * ajat.tarkista("00.80.323") === "Sekunnit eivät voi mennä yli 59";
     * ajat.tarkista("00:80:323") === "Sekunnit eivät voi mennä yli 59";
     * ajat.tarkista("000000000") === "Väärä erotinmerkki";
     * ajat.tarkista("00:00:000") === null;
     * ajat.tarkista("00.00.000") === null;
     * ajat.tarkista("59.59.999") === null;
     * ajat.tarkista("32:45:423") === null;
     * </pre>
     */
    public String tarkista(String aika) {
        int pituus = aika.length();
        if (pituus < 9) return "Aika on liian lyhyt. Kirjoita aika muodossa 00.00.000 tai 00:00:000.";
        if (pituus > 9) return "Liian monta merkkiä";
        if (!onkoVain(aika, NUMEROTJAEROTTIMET)) return "Saa sisältää vain numeroita, pisteitä ja kaksoispisteitä";
        int minuutit = Integer.parseInt(aika.substring(0,2));
        int sekunnit = Integer.parseInt(aika.substring(3,5));
        int sadasosat = Integer.parseInt(aika.substring(7,9));
        if (minuutit < 0) return "Minuutit eivät saa olla negatiivia";
        if (sekunnit < 0) return "Sekunnit eivät saa olla negatiivia";
        if (sadasosat < 0) return "Sadasosat eivät saa olla negatiivia";
        if (minuutit > 59) return "Minuutit eivät saa mennä yli 59";
        if (sekunnit > 59) return "Sekunnit eivät voi mennä yli 59";
        if (sadasosat > 999) return "Sadasosat eivät voi mennä yli 999";
        if (onkoVain(aika, NUMEROT)) return "Väärä erotinmerkki";
        return null;
    }
    
    
    /**
     * Arpoo satunnaisen luvun halutulta väliltä
     * @param ala alin raja
     * @param yla ylin raja
     * @return satunnainen luku ala-yla -väliltä
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    
    /**
     * Arvotaan satunnainen kierrosaika, jonka muotoilu on oikeanlainen.
     * @return satunnainen kierrosaika, oikein formatoituna
     */
    public static String arvoKierrosaika() {
        String apuKierros = String.format("%02d",rand(0,60)) + "." +
        String.format("%02d",rand(0,59)) + "." +
        String.format("%03d",rand(0,999));
        return apuKierros;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        //
    }
}
