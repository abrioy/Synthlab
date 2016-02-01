package fr.synthlab.model.module;

import fr.synthlab.model.module.port.Port;

import java.util.Collection;

/**
 * Created by johan on 01/02/16.
 */
public interface Module {
    Collection<Port> getPorts();
}
