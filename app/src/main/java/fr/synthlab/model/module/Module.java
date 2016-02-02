package fr.synthlab.model.module;

import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface Module {
    Collection<Port> getPorts();

    default Port getPort(String name) {
        for (Port p : getPorts()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    void update();
}
