package kanta;

/**
 * @author Matruusi
 * @version 23.3.2023
 *
 */
public class KierrosaikaTarkistus {
    
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
