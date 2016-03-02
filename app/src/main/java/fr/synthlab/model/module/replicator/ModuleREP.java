package fr.synthlab.model.module.replicator;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface ModuleREP extends Module {
    /**
     * Getter on ports input and output.
     * @return Scope port
     */
    @Override
    Collection<Port> getPorts();

    /**
     * Start module.
     */
    @Override
    void start();

    /**
     * Stop module.
     */
    @Override
    void stop();

    /**
     * Inherit method.
     */
    @Override
    void update();

    @Override
    ModuleType getType();
}
