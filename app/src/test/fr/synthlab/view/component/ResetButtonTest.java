package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test on the JavaFX component ResetButton.
 */
public class ResetButtonTest {

    /**
     * Rule to test JavaFX Component.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Component tested.
     */
    private ResetButton resetButton;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        resetButton = new ResetButton();
    }

    /**
     * Check initialization.
     */
    @Test
    public void testInitialization() {
        assertTrue(resetButton.getStyleClass().contains("reset-button"));
    }
}