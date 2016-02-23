package fr.synthlab.model.module.replicator;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test on the replicator filter.
 */
public class FilterREPTest extends TestCase {

    /**
     * Filter tested.
     */
    private FilterREP filterREP;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        filterREP = new FilterREP();
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
}