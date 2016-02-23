package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

public class OutputPort extends Port {
	private static final Logger logger = Logger.getLogger(OutputPort.class.getName());

    private ConnectableOutput output;

    /**
     * Constructor
     *
     * @param name   The name of this port
     * @param m      The module containing this port
     * @param output The JSyn port to assign
     */
    public OutputPort(String name, Module m, ConnectableOutput output) {
        super(name, m);
        this.output = output;
    }

    /**
     *
     * @return The JSyn output port
     */
    public ConnectableOutput getOutput() {
        return output;
    }

    /**
     * Connect another port to this port
     * @param port
     */
    @Override
    public void connect(Port port) {
        if (getConnected() != null)
            throw new RuntimeException("Unable to connect \"" + port.getName()
                    + "\" to this port (" + this.getName() + ") because it is already connected to \""
                    + getConnected().getName() + "\".");

        if (port instanceof InputPort)
            output.connect(((InputPort) port).getInput());
        super.connect(port);
    }

    /**
     * Disconnect the current connected port
     */
    public void disconnect() {
        if(getConnected() instanceof InputPort)
            output.disconnect(((InputPort) getConnected()).getInput());
        super.disconnect();
    }
}