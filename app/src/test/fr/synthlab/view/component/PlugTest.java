package fr.synthlab.view.component;

import fr.synthlab.model.module.port.Port;
import fr.synthlab.util.JavaFXThreadingRule;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test on the JavaFX component Plug.
 */
public class PlugTest {

    /**
     * Rule to test JavaFX Thread.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Plug tested.
     */
    private Plug plug;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        plug = new Plug();
    }

    /**
     * test get center. This is the center of the plug.
     */
    @Test
    public void testGetCenter() {
        double d = plug.getLayoutX();
        double d2 = plug.getLayoutY();
        Point2D point2D = new Point2D(d, d2);
        assertEquals(point2D, plug.getCenter());
    }

    /**
     * test get port.
     */
    @Test
    public void testGetPort() {
        assertNull(plug.getPort());
    }

    /**
     * test set get port command.
     * @Exception call exception
     */
    @Test
    public void testSetGetPortCommand() throws Exception {
        Callable callable = mock(Callable.class);
        Port port = mock(Port.class);
        plug.setGetPortCommand(callable);
        when(callable.call()).thenReturn(port);

        assertSame(port, plug.getPort());
    }

    /**
     * test get type.
     */
    @Test
    public void testGetType() {
        assertEquals("other", plug.getType());
    }

    /**
     * test set type to input type.
     */
    @Test
    public void testSetType1() {
        plug.setType("input");

        assertEquals("input", plug.getType());
        Circle c;
        for(Node n : plug.getChildren()){
            if(n instanceof Circle){
                c = (Circle) n;
                assertEquals(Color.LIGHTBLUE, c.getStroke());
                return;
            }
        }
    }

    /**
     * test set type to output type.
     */
    @Test
    public void testSetType2() {
        plug.setType("output");

        assertEquals("output", plug.getType());
        Circle c;
        for(Node n : plug.getChildren()){
            if(n instanceof Circle){
                c = (Circle) n;
                assertEquals(Color.ORANGE, c.getStroke());
                return;
            }
        }
    }

    /**
     * test set type to modulation type.
     */
    @Test
    public void testSetType3() {
        plug.setType("modulation");

        assertEquals("modulation", plug.getType());
        Circle c;
        for(Node n : plug.getChildren()){
            if(n instanceof Circle){
                c = (Circle) n;
                assertEquals(Color.HOTPINK, c.getStroke());
                return;
            }
        }
    }

    /**
     * test set type to other type.
     */
    @Test
    public void testSetType4() {
        plug.setType("test");

        assertEquals("test", plug.getType());
        Circle c;
        for(Node n : plug.getChildren()){
            if(n instanceof Circle){
                c = (Circle) n;
                assertEquals(Color.WHITE, c.getStroke());
                return;
            }
        }
    }

    /**
     * test type property.
     */
    @Test
    public void testTypeProperty() {
        StringProperty stringProperty = plug.typeProperty();
        assertEquals(plug, stringProperty.getBean());
        assertEquals("type", stringProperty.getName());
        assertEquals("other", stringProperty.getValue());
    }

    /**
     * test get name.
     */
    @Test
    public void testGetName() {
        assertEquals("", plug.getName());
    }

    /**
     * test name property.
     */
    @Test
    public void testNameProperty() {
        StringProperty stringProperty = plug.nameProperty();
        assertEquals(plug, stringProperty.getBean());
        assertEquals("name", stringProperty.getName());
        assertEquals("", stringProperty.getValue());
    }

    /**
     * test set name.
     */
    @Test
    public void testSetName() {
        plug.setName("test");
        assertEquals("test", plug.getName());
    }

    /**
     * test get label.
     */
    @Test
    public void testGetLabel() {
        assertEquals("", plug.getLabel());
    }

    /**
     * test label property.
     */
    @Test
    public void testLabelProperty() {
        StringProperty stringProperty = plug.labelProperty();
        assertEquals(plug, stringProperty.getBean());
        assertEquals("label", stringProperty.getName());
        assertEquals("", stringProperty.getValue());
    }

    /**
     * test set label.
     */
    @Test
    public void testSetLabel() {
        plug.setLabel("test");
        assertEquals("test", plug.getLabel());
    }

    /**
     * test get cable.
     */
    @Test
    public void testGetCable() {
        assertNull(plug.getCable());
    }

    /**
     * test set cable.
     */
    @Test
    public void testSetCable() {
        Cable cable = mock(Cable.class);
        plug.setCable(cable);
        assertSame(cable, plug.getCable());
    }
}