package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import org.apache.log4j.Logger;

public class OutputPort extends Port {
	private static final Logger logger = Logger.getLogger(OutputPort.class);

    private ConnectableOutput output;

    public OutputPort(String name, ConnectableOutput output) {
        super(name);
        this.output = output;
    }
}