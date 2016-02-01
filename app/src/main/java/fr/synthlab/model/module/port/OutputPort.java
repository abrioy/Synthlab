package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;

public class OutputPort extends Port {
    private ConnectableOutput output;

    public OutputPort(String name, ConnectableOutput output) {
        super(name);
        this.output = output;
    }
}