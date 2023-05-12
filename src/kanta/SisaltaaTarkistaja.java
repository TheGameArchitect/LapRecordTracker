/**
 * 
 */
package kanta;

/**
 * Tarkistaja joka tarkistaa että jono sisältää vain valittuja merkkejä.
 * @author Matti Savolainen
 * @version 12.5.2023
 *
 */
public class SisaltaaTarkistaja {

    
    /** Numeroita ja erottimia vastaavat kirjaimet */
    public static final String NUMEROTJAEROTTIMET = "0123456789:.";
    
    /** Numeroita vastaavat kirjaimet */
    public static final String NUMEROT = "0123456789";
    
    
    /**
     * Onko jonossa vain sallittuja merkkejä
     * @param jono tutkittava jono
     * @param sallitut merkit joita sallitaan
     * @return true jos vain sallittuja, false muuten
     * @example
     * <pre name="test">
     * onkoVain("123", "12") === false;
     * onkoVain("123, "1234") === true;
     * onkoVain("", "1234") === true;
     * </pre>
     */
    public static boolean onkoVain(String jono, String sallitut) {
        for (int i = 0; i < jono.length(); i++) {
            if (sallitut.indexOf(jono.charAt(i)) < 0) return false;
        }
        return true;
    }
    
    
    private final String sallitut;
    
    
    /**
     * Luodaan tarkistaja joka hyväksyy sallitut merkit
     * @param sallitut hyväksyttävät merkit
     */
    public SisaltaaTarkistaja(String sallitut) {
        this.sallitut = sallitut;
    }
    
    
    /**
     * @param jono tutkittava jono
     * @return null jos vain valittuja merkkejä, muuten virheilmoitus
     * @example
     * <pre name="test">
     * SisaltaaTarkistaja tar = new SisaltaaTarkistaja("123");
     * tar.tarkista("12") === null;
     * tar.tarkista("14") === "Saa olla vain merkkejä: 123";
     * tar.tarkista("") === null;
     * </pre>
     */
    public String tarkista(String jono) {
        if (onkoVain(jono, sallitut)) return null;
        return "Saa olla vain merkkejä: " + sallitut;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    }

}
