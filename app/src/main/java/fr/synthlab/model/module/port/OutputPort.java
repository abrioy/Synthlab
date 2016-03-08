package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

/**
 * Output Port.
 * @see PortImpl
 */
public class OutputPort extends PortImpl {
    /**
     * logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(OutputPort.class.getName());

    /**
     * the port out jsyn.
     */
    private ConnectableOutput output;

    /**
     * Constructor.
     *
     * @param name   The name of this port
     * @param m      The module containing this port
     * @param outputInit The JSyn port to assign
     */
    public OutputPort(final String name,
                      final Module m, final ConnectableOutput outputInit) {
        super(name, m);
        output = outputInit;
    }

    /**
     * @return The JSyn output port
     */
    public final ConnectableOutput getOutput() {
        return output;
    }

    /**
     * Connect another port to this port.
     *
     * @param port Port to connect
     */
    @Override
    public final void connect(final Port port) {
        if (getConnected() != null) {
            throw new RuntimeException("Unable to connect \"" + port.getName()
                    + "\" to this port (" + this.getName()
                    + ") because it is already connected to \""
                    + getConnected().getName() + "\".");
        }
        if (port instanceof InputPort) {
            output.connect(((InputPort) port).getInput());
        }
        super.connect(port);
    }

    /**
     * Disconnect the current connected port.
     */
    public final void disconnect() {
        if (getConnected() instanceof InputPort) {
            output.disconnect(((InputPort) getConnected()).getInput());
        }
        super.disconnect();
    }
}
