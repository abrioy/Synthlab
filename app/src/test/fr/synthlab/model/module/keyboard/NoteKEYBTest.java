package fr.synthlab.model.module.keyboard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test note key enumeration.
 */
public class NoteKEYBTest {

    /**
     * test get value.
     */
    @Test
    public void testGetValue() {
        int i = -9;
        for(NoteKEYB noteKEYB : NoteKEYB.values()){
            assertEquals(i, noteKEYB.getValue());
            i++;
        }
    }

    /**
     * test from value with -10.
     */
    @Test
    public void testFromValue() {
        assertNull(NoteKEYB.fromValue(-10));
    }

    /**
     * test from value with 10.
     */
    @Test
    public void testFromValue2() {
        assertNull(NoteKEYB.fromValue(10));
    }

    /**
     * test from value with -9.
     */
    @Test
    public void testFromValue3() {
        assertEquals(NoteKEYB.C, NoteKEYB.fromValue(-9));
    }

    /**
     * test from value with -8.
     */
    @Test
    public void testFromValue4() {
        assertEquals(NoteKEYB.CSharp, NoteKEYB.fromValue(-8));
    }

    /**
     * test from value with -7.
     */
    @Test
    public void testFromValue5() {
        assertEquals(NoteKEYB.D, NoteKEYB.fromValue(-7));
    }

    /**
     * test from value with -6.
     */
    @Test
    public void testFromValue6() {
        assertEquals(NoteKEYB.DSharp, NoteKEYB.fromValue(-6));
    }

    /**
     * test from value with -5.
     */
    @Test
    public void testFromValue7() {
        assertEquals(NoteKEYB.E, NoteKEYB.fromValue(-5));
    }

    /**
     * test from value with -4.
     */
    @Test
    public void testFromValue8() {
        assertEquals(NoteKEYB.F, NoteKEYB.fromValue(-4));
    }

    /**
     * test from value with -3.
     */
    @Test
    public void testFromValue9() {
        assertEquals(NoteKEYB.FSharp, NoteKEYB.fromValue(-3));
    }

    /**
     * test from value with -2.
     */
    @Test
    public void testFromValue10() {
        assertEquals(NoteKEYB.G, NoteKEYB.fromValue(-2));
    }

    /**
     * test from value with -1.
     */
    @Test
    public void testFromValue11() {
        assertEquals(NoteKEYB.GSharp, NoteKEYB.fromValue(-1));
    }

    /**
     * test from value with 0.
     */
    @Test
    public void testFromValue12() {
        assertEquals(NoteKEYB.A, NoteKEYB.fromValue(0));
    }

    /**
     * test from value with 1.
     */
    @Test
    public void testFromValue13() {
        assertEquals(NoteKEYB.ASharp, NoteKEYB.fromValue(1));
    }

    /**
     * test from value with 2.
     */
    @Test
    public void testFromValue14() {
        assertEquals(NoteKEYB.B, NoteKEYB.fromValue(2));
    }

    /**
     * test from value with 3.
     */
    @Test
    public void testFromValue15() {
        assertEquals(NoteKEYB.C2, NoteKEYB.fromValue(3));
    }
}