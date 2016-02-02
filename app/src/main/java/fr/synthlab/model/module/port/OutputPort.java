package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import fr.synthlab.model.module.Module;
import org.apache.log4j.Logger;

public class OutputPort extends Port {
	private static final Logger logger = Logger.getLogger(OutputPort.class);

    private ConnectableOutput output;

    public OutputPort(String name, Module m, ConnectableOutput output) {
        super(name, m);
        this.output = output;
    }

    public ConnectableOutput getOutput() {
        return output;
    }

    public void connect(InputPort port) {
        output.connect(port.getInput());
        super.connect(port);
    }

    public void disconnect() {
        output.disconnect(((InputPort) getConnected()).getInput());
        super.disconnect();
    }
}