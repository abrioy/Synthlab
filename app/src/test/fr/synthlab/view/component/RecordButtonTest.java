package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test on the JavaFX component RecordButton.
 */
public class RecordButtonTest {

    /**
     * Rule to test JavaFX Component.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Component tested.
     */
    private RecordButton recordButton;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        recordButton = new RecordButton();
    }

    /**
     * Check if the button is release.
     */
    @Test
    public void testInitialize() {
        assertTrue(recordButton.getStyleClass().contains("record-release"));
        assertFalse(recordButton.getStyleClass().contains("record-pressed"));
    }

    /**
     * Test if the button turn to pressed.
     */
    @Test
    public void testSetToggle() {
        recordButton.setToggle(true);
        assertTrue(recordButton.getStyleClass().contains("record-pressed"));
        assertFalse(recordButton.getStyleClass().contains("record-release"));
    }

    /**
     * Test if the button turn to release when it is pressed.
     */
    @Test
    public void testSetToggle2() {
        recordButton.setToggle(true);
        recordButton.setToggle(false);
        assertEquals(1, recordButton.getStyleClass().size());
        assertFalse(recordButton.getStyleClass().contains("record-pressed"));
        assertTrue(recordButton.getStyleClass().contains("record-release"));
    }

    /**
     * Test if the button rest released.
     */
    @Test
    public void testSetToggle3() {
        recordButton.setToggle(false);
        assertEquals(1, recordButton.getStyleClass().size());
        assertTrue(recordButton.getStyleClass().contains("record-release"));
        assertFalse(recordButton.getStyleClass().contains("record-pressed"));
    }

    /**
     * Test if the button turn to pressed.
     */
    @Test
    public void testSetToggle4() {
        recordButton.setToggle(false);
        recordButton.setToggle(true);
        assertEquals(1, recordButton.getStyleClass().size());
        assertTrue(recordButton.getStyleClass().contains("record-pressed"));
        assertFalse(recordButton.getStyleClass().contains("record-release"));
    }

    /**
     * Test if two method calls with a "true" in parameter change nothing.
     */
    @Test
    public void testSetToggle5() {
        recordButton.setToggle(true);
        recordButton.setToggle(true);
        assertEquals(1, recordButton.getStyleClass().size());
        assertTrue(recordButton.getStyleClass().contains("record-pressed"));
        assertFalse(recordButton.getStyleClass().contains("record-release"));
    }

    /**
     * Test if two method calls with a "false" in parameter change nothing.
     */
    @Test
    public void testSetToggle6() {
        recordButton.setToggle(false);
        recordButton.setToggle(false);
        assertEquals(1, recordButton.getStyleClass().size());
        assertFalse(recordButton.getStyleClass().contains("record-pressed"));
        assertTrue(recordButton.getStyleClass().contains("record-release"));
    }
}