package fr.synthlab.model.module.keyboard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test note key enumeration.
 */
public class NoteKEYBImplTest {

    /**
     * test get value.
     */
    @Test
    public void testGetValue() {
        int i = -9;
        for(NoteKEYBImpl noteKEYB : NoteKEYBImpl.values()){
            assertEquals(i, noteKEYB.getValue());
            i++;
        }
    }

    /**
     * test from value with -10.
     */
    @Test
    public void testFromValue() {
        assertNull(NoteKEYBImpl.fromValue(-10));
    }

    /**
     * test from value with 10.
     */
    @Test
    public void testFromValue2() {
        assertNull(NoteKEYBImpl.fromValue(10));
    }

    /**
     * test from value with -9.
     */
    @Test
    public void testFromValue3() {
        assertEquals(NoteKEYBImpl.C, NoteKEYBImpl.fromValue(-9));
    }

    /**
     * test from value with -8.
     */
    @Test
    public void testFromValue4() {
        assertEquals(NoteKEYBImpl.CSharp, NoteKEYBImpl.fromValue(-8));
    }

    /**
     * test from value with -7.
     */
    @Test
    public void testFromValue5() {
        assertEquals(NoteKEYBImpl.D, NoteKEYBImpl.fromValue(-7));
    }

    /**
     * test from value with -6.
     */
    @Test
    public void testFromValue6() {
        assertEquals(NoteKEYBImpl.DSharp, NoteKEYBImpl.fromValue(-6));
    }

    /**
     * test from value with -5.
     */
    @Test
    public void testFromValue7() {
        assertEquals(NoteKEYBImpl.E, NoteKEYBImpl.fromValue(-5));
    }

    /**
     * test from value with -4.
     */
    @Test
    public void testFromValue8() {
        assertEquals(NoteKEYBImpl.F, NoteKEYBImpl.fromValue(-4));
    }

    /**
     * test from value with -3.
     */
    @Test
    public void testFromValue9() {
        assertEquals(NoteKEYBImpl.FSharp, NoteKEYBImpl.fromValue(-3));
    }

    /**
     * test from value with -2.
     */
    @Test
    public void testFromValue10() {
        assertEquals(NoteKEYBImpl.G, NoteKEYBImpl.fromValue(-2));
    }

    /**
     * test from value with -1.
     */
    @Test
    public void testFromValue11() {
        assertEquals(NoteKEYBImpl.GSharp, NoteKEYBImpl.fromValue(-1));
    }

    /**
     * test from value with 0.
     */
    @Test
    public void testFromValue12() {
        assertEquals(NoteKEYBImpl.A, NoteKEYBImpl.fromValue(0));
    }

    /**
     * test from value with 1.
     */
    @Test
    public void testFromValue13() {
        assertEquals(NoteKEYBImpl.ASharp, NoteKEYBImpl.fromValue(1));
    }

    /**
     * test from value with 2.
     */
    @Test
    public void testFromValue14() {
        assertEquals(NoteKEYBImpl.B, NoteKEYBImpl.fromValue(2));
    }

    /**
     * test from value with 3.
     */
    @Test
    public void testFromValue15() {
        assertEquals(NoteKEYBImpl.C2, NoteKEYBImpl.fromValue(3));
    }
}