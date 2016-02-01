package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import org.apache.log4j.Logger;

public class InputPort extends Port {
	private static final Logger logger = Logger.getLogger(InputPort.class);

    private ConnectableInput input;

    public InputPort(String name, ConnectableInput input) {
        super(name);
        this.input = input;
    }
}
