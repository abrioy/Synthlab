package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.Module;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test on Output port.
 */
public class OutputPortTest {

    private OutputPort outputPort;

    private Module mockModule;

    private ConnectableOutput connectableOutput;

    @Before
    public void setUp() {
        mockModule = mock(Module.class);
        connectableOutput = new UnitOutputPort("out");
        outputPort = new OutputPort("out", mockModule, connectableOutput);
    }

    @Test
    public void testGetOutput() {
        assertSame(connectableOutput, outputPort.getOutput());
    }

    @Test
    public void testDisconnect() {
        connectInput();
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    @Test
    public void testDisconnect2() {
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    @Test
    public void testDisconnect3() {
        connectOutput();
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    @Test
    public void testGetName() {
        assertEquals("out", outputPort.getName());
    }

    @Test
    public void testIsConnected() {
        assertFalse(outputPort.isConnected());
    }

    @Test
    public void testIsConnected2() {
        connectOutput();
        assertTrue(outputPort.isConnected());
    }

    @Test
    public void testIsConnected3() {
        connectInput();
        assertTrue(outputPort.isConnected());
    }

    @Test
    public void testIsConnected4() {
        connectInput();
        outputPort.disconnect();
        connectInput();
        assertTrue(outputPort.isConnected());
    }

    @Test
    public void testGetConnected() {
        assertNull(outputPort.getConnected());
    }

    @Test
    public void testGetConnected2() {
        connectInput();
        assertNotNull(outputPort.getConnected());
        assertTrue(outputPort.getConnected() instanceof InputPort);
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected3() {
        connectInput();
        connectInput();
    }

    @Test
    public void testGetConnected4() {
        connectOutput();
        assertTrue(outputPort.getConnected() instanceof OutputPort);
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected5() {
        connectOutput();
        connectInput();
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected6() {
        connectInput();
        connectOutput();
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected7() {
        connectOutput();
        connectOutput();
    }

    @Test
    public void testGetModule() {
        assertSame(mockModule, outputPort.getModule());
    }

    @Test
    public void testSetPort() {
        InputPort mockPort = mock(InputPort.class);
        outputPort.setPort(mockPort);
        assertSame(mockPort, outputPort.getConnected());
    }

    private void connectOutput(){
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        OutputPort outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        this.outputPort.connect(outputPort);
    }

    private void connectInput(){
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        InputPort input = new InputPort("in", mockModule2, unitInputPort);
        outputPort.connect(input);
    }
}