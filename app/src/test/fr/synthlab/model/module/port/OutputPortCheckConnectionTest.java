package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Other test on output ports to accurately check the connect methods call.
 */
@RunWith(MockitoJUnitRunner.class)
public class OutputPortCheckConnectionTest {

    /**
     * Mock module to initialize the InputPort.
     */
    @Mock
    private Module mockModule;

    /**
     * Mockito spy to check connect and disconnect call.
     */
    @Spy
    private ConnectableOutput connectableOutput = new UnitOutputPort();

    /**
     * Port tested.
     */
    private OutputPort outputPort;

    /**
     * Initialize.
     */
    @Before
    public void setUp() {
        outputPort = new OutputPort("out", mockModule, connectableOutput);
    }

    /**
     * Check that Jsyn's connect is called when we connect our output to an input.
     */
    @Test
    public void testCheckConnect() {
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        Port inputPort = new InputPort("in", mockModule2, unitInputPort);
        outputPort.connect(inputPort);

        verify(connectableOutput).connect(((InputPort) inputPort).getInput());
    }

    /**
     * Check that Jsyn's connect is not called when we connect our output to an other output.
     */
    @Test
    public void testCheckConnect2() {
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        Port port = new OutputPort("out", mockModule2, unitOutputPort);
        outputPort.connect(port);

        verify(connectableOutput, never()).connect(any(UnitInputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is not called when we disconnect our output.
     */
    @Test
    public void testCheckDisconnect1() {
        outputPort.disconnect();

        verify(connectableOutput, never()).disconnect(any(UnitInputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is called when our output is connect.
     */
    @Test
    public void testCheckDisconnect2() {
        UnitInputPort unitInputPort = new UnitInputPort("in");
        Module mockModule2 = mock(Module.class);
        Port inputPort = new InputPort("in", mockModule2, unitInputPort);

        outputPort.connect(inputPort);
        outputPort.disconnect();

        InOrder inOrder = inOrder(connectableOutput);

        inOrder.verify(connectableOutput).connect(((InputPort) inputPort).getInput());
        inOrder.verify(connectableOutput).disconnect(any(UnitInputPort.class));
    }

    /**
     * Check that Jsyn's disconnect is not called when our output is connect to an other output.
     */
    @Test
    public void testCheckDisconnect3() {
        UnitOutputPort unitOutputPort = new UnitOutputPort();
        Module mockModule2 = mock(Module.class);
        Port port = new OutputPort("out", mockModule2, unitOutputPort);
        outputPort.connect(port);
        outputPort.disconnect();

        InOrder inOrder = inOrder(connectableOutput);

        inOrder.verify(connectableOutput, never()).connect(any(UnitInputPort.class));
        inOrder.verify(connectableOutput, never()).disconnect(any(UnitInputPort.class));
    }
}