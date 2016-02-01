package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import fr.synthlab.model.module.Module;
import org.apache.log4j.Logger;

public class InputPort extends Port {
	private static final Logger logger = Logger.getLogger(InputPort.class);

    private ConnectableInput input;

    public InputPort(String name, Module m, ConnectableInput input) {
        super(name, m);
        this.input = input;
    }

    public ConnectableInput getInput() {
        return input;
    }

    public void connect(OutputPort port) {
        super.connect(port);
        input.connect(port.getOutput());
    }
}
