package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.Module;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class InputPortTest extends TestCase {

    private InputPort inputPort;

    private Module mockModule;

    private ConnectableInput connectableInput;

    @Before
    public void setUp() {
        mockModule = mock(Module.class);
        connectableInput = new UnitInputPort("in");
        inputPort = new InputPort("in", mockModule, connectableInput);
    }

    public void testGetInput() {
        assertSame(connectableInput, inputPort.getInput());
    }

    public void testDisconnect() {
        connectOutput();
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    public void testDisconnect2() {
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    public void testGetName() {
        assertEquals("in", inputPort.getName());
    }

    public void testIsConnected() {
        assertFalse(inputPort.isConnected());
    }

    public void testIsConnected2() {
        connectOutput();
        assertTrue(inputPort.isConnected());
    }

    public void testGetConnected() {
        assertNull(inputPort.getConnected());
    }

    public void testGetConnected2() {
        connectOutput();
        assertNotNull(inputPort.getConnected());
        assertTrue(inputPort.getConnected() instanceof OutputPort);
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected3() {
        connectOutput();
        connectOutput();
    }

    public void testGetConnected4() {
        connectInput();
        //assertNull(inputPort.getConnected());
        assertTrue(inputPort.getConnected() instanceof InputPort);
    }

    public void testGetModule() {
        assertSame(mockModule, inputPort.getModule());
    }

    public void testSetPort() {
        OutputPort mockPort = mock(OutputPort.class);
        inputPort.setPort(mockPort);
        assertSame(mockPort, inputPort.getConnected());
    }

    private void connectOutput(){
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        OutputPort outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        inputPort.connect(outputPort);
    }

    private void connectInput(){
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        InputPort input = new InputPort("in", mockModule2, unitInputPort);
        inputPort.connect(input);
    }
}