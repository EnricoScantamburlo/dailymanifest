package it.scantamburloenrico.dailymanifestant;

import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Enrico Scantamburlo <scantamburlo at streamsim.com>
 */
public class UpdateManifestTaskTest {

    public UpdateManifestTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of updateManifest method, of class UpdateManifestTask.
     */
    @Test
    public void testUpdateManifest() {
        System.out.println("updateManifest");
        String original = "1.2.20170101151230";
        LocalDateTime now = LocalDateTime.of(2017, 3, 5, 23, 12, 34);
        UpdateManifestTask instance = new UpdateManifestTask();
        String expResult = "1.2.2017" + "03" + "05" + "23" + "12" + "34";
        String result = instance.updateManifest(original, now);
        assertNotNull("null", result);
        assertEquals(expResult, result);
    }

}
