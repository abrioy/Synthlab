package fr.synthlab.model.module.replicator;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class FilterREPTest extends TestCase {

    private FilterREP filterREP;

    @Before
    public void setUp() {
        filterREP = new FilterREP();
    }

    @Test
    public void testGetIn() {
        assertNotNull(filterREP.getIn());
    }

    @Test
    public void testGetOut1() {
        assertNotNull(filterREP.getOut1());
    }

    @Test
    public void testGetOut2() {
        assertNotNull(filterREP.getOut2());
    }

    @Test
    public void testGetOut3() {
        assertNotNull(filterREP.getOut3());
    }
}