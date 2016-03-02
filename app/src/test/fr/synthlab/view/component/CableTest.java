package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import fr.synthlab.view.controller.Workbench;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
     * test get plugged plug.
     */
    @Test
    public void testGetPluggedPlug() {
        assertSame(plug, cable.getConnectedPlug());
    }

    /**
     * test get plugged plug when two plug are connected.
     */
    @Test
    public void testGetPluggedPlug2() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        assertSame(plug, cable.getConnectedPlug());
        assertSame(newPlug, cable.getOppositePlug(plug));
    }

    /**
     * test get plugged plug.
     */
    @Test
    public void testGetPluggedPlug3() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        cable.disconnectPlug(plug);
        assertSame(newPlug, cable.getConnectedPlug());
    }

    /**
     * test set empty plug.
     */
    @Test
    public void testSetEmptyPlug() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        assertSame(newPlug, cable.getOppositePlug(plug));
    }

    /**
     * test set empty plug.
     */
    @Test
    public void testSetEmptyPlug2() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        cable.disconnectPlug(plug);

        Plug otherPlug = new Plug();

        cable.connectPlug(otherPlug);

        assertSame(otherPlug, cable.getOppositePlug(newPlug));
        assertSame(newPlug, cable.getOppositePlug(otherPlug));
    }

    /**
     * test set empty plug.
     */
    @Test
    public void testSetEmptyPlug3() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);

        Plug otherPlug = new Plug();
        cable.connectPlug(otherPlug);

        assertSame(newPlug, cable.getOppositePlug(plug));
        assertSame(plug, cable.getOppositePlug(newPlug));
    }

    /**
     * test disconnectPlug.
     */
    @Test
    public void testUnplug() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        cable.disconnectPlug(plug);
        assertSame(newPlug, cable.getConnectedPlug());
    }

    /**
     * test disconnectPlug.
     */
    @Test
    public void testUnplug2() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);
        cable.disconnectPlug(newPlug);
        assertSame(plug, cable.getConnectedPlug());
    }

    /**
     * test disconnectPlug.
     */
    @Test
    public void testUnplug3() {
        Plug newPlug = new Plug();
        cable.connectPlug(newPlug);

        Plug otherPlug = new Plug();

        cable.disconnectPlug(otherPlug);
        assertSame(plug, cable.getConnectedPlug());
        assertSame(newPlug, cable.getOppositePlug(plug));
    }

    /**
     * test delete circle.
     */
    @Test
    public void testDeleteCircle() {
        cable.dispose();
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