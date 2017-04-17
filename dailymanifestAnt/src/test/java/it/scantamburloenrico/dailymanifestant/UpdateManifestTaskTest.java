package it.scantamburloenrico.dailymanifestant;

import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Enrico Scantamburlo <scantamburlo at streamsim.com>
 */
public class UpdateManifestTaskTest {

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

    @Test
    public void testIncreaseManifest() {
        System.out.println("updateManifest");
        {
            String original = "1.0.0";
            UpdateManifestTask instance = new UpdateManifestTask();
            String result = instance.increaseManifest(original);
            assertNotNull("null", result);
            String expResult = "1.0.1";
            assertEquals(expResult, result);
        }
        {
            String original = "2.0";
            UpdateManifestTask instance = new UpdateManifestTask();
            String result = instance.increaseManifest(original);
            assertNotNull("null", result);
            String expResult = "2.1";
            assertEquals(expResult, result);
        }
    }

}
