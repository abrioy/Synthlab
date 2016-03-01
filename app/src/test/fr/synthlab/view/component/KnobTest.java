package fr.synthlab.view.component;

import fr.synthlab.util.JavaFXThreadingRule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

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

    /**
     * Initialize.
     */
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
     * test scaleType set to enum.
     */
    @Test
    public void testSetScaleType2() {
        knob.setScaleType("enum");
        assertEquals("enum",knob.getScaleType());
    }

    /**
     * test scaleType set to nothing.
     */
    @Test
    public void testSetScaleType3() {
        knob.setScaleType("");
        assertEquals("linear",knob.getScaleType());
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
     * test step set to -5.
     */
    @Test
    public void testSetStep2() {
        knob.setStep(-5);
        assertEquals(0,knob.getStep());
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

    /**
     * test speed init.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(Double.MAX_VALUE, knob.getSpeed(), 0.1);
    }

    /**
     * test set speed to 5.
     */
    @Test
    public void testSetSpeed() {
        knob.setSpeed(5.0);
        assertEquals(5.0, knob.getSpeed(), 0.1);
    }

    /**
     * test speed init property.
     */
    @Test
    public void testSpeedProperty() {
        DoubleProperty speedProperty = knob.speedProperty();
        assertEquals(knob, speedProperty.getBean());
        assertEquals("slow", speedProperty.getName());
        assertEquals(Double.MAX_VALUE, speedProperty.getValue(), 0.00001);
    }

    /**
     * test set stepType to true.
     */
    @Test
    public void testSetStepType() {
        knob.setStepType(true);
        assertTrue(knob.getStepType());
    }

    /**
     * test set stepType to false.
     */
    @Test
    public void testSetStepType2() {
        knob.setStepType(false);
        assertFalse(knob.getStepType());
    }

    /**
     * test set stepType to false.
     */
    @Test
    public void testSetStepType3() {
        knob.setStepType(true);
        knob.setStepType(false);
        assertFalse(knob.getStepType());
    }

    /**
     * test get stepType.
     */
    @Test
    public void testGetStepType() {
        assertFalse(knob.getStepType());
    }

    /**
     * test stepType property.
     */
    @Test
    public void testStepTypeProperty() {
        BooleanProperty stepTypeProperty = knob.stepTypeProperty();
        assertEquals(knob, stepTypeProperty.getBean());
        assertEquals("stepType", stepTypeProperty.getName());
        assertFalse(stepTypeProperty.getValue());
    }

    /**
     * test get minAngle.
     */
    @Test
    public void testGetMinAngle() {
        assertEquals(240, knob.getMinAngle(), 0.0001);
    }

    /**
     * test set minAngle to 120.
     */
    @Test
    public void testSetMinAngle() {
        knob.setMinAngle(120);
        assertEquals(120, knob.getMinAngle(), 0.0001);
    }

    /**
     * test set minAngle to -120.
     */
    @Test
    public void testSetMinAngle2() {
        knob.setMinAngle(-120);
        assertEquals(-120, knob.getMinAngle(), 0.0001);
    }

    /**
     * test minAngle property.
     */
    @Test
    public void testMinAngleProperty() {
        DoubleProperty angleProperty = knob.minAngleProperty();
        assertEquals(knob, angleProperty.getBean());
        assertEquals("minAngle", angleProperty.getName());
        assertEquals(240, angleProperty.getValue(),0.00001);
    }

    /**
     * test get maxAngle.
     */
    @Test
    public void testGetMaxAngle() {
        assertEquals(-60, knob.getMaxAngle(), 0.00000001);
    }

    /**
     * test set maxAngle to 30.
     */
    @Test
    public void testSetMaxAngle() {
        knob.setMaxAngle(30);
        assertEquals(30, knob.getMaxAngle(), 0.0001);
    }

    /**
     * test set maxAngle to -500.
     */
    @Test
    public void testSetMaxAngle2() {
        knob.setMaxAngle(-500);
        assertEquals(-500, knob.getMaxAngle(), 0.0001);
    }

    /**
     * test maxAngle property.
     */
    @Test
    public void testMaxAngleProperty() {
        DoubleProperty angleProperty = knob.maxAngleProperty();
        assertEquals(knob, angleProperty.getBean());
        assertEquals("maxAngle", angleProperty.getName());
        assertEquals(-60, angleProperty.getValue(),0.00001);
    }
}