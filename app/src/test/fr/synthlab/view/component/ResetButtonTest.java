package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
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
     * Check initialization.
     */
    @Test
    public void testInitialization() {
        resetButton = new ResetButton();
        assertTrue(resetButton.getStyleClass().contains("reset-button"));
    }
}