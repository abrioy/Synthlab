package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test on Knob
 * @see Knob
 * @author johan
 */
public class KnobTest {

    /**
     * Rule to test JavaFX Thread.
     */
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * knob tested
     */
    private Knob knob;

    @Before
    public void setUp(){
        knob = new Knob();
    }

    /**
     * test on init value
     */
    @Test
    public void testGetValue() {
        assertEquals(0,knob.getValue(),0.00000001);
    }

    /**
     * test on value 5
     */
    @Test
    public void testSetValue() {
        knob.setValue(5);
        assertEquals(5,knob.getValue(),0.00000001);
    }

    /**
     * test init value
     */
    @Test
    public void testValueProperty() {
        DoubleProperty v = knob.valueProperty();
        assertEquals(0,v.get(),0.00000001);
    }

    /**
     * test after change value
     */
    @Test
    public void testValueProperty2()  {
        knob.setValue(5);
        DoubleProperty v = knob.valueProperty();
        assertEquals(5,v.get(),0.00000001);
    }

    /**
     * test init min
     */
    @Test
    public void testGetMin() {
        assertEquals(0,knob.getMin(),0.00000001);
    }

    /**
     * test change min by 5.
     */
    @Test
    public void testSetMin() {
        knob.setMin(5);
        assertEquals(5,knob.getMin(),0.00000001);
    }

    /**
     * test init min property
     */
    @Test
    public void testMinProperty() {
        DoubleProperty property = knob.minProperty();
        assertEquals(0,property.get(),0.00000001);
    }

    /**
     * test init
     */
    @Test
    public void testGetMax(){
        assertEquals(100,knob.getMax(),0.00000001);
    }

    /**
     * test max set to 5.
     */
    @Test
    public void testSetMax(){
        knob.setMax(5);
        assertEquals(5,knob.getMax(),0.00000001);
    }


    /**
     * test max init property.
     */
    @Test
    public void testMaxProperty() {
        DoubleProperty property = knob.maxProperty();
        assertEquals(100,property.get(),0.00000001);
    }


    /**
     * test Diameter init.
     */
    @Test
    public void testGetDiameter() {
        assertEquals(200,knob.getDiameter(),0.00000001);
    }


    /**
     * test diameter set to 5.
     */
    @Test
    public void testSetDiameter() {
        knob.setDiameter(5);
        assertEquals(5,knob.getDiameter(),0.00000001);
    }

    /**
     * test diameter init property.
     */
    @Test
    public void testDiameterProperty() {
        DoubleProperty property = knob.diameterProperty();
        assertEquals(200,property.get(),0.00000001);
    }


    /**
     * test scaleType set to log.
     */
    @Test
    public void testSetScaleType() {
        knob.setScaleType("log");
        assertEquals("log",knob.getScaleType());
    }

    /**
     * test scaleType set to log.
     */
    @Test
    public void testGetScaleType() {
        assertEquals("linear",knob.getScaleType());
    }

    /**
     * test scaleType init property.
     */
    @Test
    public void testScaleTypeProperty() {
        StringProperty property = knob.scaleTypeProperty();
        assertEquals("linear",property.get());
    }

    /**
     * test label set to name.
     */
    @Test
    public void testSetLabel() {
        knob.setLabel("name");
        assertEquals("name",knob.getLabel());
    }

    /**
     * test label init.
     */
    @Test
    public void testGetLabel() {
        assertEquals("",knob.getLabel());
    }

    /**
     * test label set to log.
     */
    @Test
    public void testLabelProperty() {

        DoubleProperty property = knob.minProperty();
        assertEquals(0,property.get(),0.00000001);
    }

    /**
     * test step set to 5.
     */
    @Test
    public void testSetStep() {
        knob.setStep(5);
        assertEquals(5,knob.getStep());
    }

    /**
     * test step init.
     */
    @Test
    public void testGetStep() {
        assertEquals(20,knob.getStep());
    }

    /**
     * test step set to 5.
     */
    @Test
    public void testStepProperty() {
        IntegerProperty property = knob.stepProperty();
        assertEquals(20,property.get());
    }
}