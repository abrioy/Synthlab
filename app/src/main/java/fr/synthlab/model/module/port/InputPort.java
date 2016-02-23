package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

/**
 *
 */
public class InputPort extends Port {
    private static final Logger LOGGER = Logger.getLogger(InputPort.class.getName());

    /**
     * The matching port in JSyn
     */
    private ConnectableInput input;

    /**
     * Constructor
     *
     * @param name  The name of this port
     * @param m     The module containing this port
     * @param inputInit The JSyn port to assign
     */
    public InputPort(String name, Module m, ConnectableInput inputInit) {
        super(name, m);
        input = inputInit;
    }

    /**
     * @return The JSyn port
     */
    public ConnectableInput getInput() {
        return input;
    }

    /**
     * Connect another port to this port.
     *
     * @param port Port to connect
     */
    @Override
    public void connect(Port port) {
        if (getConnected() != null) {
            throw new RuntimeException("Unable to connect \"" + port.getName()
                    + "\" to this port (" + this.getName() + ") because it is already connected to \""
                    + getConnected().getName() + "\".");
        }

        if (port instanceof OutputPort) {
            input.connect(((OutputPort) port).getOutput());
        }
        super.connect(port);
    }

    /**
     * Disconnect a connected port from this port.
     */
    public void disconnect() {
        if (getConnected() instanceof OutputPort) {
            input.disconnect(((OutputPort) getConnected()).getOutput());
        }
        super.disconnect();
    }
}
