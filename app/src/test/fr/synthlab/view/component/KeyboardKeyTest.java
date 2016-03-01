package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import javafx.beans.property.BooleanProperty;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test on the JavaFX component KeyboardKey.
 */
public class KeyboardKeyTest {

    /**
     * Rule to test JavaFX Component.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Component tested.
     */
    private KeyboardKey keyboardKey;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        keyboardKey = new KeyboardKey();
    }

    /**
     * test getIsWhiteKey method.
     */
    @Test
    public void testGetIsWhiteKey() {
        assertTrue(keyboardKey.getIsWhiteKey());
        assertEquals(Color.WHITE, keyboardKey.getFill());
        assertEquals(40.0, keyboardKey.getWidth(), 0.0000001);
        assertEquals(150.0, keyboardKey.getHeight(), 0.0000001);
    }

    /**
     * test setIsWhiteKey method.
     */
    @Test
    public void testSetIsWhiteKey() {
        keyboardKey.setIsWhiteKey(false);
        assertFalse(keyboardKey.getIsWhiteKey());
        assertEquals(Color.BLACK, keyboardKey.getFill());
        assertEquals(30.0, keyboardKey.getWidth(), 0.0000001);
        assertEquals(110.0, keyboardKey.getHeight(), 0.0000001);
    }

    /**
     * test isWhiteKeyProperty method.
     */
    @Test
    public void testIsWhiteKeyProperty() {
        BooleanProperty booleanProperty = keyboardKey.isWhiteKeyProperty();
        assertTrue(booleanProperty.getValue());
        assertEquals("isWhiteKey", booleanProperty.getName());
        assertSame(keyboardKey, booleanProperty.getBean());
    }

    /**
     * test isWhiteKeyProperty method.
     */
    @Test
    public void testIsWhiteKeyProperty2() {
        keyboardKey.setIsWhiteKey(false);

        BooleanProperty booleanProperty = keyboardKey.isWhiteKeyProperty();
        assertFalse(booleanProperty.getValue());
        assertEquals("isWhiteKey", booleanProperty.getName());
        assertSame(keyboardKey, booleanProperty.getBean());
    }
}