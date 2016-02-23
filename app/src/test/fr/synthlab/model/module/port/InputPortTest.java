package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.Module;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;

public class InputPortTest {

    private InputPort inputPort;

    private Module mockModule;

    private ConnectableInput connectableInput;

    @Before
    public void setUp() {
        mockModule = mock(Module.class);
        connectableInput = new UnitInputPort("in");
        inputPort = new InputPort("in", mockModule, connectableInput);
    }

    @Test
    public void testGetInput() {
        assertSame(connectableInput, inputPort.getInput());
    }

    @Test
    public void testDisconnect() {
        connectOutput();
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    @Test
    public void testDisconnect2() {
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    @Test
    public void testDisconnect3() {
        connectInput();
        inputPort.disconnect();
        assertFalse(inputPort.isConnected());
    }

    @Test
    public void testGetName() {
        assertEquals("in", inputPort.getName());
    }

    @Test
    public void testIsConnected() {
        assertFalse(inputPort.isConnected());
    }

    @Test
    public void testIsConnected2() {
        connectOutput();
        assertTrue(inputPort.isConnected());
    }

    @Test
    public void testIsConnected3() {
        connectInput();
        assertTrue(inputPort.isConnected());
    }

    @Test
    public void testIsConnected4() {
        connectOutput();
        inputPort.disconnect();
        connectOutput();
        assertTrue(inputPort.isConnected());
    }

    @Test
    public void testGetConnected() {
        assertNull(inputPort.getConnected());
    }

    @Test
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

    @Test
    public void testGetConnected4() {
        connectInput();
        assertTrue(inputPort.getConnected() instanceof InputPort);
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected5() {
        connectInput();
        connectOutput();
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected6() {
        connectOutput();
        connectInput();
    }

    @Test(expected=RuntimeException.class)
    public void testGetConnected7() {
        connectInput();
        connectInput();
    }

    @Test
    public void testGetModule() {
        assertSame(mockModule, inputPort.getModule());
    }

    @Test
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