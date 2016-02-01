package fr.synthlab.model.module;

import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface Module {
    Collection<Port> getPorts();
}
