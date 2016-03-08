package fr.synthlab.model.module.mixer;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests on the mixer filter.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterMIXImplTest {

    /**
     * mock input 1.
     */
    @Mock
    private UnitInputPort input1;

    /**
     * mock input 2.
     */
    @Mock
    private UnitInputPort input2;

    /**
     * mock input 3.
     */
    @Mock
    private UnitInputPort input3;

    /**
     * mock input 4.
     */
    @Mock
    private UnitInputPort input4;

    /**
     * spy output.
     */
    @Spy
    private UnitOutputPort output;

    /**
     * Filter tested
     */
    @InjectMocks
    private FilterMIX filterMIX = new FilterMIXImpl();


    /**
     * test on get input 1.
     */
    @Test
    public void testGetInput1() {
        assertSame(input1, filterMIX.getInput1());
    }

    /**
     * test on get input 2.
     */
    @Test
    public void testGetInput2() {
        assertSame(input2, filterMIX.getInput2());
    }

    /**
     * test on get input 3.
     */
    @Test
    public void testGetInput3() {
        assertSame(input3, filterMIX.getInput3());
    }

    /**
     * test on get input 4.
     */
    @Test
    public void testGetInput4() {
        assertSame(input4, filterMIX.getInput4());
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate() {
        when(input1.getValues()).thenReturn(new double[]{10.0});
        when(input2.getValues()).thenReturn(new double[]{20.0});
        when(input3.getValues()).thenReturn(new double[]{30.0});
        when(input4.getValues()).thenReturn(new double[]{40.0});

        when(input1.isConnected()).thenReturn(true);
        when(input2.isConnected()).thenReturn(true);
        when(input3.isConnected()).thenReturn(true);
        when(input4.isConnected()).thenReturn(true);
        filterMIX.generate(0,1);

        InOrder inOrder = inOrder(input1, input2, input3, input4, output, output);

        inOrder.verify(input1).getValues();
        inOrder.verify(input2).getValues();
        inOrder.verify(input3).getValues();
        inOrder.verify(input4).getValues();
        inOrder.verify(output).getValues();

        inOrder.verify(input1).isConnected();
        inOrder.verify(input2).isConnected();
        inOrder.verify(input3).isConnected();
        inOrder.verify(input4).isConnected();

        assertEquals(25.0, filterMIX.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate2() {
        when(input1.getValues()).thenReturn(new double[]{10.0});
        when(input2.getValues()).thenReturn(new double[]{20.0});
        when(input3.getValues()).thenReturn(new double[]{30.0});
        when(input4.getValues()).thenReturn(new double[]{40.0});

        when(input1.isConnected()).thenReturn(false);
        when(input2.isConnected()).thenReturn(true);
        when(input3.isConnected()).thenReturn(true);
        when(input4.isConnected()).thenReturn(true);
        filterMIX.generate(0,1);

        InOrder inOrder = inOrder(input1, input2, input3, input4, output, output);

        inOrder.verify(input1).getValues();
        inOrder.verify(input2).getValues();
        inOrder.verify(input3).getValues();
        inOrder.verify(input4).getValues();
        inOrder.verify(output).getValues();

        inOrder.verify(input1).isConnected();
        inOrder.verify(input2).isConnected();
        inOrder.verify(input3).isConnected();
        inOrder.verify(input4).isConnected();

        assertEquals(30.0, filterMIX.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate3() {
        when(input1.getValues()).thenReturn(new double[]{10.0});
        when(input2.getValues()).thenReturn(new double[]{20.0});
        when(input3.getValues()).thenReturn(new double[]{30.0});
        when(input4.getValues()).thenReturn(new double[]{40.0});

        when(input1.isConnected()).thenReturn(true);
        when(input2.isConnected()).thenReturn(false);
        when(input3.isConnected()).thenReturn(true);
        when(input4.isConnected()).thenReturn(true);
        filterMIX.generate(0,1);

        InOrder inOrder = inOrder(input1, input2, input3, input4, output);

        inOrder.verify(input1).getValues();
        inOrder.verify(input2).getValues();
        inOrder.verify(input3).getValues();
        inOrder.verify(input4).getValues();
        inOrder.verify(output).getValues();

        inOrder.verify(input1).isConnected();
        inOrder.verify(input2).isConnected();
        inOrder.verify(input3).isConnected();
        inOrder.verify(input4).isConnected();

        assertEquals(26.6666667, filterMIX.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate4() {
        when(input1.getValues()).thenReturn(new double[]{10.0});
        when(input2.getValues()).thenReturn(new double[]{20.0});
        when(input3.getValues()).thenReturn(new double[]{30.0});
        when(input4.getValues()).thenReturn(new double[]{40.0});

        when(input1.isConnected()).thenReturn(true);
        when(input2.isConnected()).thenReturn(true);
        when(input3.isConnected()).thenReturn(false);
        when(input4.isConnected()).thenReturn(true);
        filterMIX.generate(0,1);

        InOrder inOrder = inOrder(input1, input2, input3, input4, output);

        inOrder.verify(input1).getValues();
        inOrder.verify(input2).getValues();
        inOrder.verify(input3).getValues();
        inOrder.verify(input4).getValues();
        inOrder.verify(output).getValues();

        inOrder.verify(input1).isConnected();
        inOrder.verify(input2).isConnected();
        inOrder.verify(input3).isConnected();
        inOrder.verify(input4).isConnected();

        assertEquals(23.3333333, filterMIX.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on generate.
     */
    @Test
    public void testGenerate5() {
        when(input1.getValues()).thenReturn(new double[]{10.0});
        when(input2.getValues()).thenReturn(new double[]{20.0});
        when(input3.getValues()).thenReturn(new double[]{30.0});
        when(input4.getValues()).thenReturn(new double[]{40.0});

        when(input1.isConnected()).thenReturn(true);
        when(input2.isConnected()).thenReturn(true);
        when(input3.isConnected()).thenReturn(true);
        when(input4.isConnected()).thenReturn(false);
        filterMIX.generate(0,1);

        InOrder inOrder = inOrder(input1, input2, input3, input4, output);

        inOrder.verify(input1).getValues();
        inOrder.verify(input2).getValues();
        inOrder.verify(input3).getValues();
        inOrder.verify(input4).getValues();
        inOrder.verify(output).getValues();

        inOrder.verify(input1).isConnected();
        inOrder.verify(input2).isConnected();
        inOrder.verify(input3).isConnected();
        inOrder.verify(input4).isConnected();

        assertEquals(20.0, filterMIX.getOutput().getValue(), 0.0000001);
    }

    /**
     * test on get output.
     */
    @Test
    public void testGetOutput() {
        assertSame(output, filterMIX.getOutput());
     }
}