/**
 * 
 */
package laprecordtracker;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Matruusi
 * @version 22.3.2023
 *
 */
public class Kierrosaika {

    private int     tunnusNro   = 0;
    private String  kierrosaika = "";
    private String  auto        = "";
    private String  renkaat     = "";
    private String  keli        = "";
    private String  ajoavut     = "";
    private String  kommentit   = "";
    
    /**
     * Tulostetaan kierrosajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + auto);
        out.println("Kierrosaika: " + kierrosaika);
        out.println("Renkaat: " + renkaat);
        out.println("Ajokeli: " + keli);
        out.println("Ajoavut: " + ajoavut);
        out.println("Kommentit: " + kommentit);
    }
    
    
    /**
     * Tulostetaan kierrosajan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
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
