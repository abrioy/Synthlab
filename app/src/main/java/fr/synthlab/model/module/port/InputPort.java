package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;

public class InputPort extends Port {
    private ConnectableInput input;

    public InputPort(String name) {
        super(name);
        input = new UnitInputPort(name);
    }
}
