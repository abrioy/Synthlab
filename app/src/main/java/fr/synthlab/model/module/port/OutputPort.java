package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableOutput;
import com.jsyn.ports.UnitOutputPort;

public class OutputPort extends Port {
    private ConnectableOutput output;

    public OutputPort(String name) {
        super(name);
        output = new UnitOutputPort(name);
    }
}