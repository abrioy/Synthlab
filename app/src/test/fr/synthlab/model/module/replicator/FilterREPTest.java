package fr.synthlab.model.module.replicator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test on the replicator filter.
 */
public class FilterREPTest {



    /**
     * Filter tested.
     */
    private FilterREP filterREP;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        filterREP = new FilterREPImpl();
    }

    /**
     * Test on input's getter
     */
    @Test
    public void testGetIn() {
        assertNotNull(filterREP.getIn());
    }

    /**
     * Test on output's getter
     */
    @Test
    public void testGetOut1() {
        assertNotNull(filterREP.getOut1());
    }

    /**
     * Test on output's getter
     */
    @Test
    public void testGetOut2() {
        assertNotNull(filterREP.getOut2());
    }

    /**
     * Test on output's getter
     */
    @Test
    public void testGetOut3() {
        assertNotNull(filterREP.getOut3());
    }

    /**
     * Test on generate.
     */
    @Test
    public void testGenerate() {
        filterREP.generate(0,1);

        assertEquals(0, filterREP.getOut1().getValue(), 0.00000001);
        assertEquals(0, filterREP.getOut2().getValue(), 0.00000001);
        assertEquals(0, filterREP.getOut3().getValue(), 0.00000001);
    }

    /**
     * Test on generate.
     */
    @Test
    public void testGenerate2() {
        filterREP.getIn().set(100.0);

        filterREP.generate(0,1);

        assertEquals(100.0, filterREP.getOut1().getValue(), 0.00000001);
        assertEquals(100.0, filterREP.getOut2().getValue(), 0.00000001);
        assertEquals(100.0, filterREP.getOut3().getValue(), 0.00000001);
    }
}