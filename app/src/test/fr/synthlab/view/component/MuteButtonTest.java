package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test on the JavaFX component MuteButton.
 */
public class MuteButtonTest {

    /**
     * Rule to test JavaFX Component.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Component tested.
     */
    private MuteButton muteButton;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        muteButton = new MuteButton();
    }

    /**
     * Check if the button is release.
     */
    @Test
    public void testInitialize() {
        assertTrue(muteButton.getStyleClass().contains("mute-release"));
        assertFalse(muteButton.getStyleClass().contains("mute-pressed"));
    }

    /**
     * Test if the button turn to pressed.
     */
    @Test
    public void testSetToggle() {
        muteButton.setToggle(true);
        assertTrue(muteButton.getStyleClass().contains("mute-pressed"));
        assertFalse(muteButton.getStyleClass().contains("mute-release"));
    }

    /**
     * Test if the button turn to release when it is pressed.
     */
    @Test
    public void testSetToggle2() {
        muteButton.setToggle(true);
        muteButton.setToggle(false);
        assertEquals(1, muteButton.getStyleClass().size());
        assertFalse(muteButton.getStyleClass().contains("mute-pressed"));
        assertTrue(muteButton.getStyleClass().contains("mute-release"));
    }

    /**
     * Test if the button rest released.
     */
    @Test
    public void testSetToggle3() {
        muteButton.setToggle(false);
        assertEquals(1, muteButton.getStyleClass().size());
        assertTrue(muteButton.getStyleClass().contains("mute-release"));
        assertFalse(muteButton.getStyleClass().contains("mute-pressed"));
    }

    /**
     * Test if the button turn to pressed.
     */
    @Test
    public void testSetToggle4() {
        muteButton.setToggle(false);
        muteButton.setToggle(true);
        assertEquals(1, muteButton.getStyleClass().size());
        assertTrue(muteButton.getStyleClass().contains("mute-pressed"));
        assertFalse(muteButton.getStyleClass().contains("mute-release"));
    }

    /**
     * Test if two method calls with a "true" in parameter change nothing.
     */
    @Test
    public void testSetToggle5() {
        muteButton.setToggle(true);
        muteButton.setToggle(true);
        assertEquals(1, muteButton.getStyleClass().size());
        assertTrue(muteButton.getStyleClass().contains("mute-pressed"));
        assertFalse(muteButton.getStyleClass().contains("mute-release"));
    }

    /**
     * Test if two method calls with a "false" in parameter change nothing.
     */
    @Test
    public void testSetToggle6() {
        muteButton.setToggle(false);
        muteButton.setToggle(false);
        assertEquals(1, muteButton.getStyleClass().size());
        assertFalse(muteButton.getStyleClass().contains("mute-pressed"));
        assertTrue(muteButton.getStyleClass().contains("mute-release"));
    }
}