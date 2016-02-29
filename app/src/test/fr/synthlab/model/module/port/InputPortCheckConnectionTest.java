package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import fr.synthlab.model.module.Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Other test on input ports to accurately check the connect methods call.
 */
@RunWith(MockitoJUnitRunner.class)
public class InputPortCheckConnectionTest {

    /**
     * Mock module to initialize the InputPort.
     */
    @Mock
    private Module mockModule;

    /**
     * Mockito spy to check connect and disconnect call.
     */
    @Spy
    private ConnectableInput connectableInput = new UnitInputPort("in");

    /**
     * Port tested.
     */
    private InputPort inputPort;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        inputPort = new InputPort("in", mockModule, connectableInput);
    }

    /**
     * Check that Jsyn's connect is called when we connect our input to an output.
     */
    @Test
    public void testCheckConnect() {
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        Port outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        inputPort.connect(outputPort);

        verify(connectableInput).connect(((OutputPort) outputPort).getOutput());
    }

    /**
     * Check that Jsyn's connect is not called when we connect our input to an other input.
     */
    @Test
    public void testCheckConnect2() {
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        Port port = new InputPort("in", mockModule2, unitInputPort);
        inputPort.connect(port);

        verify(connectableInput, never()).connect(any(UnitOutputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is not called when we disconnect our input.
     */
    @Test
    public void testCheckDisconnect1() {
        inputPort.disconnect();

        verify(connectableInput, never()).disconnect(any(UnitOutputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is called when our input is connect.
     */
    @Test
    public void testCheckDisconnect2() {
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        Port outputPort = new OutputPort("out", mockModule2, unitOutputPort);
        inputPort.connect(outputPort);
        inputPort.disconnect();

        InOrder inOrder = inOrder(connectableInput);

        inOrder.verify(connectableInput).connect(((OutputPort) outputPort).getOutput());
        inOrder.verify(connectableInput).disconnect(any(UnitOutputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is not called when our input is connect to an other input.
     */
    @Test
    public void testCheckDisconnect3() {
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        Port port = new InputPort("in", mockModule2, unitInputPort);

        inputPort.connect(port);
        inputPort.disconnect();

        InOrder inOrder = inOrder(connectableInput);

        inOrder.verify(connectableInput, never()).connect(any(UnitOutputPort.class));
        inOrder.verify(connectableInput, never()).disconnect(any(UnitOutputPort.class));
    }
}