package laprecordtracker.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import laprecordtracker.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.13 19:49:13 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class PelitTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa33 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa33() throws SailoException {    // Pelit: 33
    Pelit pelit = new Pelit(); 
    Peli acorsa = new Peli(), acorsa2 = new Peli(); 
    assertEquals("From: Pelit line: 37", 0, pelit.getLkm()); 
    pelit.lisaa(acorsa); assertEquals("From: Pelit line: 38", 1, pelit.getLkm()); 
    pelit.lisaa(acorsa2); assertEquals("From: Pelit line: 39", 2, pelit.getLkm()); 
    pelit.lisaa(acorsa); assertEquals("From: Pelit line: 40", 3, pelit.getLkm()); 
    assertEquals("From: Pelit line: 41", acorsa, pelit.anna(0)); 
    assertEquals("From: Pelit line: 42", acorsa2, pelit.anna(1)); 
    assertEquals("From: Pelit line: 43", acorsa, pelit.anna(2)); 
    assertEquals("From: Pelit line: 44", false, pelit.anna(1) == acorsa); 
    assertEquals("From: Pelit line: 45", true, pelit.anna(1) == acorsa2); 
    pelit.lisaa(acorsa); assertEquals("From: Pelit line: 46", 4, pelit.getLkm()); 
    pelit.lisaa(acorsa); assertEquals("From: Pelit line: 47", 5, pelit.getLkm()); 
  } // Generated by ComTest END
}