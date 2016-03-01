package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.Module;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test on Input port.
 */
public class InputPortTest {

    /**
     * Port tested.
     */
    private InputPort inputPort;

    /**
     * Mock module to initialize the InputPort.
     */
    private Module mockModule;

    /**
     * JSyn port.
     */
    private ConnectableInput connectableInput;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        mockModule = mock(Module.class);
        connectableInput = new UnitInputPort("in");
        inputPort = new InputPort("in", mockModule, connectableInput);
    }

    /**
     * test on get Input.
     */
    @Test
    public void testGetInput() {
        assertSame(connectableInput, inputPort.getInput());
    }

    /**
     * test on disconnect method when the port is connected to a good port.
     */
    @Test
    public void testDisconnect() {
        connectOutput();
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    /**
     * test on disconnect method when the port is not connected to a port.
     */
    @Test
    public void testDisconnect2() {
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    /**
     * test on disconnect method when the port is not connected to a good port.
     */
    @Test
    public void testDisconnect3() {
        connectInput();
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    /**
     * Test get port name.
     */
    @Test
    public void testGetName() {
        assertEquals("in", inputPort.getName());
    }

    /**
     * Test if the port is not connected after initialization.
     */
    @Test
    public void testIsConnected() {
        assertFalse(inputPort.isConnected());
    }

    /**
     * Test if the port is connected after connection with an output port.
     */
    @Test
    public void testIsConnected2() {
        connectOutput();
        assertTrue(inputPort.isConnected());
    }

    /**
     * Test if the port is connected after connection with an input port.
     */
    @Test
    public void testIsConnected3() {
        connectInput();
        assertTrue(inputPort.isConnected());
    }

    /**
     * Test if the port can be reconnected after disconnection.
     */
    @Test
    public void testIsConnected4() {
        connectOutput();
        inputPort.disconnect();
        connectOutput();
        assertTrue(inputPort.isConnected());
    }

    /**
     * Check that nothing is connected
     */
    @Test
    public void testGetConnected() {
        assertNull(inputPort.getConnected());
    }

    /**
     * Check the connected port is an output port.
     */
    @Test
    public void testGetConnected2() {
        connectOutput();
        assertNotNull(inputPort.getConnected());
        assertTrue(inputPort.getConnected() instanceof OutputPort);
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected3() {
        connectOutput();
        connectOutput();
    }

    /**
     * Check the connected port is an input port.
     */
    @Test
    public void testGetConnected4() {
        connectInput();
        assertTrue(inputPort.getConnected() instanceof InputPort);
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected5() {
        connectInput();
        connectOutput();
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected6() {
        connectOutput();
        connectInput();
    }

    /**
     * Check throw exception when we connect a port already connected.
     */
    @Test(expected=RuntimeException.class)
    public void testGetConnected7() {
        connectInput();
        connectInput();
    }

    /**
     * test on get module.
     */
    @Test
    public void testGetModule() {
        assertSame(mockModule, inputPort.getModule());
    }

    /**
     * test on set port.
     */
    @Test
    public void testSetPort() {
        OutputPort mockPort = mock(OutputPort.class);
        inputPort.setPort(mockPort);
        assertSame(mockPort, inputPort.getConnected());
    }

    /*
      To connect an output port on this port.
     */
    private void connectOutput(){
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        OutputPort outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        inputPort.connect(outputPort);
    }

    /*
      To connect an input port on this port.
     */
    private void connectInput(){
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        InputPort input = new InputPort("in", mockModule2, unitInputPort);
        inputPort.connect(input);
    }
}