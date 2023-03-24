package laprecordtracker.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Matti Savolainen
 * @version 24.3.2023
 *  Kaikkien testien ajaminen
 */
@Suite
@SelectClasses({ KierrosaikaTest.class, KierrosajatTest.class,
        KilparadatTest.class, KilparataTest.class, LapRecordTrackerTest.class })
public class AllTests {
    // Ajetaan kaikki testit
}
