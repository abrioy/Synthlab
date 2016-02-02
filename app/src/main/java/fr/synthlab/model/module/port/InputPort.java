package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import fr.synthlab.model.module.Module;
import java.util.logging.Logger;

public class InputPort extends Port {
	private static final Logger logger = Logger.getLogger(InputPort.class.getName());

    private ConnectableInput input;

    public InputPort(String name, Module m, ConnectableInput input) {
        super(name, m);
        this.input = input;
    }

    public ConnectableInput getInput() {
        return input;
    }

    public void connect(OutputPort port) {
        input.connect(port.getOutput());
        super.connect(port);
    }

    public void disconnect() {
        input.disconnect(((OutputPort) getConnected()).getOutput());
        super.disconnect();
    }
}
