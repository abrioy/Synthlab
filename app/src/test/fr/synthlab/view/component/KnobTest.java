package fr.synthlab.view.component;

import javafx.beans.property.DoubleProperty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test on Knob
 * @see Knob
 * @author johan
 */
public class KnobTest {

    private Knob knob;

    @Before
    public void setUp(){
        knob = new Knob();
    }

    /**
     * test on init value
     * @throws Exception
     */
    @Test
    public void testGetValue() throws Exception {
        assertEquals(0,knob.getValue(),0.00000001);
    }

    /**
     * test on value 5
     * @throws Exception
     */
    @Test
    public void testSetValue() throws Exception {
        knob.setValue(5);
        assertEquals(5,knob.getValue(),0.00000001);
    }

    /**
     * test init value
     * @throws Exception
     */
    @Test
    public void testValueProperty() throws Exception {
        DoubleProperty v = knob.valueProperty();
        assertEquals(0,v.get(),0.00000001);
    }

    /**
     * test after change value
     * @throws Exception
     */
    @Test
    public void testValueProperty2() throws Exception {
        knob.setValue(5);
        DoubleProperty v = knob.valueProperty();
        assertEquals(5,v.get(),0.00000001);
    }
    /**
     * test init min
     * @throws Exception
     */
    @Test
    public void testGetMin() throws Exception {
        assertEquals(0,knob.getMin(),0.00000001);
    }

    /**
     * test change min by 5.
     * @throws Exception
     */
    @Test
    public void testSetMin() throws Exception {
        knob.setMin(5);
        assertEquals(5,knob.getMin(),0.00000001);
    }

    /**
     * test init min property
     * @throws Exception
     */
    @Test
    public void testMinProperty() throws Exception {
        DoubleProperty property = knob.minProperty();
        assertEquals(0,property.get(),0.00000001);
    }

    /**
     * test init
     * @throws Exception
     */
    @Test
    public void testGetMax() throws Exception {

    }

    @Test
    public void testSetMax() throws Exception {

    }

    @Test
    public void testMaxProperty() throws Exception {

    }

    @Test
    public void testGetDiameter() throws Exception {

    }

    @Test
    public void testSetDiameter() throws Exception {

    }

    @Test
    public void testDiameterProperty() throws Exception {

    }

    @Test
    public void testSetScaleType() throws Exception {

    }

    @Test
    public void testGetScaleType() throws Exception {

    }

    @Test
    public void testScaleTypeProperty() throws Exception {

    }

    @Test
    public void testSetLabel() throws Exception {

    }

    @Test
    public void testGetLabel() throws Exception {

    }

    @Test
    public void testLabelProperty() throws Exception {

    }

    @Test
    public void testSetStep() throws Exception {

    }

    @Test
    public void testGetStep() throws Exception {

    }

    @Test
    public void testStepProperty() throws Exception {

    }
}