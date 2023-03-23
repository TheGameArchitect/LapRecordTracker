/**
 * 
 */
package laprecordtracker;

import java.io.PrintStream;

/**
 * @author Matruusi
 * @version 22.3.2023
 *
 */
public class Kierrosaika {

    
    /**
     * Tulostetaan kierrosaika
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Zonda, 6.12.412");
    }
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        // luento 13 kohdasta 18:00 eteenpäin
        Kierrosaika zonda = new Kierrosaika();
        Kierrosaika zonda2 = new Kierrosaika();
        
        //zonda.rekisteroi();
        //zonda2.rekisteroi();
        
        zonda.tulosta(System.out);
        
        //zonda.vastaaPaganiZonda();
        //zonda2.vastaaPaganiZonda();
        
        zonda.tulosta(System.out);
        zonda2.tulosta(System.out);
    }

}
