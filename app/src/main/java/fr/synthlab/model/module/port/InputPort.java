package fr.synthlab.model.module.port;

import com.jsyn.ports.ConnectableInput;

public class InputPort extends Port {
    private ConnectableInput input;

    public InputPort(String name, ConnectableInput input) {
        super(name);
        this.input = input;
    }
}
