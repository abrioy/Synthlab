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

    /**
     * Port tested.
     */
    private OutputPort outputPort;

    /**
     * Mock module to initialize the OutputPort.
     */
    private Module mockModule;

    /**
     * JSyn port.
     */
    private ConnectableOutput connectableOutput;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        mockModule = mock(Module.class);
        connectableOutput = new UnitOutputPort("out");
        outputPort = new OutputPort("out", mockModule, connectableOutput);
    }

    /**
     * test on get Output.
     */
    @Test
    public void testGetOutput() {
        assertSame(connectableOutput, outputPort.getOutput());
    }

    /**
     * test on disconnect method when the port is connected to a good port.
     */
    @Test
    public void testDisconnect() {
        connectInput();
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    /**
     * test on disconnect method when the port is not connected to a port.
     */
    @Test
    public void testDisconnect2() {
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    /**
     * test on disconnect method when the port is not connected to a good port.
     */
    @Test
    public void testDisconnect3() {
        connectOutput();
        outputPort.disconnect();
        assertFalse(outputPort.isConnected());
    }

    /**
     * Test get port name.
     */
    @Test
    public void testGetName() {
        assertEquals("out", outputPort.getName());
    }

    /**
     * Test if the port is not connected after initialization.
     */
    @Test
    public void testIsConnected() {
        assertFalse(outputPort.isConnected());
    }

    /**
     * Test if the port is connected after connection with an output port.
     */
    @Test
    public void testIsConnected2() {
        connectOutput();
        assertTrue(outputPort.isConnected());
    }

    /**
     * Test if the port is connected after connection with an input port.
     */
    @Test
    public void testIsConnected3() {
        connectInput();
        assertTrue(outputPort.isConnected());
    }

    /**
     * Test if the port can be reconnected after disconnection.
     */
    @Test
    public void testIsConnected4() {
        connectInput();
        outputPort.disconnect();
        connectInput();
        assertTrue(outputPort.isConnected());
    }

    /**
     * Check that nothing is connected
     */
    @Test
    public void testGetConnected() {
        assertNull(outputPort.getConnected());
    }

    /**
     * Check the connected port is an input port.
     */
    @Test
    public void testGetConnected2() {
        connectInput();
        assertNotNull(outputPort.getConnected());
        assertTrue(outputPort.getConnected() instanceof InputPort);
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected3() {
        connectInput();
        connectInput();
    }

    /**
     * Check the connected port is an output port.
     */
    @Test
    public void testGetConnected4() {
        connectOutput();
        assertTrue(outputPort.getConnected() instanceof OutputPort);
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected5() {
        connectOutput();
        connectInput();
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected6() {
        connectInput();
        connectOutput();
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected7() {
        connectOutput();
        connectOutput();
    }

    /**
     * test on get module.
     */
    @Test
    public void testGetModule() {
        assertSame(mockModule, outputPort.getModule());
    }

    /**
     * test on set port.
     */
    @Test
    public void testSetPort() {
        InputPort mockPort = mock(InputPort.class);
        outputPort.setPort(mockPort);
        assertSame(mockPort, outputPort.getConnected());
    }

    /*
      To connect an output port on this port.
     */
    private void connectOutput(){
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        OutputPort outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        this.outputPort.connect(outputPort);
    }

    /*
      To connect an input port on this port.
     */
    private void connectInput(){
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        InputPort input = new InputPort("in", mockModule2, unitInputPort);
        outputPort.connect(input);
    }
}