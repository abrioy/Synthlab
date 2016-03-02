package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import fr.synthlab.view.controller.Workbench;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test on the JavaFX component Cable.
 */
public class CableTest {

    /**
     * Rule to test JavaFX Component.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Mock workbench to initialize the cable.
     */
    private Workbench workbench;

    /**
     * Mock list to test the cable.
     */
    private ObservableList observableList;

    /**
     * Plug to initialize the cable.
     */
    private Plug plug;

    /**
     * Cable tested.
     */
    private Cable cable;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        workbench = mock(Workbench.class);
        observableList = mock(ObservableList.class);
        when(workbench.getChildren()).thenReturn(observableList);
        plug = new Plug();
        cable = new Cable(workbench, plug);
    }

    /**
     * test get opposite plug.
     */
    @Test
    public void testGetOppositePlug() {
        assertEquals(Optional.empty(), cable.getOppositePlug(plug));
    }

    /**
     * test get opposite plug.
     */
    @Test
    public void testGetOppositePlug2() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        cable.unplug(plug);
        assertEquals(Optional.empty(), cable.getOppositePlug(newPlug));
    }

    /**
     * test get opposite plug.
     */
    @Test
    public void testGetOppositePlug3() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        assertEquals(plug, cable.getOppositePlug(newPlug).get());
    }

    /**
     * test get opposite plug.
     */
    @Test
    public void testGetOppositePlug4() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        assertEquals(newPlug, cable.getOppositePlug(plug).get());
    }

    /**
     * test get opposite plug.
     */
    @Test
    public void testGetOppositePlug5() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        Plug otherPlug = new Plug();

        assertEquals(Optional.empty(), cable.getOppositePlug(otherPlug));
    }

    /**
     * test get plugged plug.
     */
    @Test
    public void testGetPluggedPlug() {
        assertSame(plug, cable.getPluggedPlug());
    }

    /**
     * test get plugged plug when two plug are connected.
     */
    @Test
    public void testGetPluggedPlug2() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        assertSame(plug, cable.getPluggedPlug());
        assertSame(newPlug, cable.getOppositePlug(plug).get());
    }

    /**
     * test get plugged plug.
     */
    @Test
    public void testGetPluggedPlug3() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        cable.unplug(plug);
        assertSame(newPlug, cable.getPluggedPlug());
    }

    /**
     * test set empty plug.
     */
    @Test
    public void testSetEmptyPlug() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        assertSame(newPlug, cable.getOppositePlug(plug).get());
    }

    /**
     * test unplug.
     */
    @Test
    public void testUnplug() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        cable.unplug(plug);
        assertSame(newPlug, cable.getPluggedPlug());
    }

    /**
     * test unplug.
     */
    @Test
    public void testUnplug2() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);
        cable.unplug(newPlug);
        assertSame(plug, cable.getPluggedPlug());
    }

    /**
     * test unplug.
     */
    @Test
    public void testUnplug3() {
        Plug newPlug = new Plug();
        cable.setEmptyPlug(newPlug);

        Plug otherPlug = new Plug();

        cable.unplug(otherPlug);
        assertSame(plug, cable.getPluggedPlug());
        assertSame(newPlug, cable.getOppositePlug(plug).get());
    }

    /**
     * test delete circle.
     */
    @Test
    public void testDeleteCircle() {
        cable.deleteCircles();
        verify(observableList, times(2)).remove(any(Circle.class));
    }

    /**
     * test set color.
     */
    @Test
    public void testSetColor() {
        cable.setColor(Color.WHITE);
        assertEquals(Color.WHITE, cable.getColor());
    }

    /**
     * test get color.
     */
    @Test
    public void testGetColor() {
        assertEquals(Color.BLACK, cable.getColor());
    }
}